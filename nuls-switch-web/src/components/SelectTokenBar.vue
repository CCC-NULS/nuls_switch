<template>
    <div class="tokenBar">
        <el-select v-model="fromTokenId" @change="changeFromTokenType">
            <el-option v-for="item in fromTokenOptions" :key="item.tokenId" :label="item.tokenSymbol"
                       :value="item.tokenId">
            </el-option>
        </el-select>
        —>
        <el-select v-model="toTokenId" @change="changeToTokenType">
            <el-option v-for="item in toTokenOptions" :key="item.tokenId" :label="item.tokenSymbol"
                       :value="item.tokenId">
            </el-option>
        </el-select>
    </div>
</template>

<script>
    export default {
        props: {
            allType: {
                type: Number,
                default: 0
            }
        },
        data() {
            return {
                fromTokenInfo: {},
                toTokenInfo: {},
                fromTokenId: '',
                //fromTokenSymbol: '',
                toTokenId: '',
                //toTokenSymbol: '',
                //源TOKEN下框列表
                fromTokenOptions: [],
                //目标TOKEN下框列表
                toTokenOptions: []
            }
        },
        created() {
            this.getTokenList();
        },
        methods: {
            changeFromTokenType() {
                //循环fromTokenOptions数组
                for (let i = 0; i < this.fromTokenOptions.length; i++) {
                    //如果当前点击的数组value等于他自己当前v-model
                    if (this.fromTokenOptions[i].tokenId === this.fromTokenId) {
                        this.fromTokenInfo = this.fromTokenOptions[i];
                        //this.fromTokenSymbol = this.fromTokenOptions[i].tokenSymbol;
                        //就把数组label绑定le2list
                        this.toTokenOptions = this.fromTokenOptions[i].switchTokenList;
                        // 加上默认全部类型
                        if (this.allType === 1) {
                            if (!this.toTokenOptions) {
                                this.toTokenOptions = [];
                            }
                            this.toTokenOptions.unshift({'tokenId': '', 'tokenSymbol': '全部'});
                        }
                        if (this.toTokenOptions.length > 0) {
                            this.toTokenInfo = this.toTokenOptions[0];
                            this.toTokenId = this.toTokenOptions[0].tokenId;
                            //this.toTokenSymbol = this.toTokenOptions[0].tokenSymbol;
                        } else {
                            this.toTokenId = '';
                            this.toTokenInfo = {};
                        }
                    }
                }
                this.$emit('change', this.fromTokenInfo, this.toTokenInfo);
            },
            changeToTokenType() {
                let obj = {};
                obj = this.toTokenOptions.find((item) => {
                    return item.tokenId === this.toTokenId;
                });
                this.toTokenInfo = obj;
                //this.toTokenSymbol = obj.tokenSymbol;
                this.$emit('change', this.fromTokenInfo, this.toTokenInfo);
            },
            /**
             * 获取所有支持兑换代币列表
             */
            getTokenList() {
                this.$get('/v1/token/list', '', {})
                    .then((response) => {
                        if (response.hasOwnProperty("result")) {
                            this.fromTokenOptions = response.result;
                            // 加上默认全部类型
                            if (this.allType === 1) {
                                this.fromTokenOptions.unshift({'tokenId': '', 'tokenSymbol': '全部'});
                            }
                            if (this.fromTokenOptions.length > 0) {
                                this.fromTokenInfo = this.fromTokenOptions[0];
                                this.fromTokenId = this.fromTokenOptions[0].tokenId;
                                //this.fromTokenSymbol = this.fromTokenOptions[0].tokenSymbol;
                                this.toTokenOptions = this.fromTokenOptions[0].switchTokenList;
                                // 加上默认全部类型
                                if (this.allType === 1) {
                                    if (!this.toTokenOptions) {
                                        this.toTokenOptions = [];
                                    }
                                    this.toTokenOptions.unshift({'tokenId': '', 'tokenSymbol': '全部'});
                                }
                                if (this.toTokenOptions.length > 0) {
                                    this.toTokenInfo = this.toTokenOptions[0];
                                    this.toTokenId = this.toTokenOptions[0].tokenId;
                                    //this.toTokenSymbol = this.toTokenOptions[0].tokenSymbol;
                                }
                            }
                            this.$emit('change', this.fromTokenInfo, this.toTokenInfo);
                        }
                    }).catch((error) => {
                    console.log(error)
                })
            },
        }
    }
</script>