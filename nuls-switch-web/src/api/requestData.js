import {post_nuls} from './https'
import {post} from './https'
import {get} from './https'
import {Plus, chainID, divDecimals} from './util'

/**
 * 计算手续费
 * @param tx
 * @param signatrueCount 签名数量，默认为1
 **/
export function countFee(tx, signatrueCount) {
    let txSize = tx.txSerialize().length;
    txSize += signatrueCount * 110;
    return 100000 * Math.ceil(txSize / 1024);
}

/**
 * 计算跨链交易手续费
 * @param tx
 * @param signatrueCount 签名数量，默认为1
 **/
export function countCtxFee(tx, signatrueCount) {
    let txSize = tx.txSerialize().length;
    txSize += signatrueCount * 110;
    return 1000000 * Math.ceil(txSize / 1024);
}

/**
 * 所有交易 获取inputs and outputs
 * @param transferInfo
 * @param balanceInfo
 * @param type
 * @returns {*}
 **/
export async function inputsOrOutputsAllTx(transferInfo, balanceInfo, type) {
    let newAmount = Number(Plus(transferInfo.amount, transferInfo.fee));
    let newLocked = 0;
    let newNonce = balanceInfo.nonce;
    let newoutputAmount = transferInfo.amount;
    let newLockTime = 0;
    if (balanceInfo.balance < newAmount) {
        return {success: false, data: "Your balance is not enough."}
    }
    if (type === 4) {
        newLockTime = -1;
    } else if (type === 5) {
        newLockTime = -1;
    } else if (type === 6) {
        newAmount = transferInfo.amount;
        newLocked = -1;
        newNonce = transferInfo.depositHash.substring(transferInfo.depositHash.length - 16);
        newoutputAmount = transferInfo.amount - transferInfo.fee;
    } else if (type === 9) {
        newAmount = transferInfo.amount;
        newLocked = -1;
        newNonce = transferInfo.depositHash.substring(transferInfo.depositHash.length - 16);
        newoutputAmount = transferInfo.amount - transferInfo.fee;
        //锁定三天
        newLockTime = (new Date()).valueOf() + 3600000 * 72;
    } else {
        //return {success: false, data: "No transaction type"}
    }

    let inputs = [{
        address: transferInfo.fromAddress,
        assetsChainId: transferInfo.assetsChainId,
        assetsId: transferInfo.assetsId,
        amount: newAmount,
        locked: newLocked,
        nonce: newNonce
    }];

    if (type === 2 && transferInfo.assetsChainId !== chainID()) {
        inputs[0].amount = transferInfo.amount;
        //账户转出资产余额
        let nulsbalance = await getBalanceOrNonceByAddress(chainID(), transferInfo.assetsId, transferInfo.fromAddress);
        if (nulsbalance.data.balance < 100000) {
            console.log("余额小于手续费");
            return
        }
        inputs.push({
            address: transferInfo.fromAddress,
            assetsChainId: chainID(),
            assetsId: transferInfo.assetsId,
            amount: 100000,
            locked: newLocked,
            nonce: nulsbalance.data.nonce
        })
    }
    let outputs = [];
    if (type === 15 || type === 17) {
        return {success: true, data: {inputs: inputs, outputs: outputs}};
    }
    if (type === 16) {
        if (!transferInfo.toAddress) {
            return {success: true, data: {inputs: inputs, outputs: outputs}};
        } else {
            newoutputAmount = transferInfo.value;
        }
    }
    outputs = [{
        address: transferInfo.toAddress ? transferInfo.toAddress : transferInfo.fromAddress,
        assetsChainId: transferInfo.assetsChainId,
        assetsId: transferInfo.assetsId,
        amount: newoutputAmount,
        lockTime: newLockTime
    }];
    return {success: true, data: {inputs: inputs, outputs: outputs}};
}

/**
 * 转账交易获取 inputs and outputs
 * @param transferInfo
 * @param balanceInfo
 * @returns {*}
 **/
export async function inputsOrOutputs(transferInfo, balanceInfo, fee) {
    // 如果资产是NULS手续费直接增加到amount
    if (fee && transferInfo.assetsChainId === chainID()) {
        transferInfo.fee = 100000;
    }
    let newAmount = Number(Plus(transferInfo.amount, transferInfo.fee));
    let newLocked = 0;
    let newNonce = balanceInfo.nonce;
    let newoutputAmount = transferInfo.amount;
    let newLockTime = 0;
    if (balanceInfo.balance < newAmount) {
        return {success: false, data: "Your balance is not enough."}
    }
    // if(!newNonce)
    // {
    //     newNonce="ffffffffffffffff";
    // }
    let inputs = [{
        address: transferInfo.fromAddress,
        assetsChainId: transferInfo.assetsChainId,
        assetsId: transferInfo.assetsId,
        amount: newAmount,
        locked: newLocked,
        nonce: newNonce
    }];
    // 如果资产不是NULS手续费需要一个单独From
    if (fee && transferInfo.assetsChainId !== chainID()) {
        inputs.push({
            address: transferInfo.fromAddress,
            assetsChainId: chainID(),
            assetsId: transferInfo.assetsId,
            amount: 100000,
            locked: newLocked,
            nonce: newNonce
        })
    }
    // 跨链资产转账时，才需要根据不同资产收取NULS手续费
    // if (transferInfo.assetsChainId !== chainID()) {
    //     inputs[0].amount = transferInfo.amount;
    //     //账户转出资产余额
    //     let nulsbalance = await getBalanceOrNonceByAddress(chainID(), 1, transferInfo.fromAddress);
    //     if (nulsbalance.data.balance < 100000) {
    //         console.log("余额小于手续费");
    //         return
    //     }
    //     inputs.push({
    //         address: transferInfo.fromAddress,
    //         assetsChainId: chainID(),
    //         assetsId: transferInfo.assetsId,
    //         amount: 100000,
    //         locked: newLocked,
    //         nonce: nulsbalance.data.nonce
    //     })
    // }
    let outputs = [{
        address: transferInfo.toAddress ? transferInfo.toAddress : transferInfo.fromAddress,
        assetsChainId: transferInfo.assetsChainId,
        assetsId: transferInfo.assetsId,
        amount: newoutputAmount,
        lockTime: newLockTime
    }];
    //console.log(inputs);
    //console.log(outputs);
    return {success: true, data: {inputs: inputs, outputs: outputs}};
}

