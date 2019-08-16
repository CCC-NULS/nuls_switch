import {BigNumber} from 'bignumber.js'
import copy from 'copy-to-clipboard'
import {API_CHAIN_ID} from './../config'
import buffer from 'nuls-sdk-js/lib/utils/buffer';
import txs from 'nuls-sdk-js/src/model/txs';

/**
 * 10的N 次方
 * @param arg
 * @returns {BigNumber}
 * @constructor
 */
export function Power(arg) {
    let newPower = new BigNumber(10);
    return newPower.pow(arg);
}

/**
 * 减法
 * @param nu
 * @param arg
 * @returns {BigNumber}
 * @constructor
 */
export function Minus(nu, arg) {
    let newMinus = new BigNumber(nu);
    return newMinus.minus(arg);
}

/**
 * 乘法
 * @param nu
 * @param arg
 * @returns {BigNumber}
 * @constructor
 */
export function Times(nu, arg) {
    let newTimes = new BigNumber(nu);
    return newTimes.times(arg);
}

/**
 * 加法
 * @param nu
 * @param arg
 * @returns {BigNumber}
 * @constructor
 */
export function Plus(nu, arg) {
    let newPlus = new BigNumber(nu);
    return newPlus.plus(arg);
}

/**
 * 除法
 * @param nu
 * @param arg
 * @returns {BigNumber}
 * @constructor
 */
export function Division(nu, arg) {
    let newDiv = new BigNumber(nu);
    return newDiv.div(arg);
}

/**
 * 复制 copy
 * @param value
 */
export const copys = (value) => copy(value);

/**
 * 数字除以精度系数
 */
export function divDecimals(nu, decimals = 8) {
    let newNu = new BigNumber(Division(nu, Power(decimals)).toString());
    return newNu.toFormat().replace(/[,]/g, '');
}

/**
 * 数字乘以精度系数
 */
export function multiDecimals(nu, decimals = 8) {
    let newNu = new BigNumber(Times(nu, Power(decimals)).toString());
    return newNu.toFormat().replace(/[,]/g, '');
}

/**
 * 获取链ID
 * @returns {number}
 */
export function chainID() {
    return API_CHAIN_ID
}

/**
 * 获取chainId+number
 * @returns {string}
 */
export function chainIdNumber() {
    return 'chainId' + chainID();
}

/**
 * 获取地址列表或选择地址
 * @param type 0:地址列表，1:选中地址
 * @returns {*}
 */
export function addressInfo(type, address) {
    let addressList = localStorage.hasOwnProperty(chainIdNumber()) ? JSON.parse(localStorage.getItem(chainIdNumber())) : [];
    if (addressList) {
        if (type === 0) {
            return addressList
        } else {
            for (let item  of addressList) {
                if (address) {
                    if (item.address === address) {
                        return item
                    }
                } else {
                    if (item.selection) {
                        return item
                    }
                }
            }
        }
    } else {
        return addressList
    }
}

/**
 * 地址信息写入localStorage
 */
export function localStorageByAddressInfo(newAddressInfo) {
    let addressList = [];
    let newAddressList = [];
    newAddressList.push(newAddressInfo);
    let newArr = addressInfo(0);
    if (newArr.length !== 0) {
        let ifAddress = false;
        for (let item of newArr) {
            if (item.address === newAddressInfo.address) {
                item.aesPri = newAddressInfo.aesPri;
                item.pub = newAddressInfo.pub;
                ifAddress = true
            }
            if (item.selection) {
                newAddressList[0].selection = false;
            }
        }
        if (ifAddress) {
            addressList = [...newArr]
        } else {
            addressList = [...newArr, ...newAddressList]
        }
    } else {
        newAddressInfo.selection = true;
        addressList.push(newAddressInfo);
    }
    //console.log(addressList);
    localStorage.setItem(chainIdNumber(), JSON.stringify(addressList));
}

/**
 * 超长数字显示
 * @param nu
 * @param powerNu
 * @returns {string}
 */
