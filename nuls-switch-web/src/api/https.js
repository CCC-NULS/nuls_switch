import axios from 'axios'
import * as config from './../config.js'
import {chainID} from './util'

axios.defaults.timeout = config.API_TIME;
axios.defaults.baseURL = config.API_URL;
axios.defaults.headers.post['Content-Type'] = 'application/json';

//axios.defaults.headers.common['Authentication'] = 12345;//localStorage.getItem('accountInfo').token;
/**
 * 封装post请求，调用nuls2.0后端api
 * Encapsulation post method
 * @param url
 * @param methodName
 * @param data
 * @returns {Promise}
 */
export function post_nuls(url, methodName, data = []) {
    return new Promise((resolve, reject) => {
        data.unshift(chainID());
        const params = {"jsonrpc": "2.0", "method": methodName, "params": data, "id": 5898};
        /* console.log(url);
         console.log(params);*/
        url = config.NULS_API_URL;
        axios.post(url, params)
            .then(response => {
                resolve(response.data)
            }, err => {
                reject(err)
            })
    })
}

/**
 * 封装post请求，调用switch后端api
 * Encapsulation post method
 * @param url
 * @param methodName
 * @param data
 * @returns {Promise}
 */
export function post(url, methodName, params = {}) {
    return new Promise((resolve, reject) => {
        let token = localStorage.getItem('accountInfo') != null ? JSON.parse(localStorage.getItem('accountInfo')).token : '';
        let headers = {"headers": {"Authorization": token}};
        params = {...params, ...headers};
        console.log(params);
        url += methodName;
        axios.post(url, params)
            .then(response => {
                resolve(response.data)
            }, err => {
                reject(err)
            })
    })
}

/**
 * 封装post请求，调用switch后端api
 * Encapsulation post method
 * @param url
 * @param methodName
 * @param data
 * @returns {Promise}
 */
export function get(url, methodName, data = {}) {
    return new Promise((resolve, reject) => {
        let params = {"params": data};
        let token = localStorage.getItem('accountInfo') != null ? JSON.parse(localStorage.getItem('accountInfo')).token : '';
        let headers = {"headers": {"Authorization": token}};
        params = {...params, ...headers};
        url += methodName;
        //console.log(params);
        axios.get(url, params)
            .then(response => {
                resolve(response.data)
                //console.log(response.data)
            }, err => {
                reject(err)
            })
    })
}