/**
 * 获取地址信息根据地址
 * @param address
 * @returns {Promise<any>}
 */
export async function getAddressInfoByAddress(address) {
    return await post_nuls('/', 'getAccount', [address])
        .then((response) => {
            //console.log(response);
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result}
            } else {
                return {success: false, data: response}
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 获取地址的余额及nonce根据地址
 * @param assetChainId
 * @param assetId
 * @param address
 * @param divDecimals 是否去除默认精度
 * @returns {Promise<any>}
 */
export async function getBalanceOrNonceByAddress(assetChainId = 2, assetId = 1, address, divisionDecimals = 0) {
    return await post_nuls('/', 'getAccountBalance', [assetChainId, assetId, address])
        .then((response) => {
            //console.log(response);
            if (response.hasOwnProperty("result")) {
                let balance = response.result.balance;
                if (divisionDecimals === 1) {
                    balance = divDecimals(balance);
                }
                return {success: true, data: {balance: balance, nonce: response.result.nonce}}
            } else {
                return {success: false, data: response}
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 验证交易
 * @param txHex
 * @returns {Promise<any>}
 **/
export async function validateTx(txHex) {
    return await post_nuls('/', 'validateTx', [txHex])
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.error};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 广播交易
 * @param txHex
 * @returns {Promise<any>}
 **/
export async function broadcastTx(txHex) {
    return await post_nuls('/', 'broadcastTx', [txHex])
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.error};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 验证交易并广播
 * @param txHex
 * @returns {Promise<any>}
 **/
export async function validateAndBroadcast(txHex) {
    return await post_nuls('/', 'validateTx', [txHex])
        .then((response) => {
            console.log(response);
            if (response.hasOwnProperty("result")) {
                let newHash = response.result.value;
                return post_nuls('/', 'broadcastTx', [txHex])
                    .then((response) => {
                        if (response.hasOwnProperty("result")) {
                            return {success: true, hash: newHash};
                        } else {
                            return {success: false, data: response.error};
                        }
                    })
                    .catch((error) => {
                        return {success: false, data: error};
                    });
            } else {
                return {success: false, data: response.error};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 获取节点的委托列表
 * @param agentHash
 * @returns {Promise<any>}
 **/
export async function agentDeposistList(agentHash) {
    //todo 这个接口是临时处理，后面要换一个接口，否则超过100个委托会出问题
    return await post_nuls('/', 'getConsensusDeposit', [1, 100, agentHash])
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.error};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 获取合约代码构造函数
 * @param contractCodeHex
 * @returns {Promise<any>}
 */
export async function getContractConstructor(contractCodeHex) {
    return await post_nuls('/', 'getContractConstructor', [contractCodeHex])
        .then((response) => {
            //console.log(response);
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result.constructor};
            } else {
                return {success: false, data: response.error};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}


/**
 * 获取卖出挂单列表
 * @param order
 * @returns {Promise<any>}
 **/
export async function listOnSell(params) {
    return await get('/v1/order/', 'listOnSell', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 获取买入挂单列表
 * @param order
 * @returns {Promise<any>}
 **/
export async function listOnBuy(params) {
    return await get('/v1/order/', 'listOnBuy', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 获取当前委托订单
 * @param order
 * @returns {Promise<any>}
 **/
export async function queryMyCurrentOrder(params) {
    return await get('/v1/order/', 'queryMyCurrentOrder', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 获取历史委托订单
 * @param order
 * @returns {Promise<any>}
 **/
export async function queryMyHisOrder(params) {
    return await get('/v1/order/', 'queryMyHisOrder', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 获取订单明细
 * @param orderId
 * @returns {Promise<any>}
 **/
export async function getOrderDetail(params) {
    return await get('/v1/order/', 'getOrderDetail', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 获取访问Token
 * @param params
 * @returns {Promise<any>}
 **/
export async function getToken(params) {
    return await post('/v1/auth', '/getToken', params)
        .then((response) => {
            return response.result;
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 创建交易订单
 * @param order
 * @returns {Promise<any>}
 **/
export async function createOrder(params) {
    return await post('/v1/order/', 'createOrder', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 取消交易订单
 * @param orderId
 * @returns {Promise<any>}
 **/
export async function cancelOrder(params) {
    return await post('/v1/order/', 'cancelOrder', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 用户吃单
 * @param trade
 * @returns {Promise<any>}
 **/
export async function tradingOrder(params) {
    return await post('/v1/order/', 'tradingOrder', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

/**
 * 用户最终确认订单
 * @param trade
 * @returns {Promise<any>}
 **/
export async function confirmOrder(params) {
    return await post('/v1/order/', 'confirmOrder', params)
        .then((response) => {
            if (response.hasOwnProperty("result")) {
                return {success: true, data: response.result};
            } else {
                return {success: false, data: response.message};
            }
        })
        .catch((error) => {
            return {success: false, data: error};
        });
}