export function langNumber(nu, powerNu) {
    let newNu = new BigNumber(Division(nu, powerNu).toString());
    return newNu.toFormat().replace(/[,]/g, '');
}

/**
 * 字符串中间显示....
 * @param string
 * @param leng
 * @returns {*}
 */
export function superLong(string, leng) {
    if (string && string.length > 10) {
        return string.substr(0, leng) + "...." + string.substr(string.length - leng, string.length);
    } else {
        return string;
    }
}

/**
 * 连接跳转
 * @param name
 * @param parameter {}
 * @param type 0:路由跳转 1：连接跳转（浏览器、其他地址）
 */
export function connect(name, parameter, type = 0) {
    if (type === 0) {
        this.$router.push({
            name: name,
            query: parameter
        });
    } else {
        //shell.openExternal(newUrl);
        window.open(name, '_blank');
    }
}

/**
 * 根据不同时区显示时间
 * @param time
 * @returns {*}
 */
export function getLocalTime(time) {
    if (typeof time !== 'number') return;
    let d = new Date();
    let offset = d.getTimezoneOffset() * 60000;
    let localUtc = new Date().getTimezoneOffset() / 60;
    let utcTime;
    if (localUtc > 0) {
        utcTime = time - offset;
    } else {
        utcTime = time + offset;
    }
    let localTime = utcTime + 3600000 * Math.abs(localUtc);
    return new Date(localTime);
}

/**
 * 数字保留几位小数显示
 */
export function toFixed(num, decimals = 8) {
    let newNu = Math.round(num * Power(decimals)) / Power(decimals);
    return newNu;
}

/**
 * 交易反序列化
 * @param txHex
 */
export function deserializeTx(txHex) {
    //转为字节
    let txBuffer = buffer.hexToBuffer(txHex);
    let transaction = new txs.TransferTransaction();
    //读取位置
    let offset = 0;
    //读取type
    transaction.type = txBuffer.readInt16LE(offset);
    offset += 2;
    //交易时间
    transaction.time = txBuffer.readInt32LE(offset);
    offset += 4;
    let varInt = new VarInt();
    varInt.parse(txBuffer, offset);
    //备注
    if (varInt.value == 0) {
        transaction.remark = null;
    }
    offset += varInt.originallyEncodedSize;
    //交易数据
    varInt.parse(txBuffer, offset);
    offset += varInt.originallyEncodedSize;
    transaction.txData = txBuffer.slice(offset, varInt.value);
    offset += varInt.value;
    //coinData数据
    varInt.parse(txBuffer, offset);
    offset += varInt.originallyEncodedSize;
    transaction.coinData = txBuffer.slice(offset, offset + varInt.value);
    //签名数据
    offset += varInt.value;
    varInt.parse(txBuffer, offset);
    offset += varInt.originallyEncodedSize;
    transaction.signatures = txBuffer.slice(offset, offset + varInt.value);
    return transaction;
}

/**
 * 读取交易数据
 * @constructor
 */
let VarInt = function () {
    this.value = null;
    this.originallyEncodedSize = 0;
    this.parse = function (buf, offset) {
        let first = 0xFF & buf[offset];
        if (first < 253) {
            this.value = first;
            // 1 entity byte (8 bits)
            this.originallyEncodedSize = 1;
        } else if (first == 253) {
            this.value = (0xFF & buf[offset + 1]) | ((0xFF & buf[offset + 2]) << 8);
            // 1 marker + 2 entity bytes (16 bits)
            this.originallyEncodedSize = 3;
        } else if (first == 254) {
            this.value = buf.readUInt32LE(offset + 1);
            // 1 marker + 4 entity bytes (32 bits)
            this.originallyEncodedSize = 5;
        } else {
            this.value = buf.readBigInt64LE(offset + 1);
            // 1 marker + 8 entity bytes (64 bits)
            this.originallyEncodedSize = 9;
        }
    };
};
