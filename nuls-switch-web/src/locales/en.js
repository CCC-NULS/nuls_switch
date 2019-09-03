import enLocale from 'element-ui/lib/locale/lang/en'

const en = {
  "nav": {
    "switch": "SwitchHall",
    "myAsset": "Assets",
    "myOrder": "HistoryOrder",
    "login": "Login",
    "logout": "Logout"
  },

  "public": {
    "operation": "Operation",
    "display": "Display",
    "total": "Total",
    "copy": "Click copy",
    "copySuccess": "Copied to clipboard",
    "copyError": "Failed to copy to clipboard",
    "developedTips": "More features are being developed...",
    "txList": "Transaction history",
    "transactionList": "Transaction list",
    "tokenTrading": "Token transaction",
    "address": "Account",
    "locking": "locking",
    "usable": "Usable",
    "balance": "Balance",
    "totals": "Totals",
    "alias": "Alias",
    "createAddress": "Agent address",
    "contractAddress": "Contract address",
    "fee": "Fee",
    "amount": "Amount",
    "week": "Week",
    "month": "Month",
    "year": "Year",
    "day": "Day",
    "hour": "Hour",
    "minute": "Minute",
    "query":"Query",
    "errorPwd": "Error password",
    "getBalanceFail": "Failed to query account balance",
    "getBalanceException": "Exception to query account balance",
    "queryMyHisOrderFail": "Failed to query user history order",
    "queryMyHisOrderException": "Exception to query user history order",
    "startTime":"StartTime",
    "endTime":"EndTime",
    "all":"All",
  },

  "bottom": {
    "website": "Official Website",
    "webWallet": "Light wallet",
    "community": "Community",
    "about": "Bug Report"
  },

  "switch":{
    "mustLogin":"You are not logged in yet. Please log in first",
    "myWantBuy":"I want buy",
    "myWantSell":"I want sell",
    "buy":"Buy",
    "sell":"Sell",
    "currentDeposit":"CurrentDeposit",
    "hisDeposit":"HisDeposit",
    "nullPrice": "Please enter unit price",
    "nullTxNum": "Please enter the number of transactions",
    "mustNum": "Please enter the correct digital format, the maximum length of integers is 11 bits, and the maximum length of decimal is 8 bits",
    "mustNotZero":"Must be greater than 0",
    "insufficientBalance": "Sorry, the balance is insufficient",
    "txNumError": "The number of transactions must be greater than 0",
    "txNumLgRemainNumError": "The number of transactions should not be greater than the remaining number of orders",
    "txNumLgBalanceNumError": "The number of transactions should not be greater than the number of balances that can be traded",
    "createOrderSuccess": "Successful to create an order",
    "createOrderError": "Failure to create an order",
    "tradingOrderSuccess": "Transaction submit is successful, waiting for confirmation",
    "tradingOrderError": "Transaction submit failed",
    "cancelOrderSuccess": "Cancel Creation Order Successfully",
    "cancelOrderError": "Failed to cancel creation order",
    "confirmOrderSuccess": "Confirm that the order is successful and wait for data to be synchronized to the block chain",
    "confirmOrderError": "Failure to confirm order",
    "getOrderTradeSuccess": "Successful to query order transaction details",
    "getOrderTradeError": "Failure to query order transaction details"
  },

  "orderInfo":{
    "price":"Price",
    "num":"Number",
    "txNum":"TransactionNumber",
    "remainNum":"RemainNumber",
    "maxTxNum":"BalanceTradable",
    "usable":"Usable",
    "txType":"TransactionType",
    "createTime":"CreateTime",
    "depositTime":"DepositTime",
    "tokenPair":"TokenPair",
    "totalAmount":"TotalAmount",
    "txAmount":"TransactionAmount",
    "status":"Status"
  },

  "asset": {
    "asset0": "Main-NetworkAssets",
    "asset1": "Cross-ChainAssets"
  },

  "assetsType":{
    "undefined": "",
    "0": "AllAssets",
    "1": "NormalAssets",
    "2": "ContractAssets",
  },

  "orderStatus":{
    "10": "All",
    "0": "NoDeal",
    "1": "PartDeal",
    "2": "CompleteDeal",
    "9": "Cancelled"
  },

  "tradeStatus":{
    "0": "Unconfirmed",
    "1": "OnConfirme",
    "2": "Confirmed",
    "3": "TransactionFailure",
    "9": "Cancelled"
  },

  "assetItem": {
    "item0": "Assets",
    "item1": "Type",
    "item2": "TotalAmount",
    "item3": "Lock",
    "item4": "Usable",
    "item5": "Time",
    "item6": "Amount",
    "item7": "UnfreezeTime",
    "item8": "LockReason",
    "item9": "Balance",
    "item10": "Status",
  },

  "operateType":{
    "undefined": "",
    "operate": "Operation",
    "cancel": "Cancel",
    "confirm": "Confirm",
    "details": "Details",
  },

  "user":{
    "createAddress": "create address",
    "importAccount": "import account",
    "prikey": "private key",
    "password": "password",
    "confirmPwd": "confirm password",
    "nullPrikey": "please enter a private key",
    "nullPassword": "please enter a password",
    "againPassword": "please enter your password again",
    "diffPassword": "different passwords are entered twice",
    "importAddressError": "import address error",
  },
  ...enLocale

};
export default en
