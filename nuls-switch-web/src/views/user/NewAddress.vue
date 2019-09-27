<template>
    <div class="new-address bg-gray">
        <div class="bg-white">
            <h3 class="title pt_10">{{$t('user.welcomeNULS')}}</h3>
        </div>
        <div class="tab bg-white w1200 mt_30">
            <div class="tips bg-gray w630">
                <p class="font14"><i></i>{{$t('user.createAccountTip1')}}</p>
                <p class="font14"><i></i>{{$t('user.createAccountTip2')}}</p>
            </div>

            <el-form :model="newAddressForm" status-icon :rules="newAddressRules" ref="newAddressForm"
                     class="pass-form w630">
                <el-form-item :label="$t('user.password')" prop="pass">
                    <el-input type="password" v-model="newAddressForm.pass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item :label="$t('user.confirmPwd')" prop="checkPass">
                    <el-input type="password" v-model="newAddressForm.checkPass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item class="form-bnt">
                    <el-button type="success" @click="submitForm('newAddressForm')">{{$t('user.createAccount')}}</el-button>
                    <el-button type="text" @click="toUrl('importAddress')">{{$t('user.importAccount')}}</el-button>
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

    export default {
        data() {
            let validatePass = (rule, value, callback) => {
                let patrn = /^(?![\d]+$)(?![a-zA-Z]+$)[\da-zA-Z]{8,20}$/;
                if (value === '') {
                    callback(new Error(this.$t('user.nullPassword')));
                } else if (!patrn.exec(value)) {
                    callback(new Error(this.$t('user.errorFormatPassword')));
                } else {
                    if (this.newAddressForm.checkPass !== '') {
                        this.$refs.newAddressForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            let validateCheckPass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error(this.$t('user.againPassword')));
                } else if (value !== this.newAddressForm.pass) {
                    callback(new Error(this.$t('user.diffPassword')));
                } else {
                    callback();
                }
            };
            return {
                newAddressForm: {
                    pass: '',
                    checkPass: '',
                },
                newAddressRules: {
                    pass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validateCheckPass, trigger: 'blur'}
                    ]
                },
                newAddressInfo: {},//创建地址信息
            };
        },
        methods: {

            /**
             *  创建账户提交
             * @param formName
             **/
            async submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.newAddressInfo = nuls.newAddress(API_CHAIN_ID, this.newAddressForm.pass);
                        this.getAddressInfoByAddress(this.newAddressInfo.address);
                    } else {
                        return false;
                    }
                });
            },

            //获取账户信息根据创建的地址
            async getAddressInfoByAddress(address) {
                //获取访问的Token
                let tokenStorage = await this.getToken(this.newAddressInfo);
                let addressInfo = await getAddressInfoByAddress(address);
                let newAdressInfo = {...this.newAddressInfo, ...addressInfo.data, ...tokenStorage};
                if (addressInfo.success) {
                    localStorage.setItem('accountInfo', JSON.stringify(newAdressInfo));
                    localStorageByAddressInfo(newAdressInfo);
                    this.$router.push({
                        name: 'backupsAddress'
                    });
                } else {
                    this.$message({
                        message: "创建地址错误: " + addressInfo.data.error.message,
                        type: 'error',
                        duration: 2000
                    });
                }
            },

            async getToken(newAddressInfo) {
                let pri = nuls.decrypteOfAES(newAddressInfo.aesPri, this.newAddressForm.pass);
                let data = "Switch Get Token"
                let dataHex = this.stringToHex(data);
                let signatureHex = nuls_api_sdk.signature(dataHex, pri);
                let params = {
                    "dataHex": dataHex,
                    "publicKeyHex": newAddressInfo.pub,
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
    @import "./../../assets/css/style";

    .new-address {
        .bg-white {
        }

        .tab {
            .tips {
                margin: 40px auto;
                padding: 20px 30px;

                p {
                    line-height: 24px;

                    i {
                        width: 5px;
                        height: 5px;
                        display: block;
                        float: left;
                        margin: 9px 10px 0 0;
                        border-radius: 5px;
                        background: #000000;
                    }
                }
            }

            .pass-form {

            }

        }
    }

</style>
