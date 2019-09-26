//开发模式
export const IS_DEV = process.env.NODE_ENV !== 'production';
//主网链ID
export const API_CHAIN_ID = 2;
//主网链主资产ID
export const API_ASSETS_ID = 1;
//燃烧地址
export const API_BURNING_ADDRESS = 'tNULSeBaMgLW3Wrt8Eh8Av5MinETdiXhfGg61u';
//正式、测试网络的api
export let NULS_API_URL = IS_DEV ? 'http://beta.public1.nuls.io' : 'http://public1.nuls.io';
export let API_URL = IS_DEV ? 'http://127.0.0.1:8010/' : './';
//请求最迟时间
export const API_TIME = IS_DEV ? 15000 : 20000;
