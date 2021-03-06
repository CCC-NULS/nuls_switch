import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
const cn ={
  "nav":{
    "switch": "交易大厅",
    "myAsset": "资产列表",
    "myOrder": "历史委托",
    "tradeList": "交易记录",
    "login": "登录",
    "logout": "退出"
  },

  "public":{
    "operation": "操作",
    "display": "显示",
    "total": "共",
    "copy":"点击复制",
    "copySuccess":"已复制到剪切板",
    "copyError":"已复制到剪切板",
    "developedTips":"更多功能正在研发中...",
    "txList":"交易记录",
    "transactionList":"交易列表",
    "tokenTrading":"通证交易",
    "address":"地址",
    "locking":"锁定",
    "usable":"可用",
    "balance":"余额",
    "totals":"总计",
    "alias":"别名",
    "createAddress":"创建地址",
    "contractAddress":"合约地址",
    "fee":"手续费",
    "amount":"金额",
    "week":"周",
    "month":"月",
    "year":"年",
    "day":"天",
    "hour":"时",
    "minute":"分",
    "query":"查询",
    "errorPwd": "密码错误",
    "getBalanceFail": "获取账户余额失败:",
    "getBalanceException": "获取账户余额异常:",
    "queryMyHisOrderFail": "查询用户历史委托订单失败:",
    "queryMyHisOrderException": "查询用户历史委托订单异常:",
    "queryTradeByPageFail": "查询用户历史交易失败:",
    "queryTradeByPageException": "查询用户历史交易异常:",
    "startTime":"开始时间",
    "endTime":"结束时间",
    "all":"全部",
  },

  "bottom":{
    "website":"官网",
    "webWallet":"网页钱包",
    "community":"社区",
    "about":"问题反馈"
  },

  "error": {
    "10000":"成功",
    "10001":"失败",
    "10002":"系统错误",
    "10003":"数据解析错误",
    "10004":"贤成名称冲突",
    "10005":"未设置语言",
    "10006":"IO错误",
    "10007":"数据长度不正确",
    "10008":"错误的配置",
    "10009":"签名错误",
    "10010":"请求被拒绝",
    "10011":"数据长度过大",
    "10012":"参数错误",
    "10013":"参数不正确，有参数不能为空",
    "10014":"数据错误",
    "10015":"数据未找到",
    "ac_0000":"密码错误",
    "ac_0001":"账户不存在",
    "ac_0002":"账户已加密",
    "ac_0003":"账户已存在",
    "ac_0004":"地址错误",
    "ac_0005":"别名已存在",
    "ac_0006":"别名不存在",
    "ac_0007":"账户已设置别名",
    "ac_0008":"账户未加密",
    "ac_0009":"别名已存在",
    "ac_0010":"包含已加密的账户",
    "ac_0011":"包含未加密的账户",
    "ac_0012":"私钥格式不正确",
    "ac_0013":"删除别名失败",
    "ac_0014":"账户keystore不存在",
    "ac_0015":"账户keystore已损坏",
    "ac_0016":"别名格式错误",
    "ac_0017":"密码格式错误",
    "ac_0018":"账户加密失败",
    "ac_0019":"账户已加密并锁定",
    "ac_0020":"备注太长了",
    "ac_0021":"输入太小了",
    "ac_0022":"必须烧毁一个NULS",
    "ac_0023":"符号计数太大",
    "ac_0024":"不是当前链地址",
    "ac_0025":"是多重签名地址",
    "ac_0026":"不是多重签名地址",
    "ac_0027":"资产不存在",
    "ac_0028":"余额不足",
    "ac_0029":"余额不足",
    "ac_0030":"链不存在",
    "ac_0031":"CoinData数据不完整",
    "ac_0032":"交易不存在",
    "ac_0033":"未找到交易coinData",
    "ac_0034":"交易数据验证错误",
    "ac_0035":"交易类型错误",
    "ac_0036":"无效的交易或当前版本不可用",
    "ac_0037":"交易数据太大",
    "ac_0038":"交易转出人信息不存在",
    "ac_0039":"交易转入人信息不存在",
    "ac_0040":"链ID是错误的",
    "ac_0041":"资产ID是错误的",
    "ac_0042":"交易的签名地址不匹配",
    "ac_0043":"已签署交易的地址",
    "ac_0044":"交易有重复的帐户资产",
    "ac_0045":"保存别名错误",
    "ac_0046":"数量太小",
    "ac_0047":"黑洞地址禁止发起交易",
    "cc_0001":"跨链交易的转入地址和转出地址不应该属于同一条链",
    "cc_0002":"没有跨链交易的转出账户",
    "cc_0003":"跨链交易转出账户不是相同的链账户",
    "cc_0004":"跨链交易接收账户不是同一个链账户",
    "cc_0005":"对不起，您的信用额度很低",
    "cc_0006":"对于具有多签名帐户的跨链交易，只能有一个转出帐户。",
    "cc_0007":"多签名帐户跨链交易转出帐户不是多签名帐户",
    "cc_0008":"多签名帐户无法发布普通的跨链交易",
    "cc_0009":"此帐户不是加密帐户",
    "cc_0010":"跨链交易转出账户必须是本链账户",
    "cc_0011":"信用值不足",
    "cc_0012":"不能省略转出帐户和转入帐户。",
    "cc_0013":"接口调用失败",
    "cc_0014":"链不存在",
    "cc_0015":"支付数据验证错误",
    "cc_0016":"交易验证错误",
    "cc_0017":"交易数据验证错误",
    "cc_0018":"事务提交失败",
    "cc_0019":"事务回滚失败",
    "cc_0020":"不是跨链交易",
    "cm_0001":"余额不够",
    "cm_0002":"参数交易错误",
    "cm_0003":"不是跨链交易",
    "cm_1001":"已存在链ID",
    "cm_1002":"链ID必须大于0",
    "cm_1003":"未找到链",
    "cm_1004":"链地址错误",
    "cm_1005":"链状态不正确",
    "cm_1006":"链资产超过1",
    "cm_2000":"资产简称不能为空",
    "cm_2001":"资产简称长度必须小于5",
    "cm_2002":"资产简称已存在",
    "cm_2003":"JSON传输失败",
    "cm_2004":"恢复的资产必须超过0.9",
    "cm_2005":"资产ID已存在",
    "cm_2006":"资产名称不能为空",
    "cm_2007":"资产名称必须小于20",
    "cm_2008":"存款金额必须为200000",
    "cm_2009":"资产初始化编号必须大于10000",
    "cm_2010":"资产初始化编号必须小于100000000",
    "cm_2011":"资产小数位数必须大于4",
    "cm_2012":"资产小数位数必须小于8",
    "cm_2013":"用于计算的链不是用于注册资产的链",
    "cm_2014":"资产不存在",
    "cm_2015":"资产编号超过初始化数量",
    "cm_2016":"资产地址错误",
    "cm_2017":"交易hash错误",
    "cm_2018":"事务寄存器rpc调用错误",
    "cm_2019":"分类帐余额rpc调用错误",
    "cm_3000":"帐户验证错误",
    "cm_3001":"帐号签名数据错误。",
    "lg_0001":"参数错误",
    "lg_0002":"链初始化异常",
    "lg_1001":"孤儿交易",
    "lg_1002":"双花。",
    "lg_1003":"交易已存在",
    "lg_1010":"失败",
    "nw_0001":"网络消息错误",
    "nw_0002":"网络消息发送失败",
    "nw_0003":"网络消息发送异常",
    "nw_0004":"网络消息广播错误",
    "nw_0005":"对等节点断开连接",
    "nw_0006":"对等节点通道异常",
    "sc_0001":"合约执行错误",
    "sc_0002":"合约地址不存在",
    "sc_0003":"创建合约tx错误",
    "sc_0004":"非法合约地址",
    "sc_0005":"非合约交易",
    "sc_0006":"非合约交易的资金不能转移到合约地址",
    "sc_0007":"名称格式不正确",
    "sc_0008":"非NRC20合约",
    "sc_0009":"非查看方法",
    "sc_0010":"非法合约代码",
    "sc_0011":"重复的令牌名称",
    "sc_0012":"符号的格式不正确",
    "sc_0013":"合约已锁定",
    "sc_0014":"小数值的范围为0到18",
    "sc_0015":"totalSupply的值范围为1到2^256-1",
    "sc_0016":"价格的最小值是25",
    "sc_0017":"当余额不为0时无法删除合约",
    "sc_0018":"合约删除者必须是合约创建者",
    "sc_0019":"合约已被删除",
    "sc_0020":"超过合约调用的最大GAS限制",
    "sc_0021":"不执行视图方法",
    "sc_0022":"本合约不接受直接转入资产",
    "sc_0023":"合约方法不存在",
    "sc_0024":"无法锁定转移金额",
    "sc_0025":"转移到合约地址的余额不足",
    "sc_0026":"合约创建者不是交易创建者",
    "sc_0027":"合约调用者不是交易创建者",
    "sc_0028":"合约删除者不是交易创建者",
    "sc_0029":"合约删除者不是合约创建者",
    "sc_0030":"合约余额异常",
    "sc_0031":"接收方不是合约地址。",
    "sc_0032":"令牌余额不足",
    "sc_0033":"不同的模块注册了重复的cmd",
    "sc_0034":"接口不支持非字符串数组返回值",
    "sc_0035":"非法操作，无法传输令牌，无法发送事件，无法内部传输，无法在内部调用契约，无法生成新事务",
    "sc_0100":"余额不足",
    "sc_0101":"交易费用不对",
    "sc_0102":"传输量太小",
    "sc_0103":"交易不存在",
    "sc_0104":"密码错误",
    "sc_0105":"帐户不存在",
    "sc_0106":"地址错误",
    "sc_9999":"合约未知错误",
    "tx_0001":"交易哈希错误",
    "tx_0002":"地址与链不匹配",
    "tx_0003":"地址与链不匹配",
    "tx_0004":"未支付足够的手续费",
    "tx_0005":"资产ID是错误的",
    "tx_0006":"余额不存在",
    "tx_0007":"余额不存在",
    "tx_0008":"转出数据具有重复的帐户资产",
    "tx_0009":"转入数据有重复的帐户资产",
    "tx_0010":"转出数据不是同一个链",
    "tx_0011":"转入数据不是同一个链",
    "tx_0012":"未找到交易资产数据",
    "tx_0013":"交易已存在",
    "tx_0014":"交易不存在",
    "tx_0015":"反序列化事务错误",
    "tx_0016":"反序列化资产数据错误",
    "tx_0017":"交易的签名地址与交易地址不匹配",
    "tx_0018":"块高度更新在其余时间内无法重新打包",
    "tx_0019":"包获取事务超时",
    "tx_0020":"找不到链",
    "tx_0021":"无效的交易或当前版本不可用",
    "tx_0022":"交易数据验证错误",
    "tx_0023":"交易数据太大",
    "tx_0024":"帐户不存在",
    "tx_0025":"交易账本验证失败",
    "tx_0026":"孤儿交易",
    "tx_0027":"重复交易",
  },

  "switch":{
    "mustLogin":"您还未登录，请先登录",
    "myWantBuy":"我要买",
    "myWantSell":"我要卖",
    "buy":"买入",
    "sell":"卖出",
    "currentDeposit":"当前委托",
    "hisDeposit":"历史委托",
    "nullPrice": "请输入单价",
    "nullTxNum": "请输入交易数量",
    "mustNum": "请输入正确的数字格式，整数位最大长度11位，小数位最大长度8位",
    "mustNotZero":"必须大于0",
    "insufficientBalance": "对不起，余额不足",
    "txNumError": "交易数量必须大于0",
    "txNumLgRemainNumError": "交易数量不能大于订单剩余数量",
    "txNumLgBalanceNumError": "交易数量不能大于余额可交易数量",
    "createOrderSuccess": "挂单成功",
    "createOrderError": "挂单失败",
    "tradingOrderSuccess": "交易提交成功，等待对方确认",
    "tradingOrderError": "交易提交失败",
    "cancelOrderSuccess": "取消挂单成功",
    "cancelOrderError": "取消挂单失败",
    "confirmOrderSuccess": "确认订单成功，等待数据同步到区块链",
    "confirmOrderError": "确认订单失败",
    "cancelTradeSuccess": "取消交易成功",
    "cancelTradeError": "取消交易失败",
    "getOrderTradeSuccess": "查询订单交易明细成功",
    "getOrderTradeError": "查询订单交易明细失败",
    "orderTradeList": "订单交易详情"
  },

  "orderInfo":{
    "price":"价格",
    "num":"数量",
    "txNum":"交易数量",
    "remainNum":"剩余数量",
    "maxTxNum":"余额可交易量",
    "usable":"可用",
    "txType":"交易类型",
    "createTime":"时间",
    "depositTime":"委托时间",
    "tokenPair":"交易对",
    "totalAmount":"总额",
    "txAmount":"交易金额",
    "status":"状态",
  },

  "tradeInfo":{
    "price":"价格",
    "num":"数量",
    "txNum":"交易数量",
    "txType":"交易类型",
    "createTime":"时间",
    "tokenPair":"交易对",
    "totalAmount":"总额",
    "txAmount":"交易金额",
    "status":"状态",
    "outputNum":"转出数量",
    "inputNum":"转入数量",
  },

  "asset": {
    "asset0": "主网资产",
    "asset1": "跨链资产"
  },

  "assetsType":{
    "undefined": "",
    "0": "所有资产",
    "1": "普通资产",
    "2": "合约资产",
  },

  "orderStatus":{
    "10": "全部",
    "0": "未成交",
    "1": "部分成交",
    "2": "完全成交",
    "9": "撤销"
  },

  "tradeStatus":{
    "10": "全部",
    "0": "未确认",
    "1": "确认中",
    "2": "已确认",
    "3": "交易失败",
    "9": "已取消"
  },

  "assetItem": {
    "item0": "资产",
    "item1": "类型",
    "item2": "总额",
    "item3": "锁定",
    "item4": "可用",
    "item5": "时间",
    "item6": "金额",
    "item7": "解冻时间",
    "item8": "冻结原因",
    "item9": "余额",
    "item10": "状态",
  },

  "operateType":{
    "undefined": "",
    "operate": "操作",
    "cancel": "取消",
    "confirm": "确认",
    "continueTx": "连续交易",
    "priorTx": "优先交易",
    "details": "详情",
  },

  "user":{
    "welcomeNULS": "欢迎来到NULS-Switch,我们一起让区块链世界变得更简单",
    "createAccountTip1": "请设置密码用以导入账户、转账等重要行为验证",
    "createAccountTip2": "请认真保存账户密码，NULS-Switch不存储密码，也无法帮您找回，请务必牢记",
    "createAccount": "创建账户",
    "importAccount": "导入账户",
    "prikey": "明文私钥",
    "password": "密码",
    "confirmPwd": "确认密码",
    "nullPrikey": "请输入私钥",
    "nullPassword": "请输入密码",
    "againPassword": "请再次输入密码",
    "diffPassword": "两次输入密码不一致",
    "importAddressError": "导入地址错误",
    "errorFormatPassword": "请输入由字母和数字组合的8-20位密码",
    "YourAddress": "您的地址",
    "Backup": "备份",
    "PrikeyBackup": "私钥备份",
    "CompleteBackup": "完成备份",
    "SafeWarning": "安全警告:私钥未经加密，备份存在风险，请保存到安全的地方，建议使用Keystore进行备份",
  },

  ...zhLocale
};

export default cn
