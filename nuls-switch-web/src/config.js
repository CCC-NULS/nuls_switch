//开发模式
export const IS_DEV = process.env.NODE_ENV !== 'production';
//链ID
export const API_CHAIN_ID = 2;
//燃烧地址
export const API_BURNING_ADDRESS = 'tNULSeBaMgLW3Wrt8Eh8Av5MinETdiXhfGg61u';
//正式、测试网络的api
export let NULS_API_URL = !IS_DEV ? 'http://192.168.1.40:18003/' : 'http://apitn1.nulscan.io/';
export let API_URL = IS_DEV ? 'http://127.0.0.1:8010/' : 'http://127.0.0.1:8010/';
//请求最迟时间
export const API_TIME = IS_DEV ? '5000' : '8000';
