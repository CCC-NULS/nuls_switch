<template>
    <div class="import-address bg-gray">
        <div class="bg-white">
            <BackBar :backTitle="$t('user.createAccount')"></BackBar>
            <h3 class="title tc mt_f_20 pt_10">{{$t('user.importAccount')}}</h3>
        </div>
        <div class="tab bg-white w1200 mt_30">
            <el-form :model="importForm" :rules="importRules" ref="importForm" status-icon class="import-form w630">
                <el-form-item :label="$t('user.prikey')" prop="keys">
                    <el-input type="textarea" v-model.trim="importForm.keys" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item :label="$t('user.password')" prop="pass">
                    <el-input v-model="importForm.pass" type="password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item :label="$t('user.confirmPwd')" prop="checkPass">
                    <el-input v-model="importForm.checkPass" type="password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item class="form-bnt">
                    <el-button type="success" @click="submitForm('importForm')">{{$t('user.importAccount')}}</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import nuls from 'nuls-sdk-js'
    import nuls_api_sdk from 'nuls-sdk-js/lib/api/sdk';
    import {API_CHAIN_ID} from '@/config'
    import {getAddressInfoByAddress, getToken} from '@/api/requestData'
    import {localStorageByAddressInfo} from '@/api/util'
    import BackBar from '@/components/BackBar'

    export default {
        name: "import-address",
        data() {
            let validateKeys = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error(this.$t('user.nullPrikey')));
                } else {
                    callback();
                }
            };
            let validatePass = (rule, value, callback) => {
                let patrn = /^(?![\d]+$)(?![a-zA-Z]+$)[\da-zA-Z]{8,20}$/;
                if (value === '') {
                    callback(new Error(this.$t('user.nullPassword')));
                } else if (!patrn.exec(value)) {
                    callback(new Error(this.$t('user.errorFormatPassword')));
                } else {
                    if (this.importForm.checkPass !== '') {
                        this.$refs.importForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            let validateCheckPass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error(this.$t('user.againPassword')));
                } else if (value !== this.importForm.pass) {
                    callback(new Error(this.$t('user.diffPassword')));
                } else {
                    callback();
                }
            };
            return {
                importForm: {
                    keys: '',
                    pass: '',
                    checkPass: ''
                },
                importRules: {
                    keys:
                        [
                            {validator: validateKeys, trigger: 'blur'}
                        ],
                    pass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validateCheckPass, trigger: 'blur'}
                    ]
                },
                importAddressInfo: {},
            };
        },
        components: {
            BackBar
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.importAddressInfo = nuls.importByKey(API_CHAIN_ID, this.importForm.keys, this.importForm.pass);
                        this.getAddressInfoByAddress(this.importAddressInfo.address);
                    } else {
                        return false;
                    }
                });
            },
            //获取账户信息根据创建的地址
            async getAddressInfoByAddress(address) {
                let addressInfo = await getAddressInfoByAddress(address);
                //获取访问的Token
                let tokenStorage = await this.getToken(this.importAddressInfo);
                let newAdressInfo = {...this.importAddressInfo, ...addressInfo.data, ...tokenStorage};
                if (addressInfo.success) {
                    localStorage.setItem('accountInfo', JSON.stringify(newAdressInfo));
                    localStorageByAddressInfo(newAdressInfo);
                    this.$router.push({
                        name: 'backupsAddress'
                    });
                } else {
                    this.$message({
                        message: this.$t('user.importAddressError') + ", " + addressInfo.data.error.message,
                        type: 'error',
                        duration: 2000
                    });
                }
            },

            async getToken(importAddressInfo) {
                let pri = nuls.decrypteOfAES(importAddressInfo.aesPri, this.importForm.pass);
                let data = "Switch Get Token"
                let dataHex = this.stringToHex(data);
                let signatureHex = nuls_api_sdk.signature(dataHex, pri);
                let params = {
                    "dataHex": dataHex,
                    "publicKeyHex": importAddressInfo.pub,
                    "signatureHex": signatureHex
                };
                let token = await getToken(params);
                let tokenStorage = {"token": token};
                return tokenStorage;
            },
            //TODO 需要提取到公共方法中
            stringToHex(str) {
                var val = "";
                for (var i = 0; i < str.length; i++)
                    val += str.charCodeAt(i).toString(16);
                return val;
            },
            /**
             * 连接跳转
             * @param name
             */
            toUrl(name) {
                this.$router.push({
                    name: name,
                })
            },
        }
    }
</script>

<style lang="less">
    .import-address {
        .import-form {
            margin: 60px auto 0;
        }
    }

</style>
