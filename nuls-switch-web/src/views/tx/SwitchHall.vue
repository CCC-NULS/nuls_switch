<template>
    <div class="switch-hall bg-gray">
        <div class="bg-white">
            <div class="title font24 w1200">
                <SelectTokenBar @change="changeTokenType"></SelectTokenBar>
            </div>
        </div>
        <div class="top w1200">
            <div class="order">
                <!-- 买入挂单 -->
                <div class="top-left fl">
                    <el-form :model="buyTokenOrderForm" :rules="buyTokenOrderRules" ref="buyTokenOrderForm">
                    <h3 class="tabs_title tabs_header capitalize">{{$t('switch.myWantBuy')}}</h3>
                    <div class="order_left">
                        <el-row class="order_row">
                            <div class="order_label"><span>{{$t('orderInfo.price')}}：</span></div>
                            <div class="order_input">
                                <el-form-item prop="price">
                                    <el-input type="input" v-model="buyTokenOrderForm.price" :placeholder="$t('switch.nullPrice')"></el-input>
                                </el-form-item>
                            </div>
                            <div class="order_span"><span>{{this.toTokenInfo.tokenSymbol}}</span></div>
                        </el-row>
                        <el-row class="order_row">
                            <div class="order_label"><span>{{$t('orderInfo.num')}}：</span></div>
                            <div class="order_input">
                                <el-form-item prop="totalNum">
                                    <el-input type="input" v-model="buyTokenOrderForm.totalNum" :placeholder="$t('switch.nullTxNum')"></el-input>
                                </el-form-item>
                            </div>
                            <div class="order_span"><span>{{this.fromTokenInfo.tokenSymbol}}</span></div>
                        </el-row>
                        <el-row class="order_row">
                            <div class="order_label"><span>{{$t('orderInfo.usable')}}：</span></div>
                            <div class="order_label"><span>{{toBalanceInfo.balance}}</span></div>
                            <div class="order_label"><span>{{this.toTokenInfo.tokenSymbol}}</span></div>
                        </el-row>
                        <el-row class="order_btn_row">
                            <el-button type="primary" @click="submitCreateOrder('buyTokenOrderForm',1)">{{$t('switch.buy')}}</el-button>
                        </el-row>
                    </div>
                    </el-form>
                </div>
                <!-- 买出挂单 -->
                <div class="top-left fl">
                    <el-form :model="sellTokenOrderForm" :rules="sellTokenOrderRules" ref="sellTokenOrderForm">
                    <h3 class="tabs_title tabs_header capitalize">{{$t('switch.myWantSell')}}</h3>
                    <div class="order_left">
                        <el-row class="order_row">
                            <div class="order_label"><span>{{$t('orderInfo.price')}}：</span></div>
                            <div class="order_input">
                                <el-form-item prop="price">
                                    <el-input type="input" v-model="sellTokenOrderForm.price" :placeholder="$t('switch.nullPrice')"></el-input>
                                </el-form-item>
                            </div>
                            <div class="order_span"><span>{{this.toTokenInfo.tokenSymbol}}</span></div>
                        </el-row>
                        <el-row class="order_row">
                            <div class="order_label"><span>{{$t('orderInfo.num')}}：</span></div>
                            <div class="order_input">
                                <el-form-item prop="totalNum">
                                    <el-input type="input" v-model="sellTokenOrderForm.totalNum" :placeholder="$t('switch.nullTxNum')"></el-input>
                                </el-form-item>
                            </div>
                            <div class="order_span"><span>{{this.fromTokenInfo.tokenSymbol}}</span></div>
                        </el-row>
                        <el-row class="order_row">
                            <div class="order_label"><span>{{$t('orderInfo.usable')}}：</span></div>
                            <div class="order_label"><span>{{fromBalanceInfo.balance}}</span></div>
                            <div class="order_label"><span>{{this.fromTokenInfo.tokenSymbol}}</span></div>
                        </el-row>
                        <el-row class="order_btn_row">
                            <el-button type="primary" @click="submitCreateOrder('sellTokenOrderForm',2)">{{$t('switch.sell')}}</el-button>
                        </el-row>
                    </div>
                    </el-form>
                </div>
            </div>
            <!-- 挂单列表 -->
            <div class="top-right fr">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane :label="$t('switch.myWantBuy')" name="buyTab">
                        <el-table :data="buyList" stripe border
                                  v-loading="buyListLoading">
                            <el-table-column label="" width="30">
                            </el-table-column>
                            <el-table-column :label="$t('orderInfo.price')" width="100" align="left">
                                <template slot-scope="scope">{{ scope.row.price }}</template>
                            </el-table-column>
                            <el-table-column :label="$t('orderInfo.num')" width="150" align="left">
                                <template slot-scope="scope">{{ scope.row.txNum }}/{{ scope.row.totalNum }}</template>
                            </el-table-column>
                            <el-table-column :label="$t('orderInfo.remainNum')" width="130" align="left">
                                <template slot-scope="scope">{{ scope.row.remainNum }}</template>
                            </el-table-column>
                            <el-table-column :label="$t('switch.buy')" width="80" align="left">
                                <template slot-scope="scope"><el-button type="primary" @click="buyBtnClick(scope.row)">{{$t('switch.buy')}}</el-button></template>
                            </el-table-column>
                        </el-table>
                        <div class="paging">
                            <el-pagination class="pages" background layout="total,prev, pager, next, jumper"
                                           v-show="buyListPager.total > sellListPager.rows"
                                           :total="buyListPager.total"
                                           :current-page.sync="buyListPager.page"
                                           :pager-count=5
                                           :page-size="buyListPager.rows" @current-change="pagesBuyList">
                            </el-pagination>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane :label="$t('switch.myWantSell')" name="sellTab">
                        <el-table :data="sellList" stripe border
                                  v-loading="sellListLoading">
                            <el-table-column label="" width="30">
                            </el-table-column>
                            <el-table-column :label="$t('orderInfo.price')" width="100" align="left">
                                <template slot-scope="scope">{{ scope.row.price }}</template>
                            </el-table-column>
                            <el-table-column :label="$t('orderInfo.num')" width="150" align="left">
                                <template slot-scope="scope">{{ scope.row.txNum }}/{{ scope.row.totalNum }}</template>
                            </el-table-column>
                            <el-table-column :label="$t('orderInfo.remainNum')" width="130" align="left">
                                <template slot-scope="scope">{{ scope.row.remainNum }}</template>
                            </el-table-column>
                            <el-table-column :label="$t('switch.sell')" width="80" align="left">
                                <template slot-scope="scope"><el-button type="primary" @click="sellBtnClick(scope.row)">{{$t('switch.sell')}}</el-button></template>
                            </el-table-column>
                        </el-table>
                        <div class="paging">
                            <el-pagination class="pages" background layout="total,prev, pager, next, jumper"
                                           v-show="sellListPager.total > sellListPager.rows"
                                           :total="sellListPager.total"
                                           :current-page.sync="sellListPager.page"
                                           :pager-count=5
                                           :page-size="sellListPager.rows" @current-change="pagesSellList">
                            </el-pagination>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
        <div class="cb"></div>
        <!-- 当前委托列表 -->
        <div class="bottoms w1200 cb">
            <el-tabs v-model="depositActiveName">
                <el-tab-pane :label="$t('switch.currentDeposit')" name="depositTab">
                    <el-table :data="depositList" stripe border class="mt_0 el-deposit-table"
                              v-loading="depositListLoading">
                        <el-table-column label="" width="30">
                        </el-table-column>
                        <el-table-column :label="$t('orderInfo.createTime')" width="170" align="left">
                            <template slot-scope="scope">{{ scope.row.createTime }}</template>
                        </el-table-column>
                        <el-table-column :label="$t('orderInfo.txType')" width="120" align="left">
                            <template slot-scope="scope">
                                <span v-if="scope.row.txType ==1">{{$t('switch.buy')}}</span>
                                <span v-else>{{$t('switch.sell')}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column :label="$t('orderInfo.tokenPair')" width="170" align="left">
                            <template slot-scope="scope">{{ scope.row.tokenPair }}</template>
                        </el-table-column>
                        <el-table-column :label="$t('orderInfo.price')" width="170" align="left">
                            <template slot-scope="scope">{{ scope.row.price }}</template>
                        </el-table-column>
                        <el-table-column :label="$t('orderInfo.num')" width="170" align="left">
                            <template slot-scope="scope">{{ scope.row.txNum }}/{{ scope.row.totalNum }}</template>
                        </el-table-column>
                        <el-table-column :label="$t('orderInfo.totalAmount')" width="170" align="left">
                            <template slot-scope="scope">{{ scope.row.totalAmount }}</template>
                        </el-table-column>
                        <el-table-column :label="$t('orderInfo.status')" width="180" align="left">
                            <template slot-scope="scope">
                                <el-button type="text" size="mini" @click="cancelOrderClick(scope.row.orderId)">{{$t('operateType.cancel')}}</el-button>
                                <span v-if="scope.row.status==1"> | </span>
                                <el-button type="text" size="mini" @click="getOrderTradeClick(scope.row.orderId, scope.row.price)" v-if="scope.row.status==1">{{$t('operateType.confirm')}}</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="paging">
                        <el-pagination class="pages" background layout="total,prev, pager, next, jumper"
                                       v-show="depositListPager.total > depositListPager.rows"
                                       :total="depositListPager.total"
                                       :current-page.sync="depositListPager.page"
                                       :pager-count=5
                                       :page-size="depositListPager.rows" @current-change="pagesDepositList">
                        </el-pagination>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>

        <!-- 买入/卖出 -->
        <el-dialog :title="buyTradeTitle" :visible.sync="buyTokenVisible" top="30vh"
                   class="trade-dialog"
                   :close-on-click-modal="false"
                   :close-on-press-escape="false"
                   @submit.native.prevent
                   @close="buyTokenFormClose">
            <el-form ref="buyTokenForm" :model="buyTokenForm" :rules="tradeTokendRules">
                <div class="tradeToken">
                    <el-row class="trade_row">
                        <div class="trade_label"><span>{{$t('orderInfo.num')}}：</span></div>
                        <div class="trade_input">
                            <el-form-item prop="txNum">
                                <el-input type="input" v-model="buyTokenForm.txNum" :maxlength="10" :placeholder="$t('switch.nullTxNum')"></el-input>
                            </el-form-item>
                        </div>
                    </el-row>
                    <el-row class="trade_row">
                        <div class="trade_label"><span>{{$t('orderInfo.remainNum')}}：</span></div>
                        <div class="trade_span"><span>{{buyTokenForm.remainNum}}</span></div>
                    </el-row>
                    <el-row class="trade_row">
                        <div class="trade_label"><span>{{$t('orderInfo.maxTxNum')}}：</span></div>
                        <div class="trade_span"><span>{{buyTokenForm.maxTxNum}}</span></div>
                    </el-row>
                </div>
            </el-form>
            <div slot="footer">
                <el-button @click="buyTokenFormClose">{{$t('operateType.cancel')}}</el-button>
                <el-button type="primary" @click="txTradeSubmit('buyTokenForm')">{{$t('operateType.confirm')}}</el-button>
            </div>
        </el-dialog>
        <el-dialog :title="sellTradeTitle" :visible.sync="sellTokenVisible" top="30vh"
                   class="trade-dialog"
                   :close-on-click-modal="false"
                   :close-on-press-escape="false"
                   @submit.native.prevent
                   @close="sellTokenFormClose">
            <el-form ref="sellTokenForm" :model="sellTokenForm" :rules="tradeTokendRules">
                <div class="tradeToken">
                    <el-row class="trade_row">
                        <div class="trade_label"><span>{{$t('orderInfo.num')}}：</span></div>
                        <div class="trade_input">
                            <el-form-item prop="txNum">
                                <el-input type="input" v-model="sellTokenForm.txNum" :maxlength="10" :placeholder="$t('switch.nullTxNum')"></el-input>
                            </el-form-item>
                        </div>
                    </el-row>
                    <el-row class="trade_row">
                        <div class="trade_label"><span>{{$t('orderInfo.remainNum')}}：</span></div>
                        <div class="trade_span"><span>{{sellTokenForm.remainNum}}</span></div>
                    </el-row>
                    <el-row class="trade_row">
                        <div class="trade_label"><span>{{$t('orderInfo.maxTxNum')}}：</span></div>
                        <div class="trade_span"><span>{{sellTokenForm.maxTxNum}}</span></div>
                    </el-row>
                </div>
            </el-form>
            <div slot="footer">
                <el-button @click="sellTokenFormClose">{{$t('operateType.cancel')}}</el-button>
                <el-button type="primary" @click="txTradeSubmit('sellTokenForm')">{{$t('operateType.confirm')}}</el-button>
            </div>
        </el-dialog>

        <!-- 订单交易详情列表 -->
        <el-dialog title="订单交易详情" :visible.sync="orderTradeVisible" top="30vh"
                   class="order-trade-detail-dialog"
                   :close-on-click-modal="false"
                   :close-on-press-escape="false"
                   @close="orderTradeFormClose">
            <el-form ref="orderTradeForm">
                <el-table :data="tradeList" stripe border class="mt_0 el-deposit-table"
                          v-loading="tradeListLoading">
                    <el-table-column label="" width="30">
                    </el-table-column>
                    <el-table-column :label="$t('orderInfo.createTime')" width="170" align="left">
                        <template slot-scope="scope">{{ scope.row.createTime }}</template>
                    </el-table-column>
                    <el-table-column :label="$t('orderInfo.price')" width="110" align="left">
                        <template slot-scope="scope">{{ scope.row.price }}</template>
                    </el-table-column>
                    <el-table-column :label="$t('orderInfo.num')" width="110" align="left">
                        <template slot-scope="scope">{{ scope.row.txNum }}</template>
                    </el-table-column>
                    <el-table-column :label="$t('orderInfo.txAmount')" width="110" align="left">
                        <template slot-scope="scope">{{ scope.row.txAmount }}</template>
                    </el-table-column>
                    <el-table-column :label="$t('orderInfo.status')" width="120" align="left">
                        <template slot-scope="scope">
                            <span v-if="scope.row.status!=0"> {{$t('tradeStatus.'+ scope.row.status)}} </span>
                            <el-button type="primary" size="mini" @click="confirmOrderClick(scope.row)" v-if="scope.row.status==0">{{$t('operateType.confirm')}}</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="paging">
                    <el-pagination class="pages" background layout="total,prev, pager, next, jumper"
                                   v-show="tradeListPager.total > tradeListPager.rows"
                                   :total="tradeListPager.total"
                                   :current-page.sync="tradeListPager.page"
                                   :pager-count=5
                                   :page-size="tradeListPager.rows" @current-change="pagesTradeList">
                    </el-pagination>
                </div>
            </el-form>
        </el-dialog>

        <!-- 用于在不同功能输入密码 -->
        <!-- 买入/卖出挂单 -->
        <Password ref="createOrderPassword" @passwordSubmit="createOrderPassSubmit">
        </Password>
        <!-- 买入/卖出交易 -->
        <Password ref="txTradePassword" @passwordSubmit="txTradePassSubmit">
        </Password>
        <!-- 取消挂单 -->
        <Password ref="cancelOrderPassword" @passwordSubmit="cancelOrderPassSubmit">
        </Password>
        <!-- 确认交易 -->
        <Password ref="confirmOrderPassword" @passwordSubmit="confirmOrderPassSubmit">
        </Password>
    </div>
</template>

<script>
    import nuls from 'nuls-sdk-js'
    import Serializers from 'nuls-sdk-js/lib/api/serializers'
    import buffer from 'nuls-sdk-js/lib/utils/buffer'
    import Password from '@/components/PasswordBar'
    import SelectTokenBar from '@/components/SelectTokenBar'
    import {addressInfo, chainID, multiDecimals, Times, divDecimals, Division, toFixed, deserializeTx} from '@/api/util'
    import {
        createOrder,
        tradingOrder,
        cancelOrder,
        confirmOrder,
        getOrderDetail,
        getBalanceOrNonceByAddress,
        inputsOrOutputs,
        validateAndBroadcast
    } from '@/api/requestData'
    //import moment from 'moment'

    export default {
        data() {
            let validatePrice = (rule, value, callback) => {
                let re = /^\d+(?=\.{0,1}\d+$|$)/;
                let res = /^\d{1,8}(\.\d{1,8})?$/;
                if (!value) {
                    callback(new Error(this.$t('switch.nullPrice')));
                } else if (!re.exec(value) || !res.exec(value)) {
                    callback(new Error(this.$t('switch.mustNum')));
                } else if (value <= 0) {
                    callback(new Error(this.$t('switch.mustNotZero')));
                } else {
                    callback();
                }
            };
            let validateTotalNum = (rule, value, callback) => {
                let re = /^\d+(?=\.{0,1}\d+$|$)/;
                let res = /^\d{1,8}(\.\d{1,8})?$/;
                if (!value) {
                    return callback(new Error(this.$t('switch.nullTxNum')));
                } else if (!re.exec(value) || !res.exec(value)) {
                    callback(new Error(this.$t('switch.mustNum')));
                } else if (value <= 0) {
                    callback(new Error(this.$t('switch.txNumError')));
                } else {
                    callback();
                }
            };
            let validateTxNum = (rule, value, callback) => {
                let re = /^\d+(?=\.{0,1}\d+$|$)/;
                let res = /^\d{1,8}(\.\d{1,8})?$/;
                if (!value) {
                    return callback(new Error(this.$t('switch.nullTxNum')));
                } else if (!re.exec(value) || !res.exec(value)) {
                    callback(new Error(this.$t('switch.mustNum')));
                } else if (value <= 0) {
                    callback(new Error(this.$t('switch.txNumError')));
                } else {
                    callback();
                }
            };
            return {
                fromTokenInfo:{},
                toTokenInfo: {},
                fromTokenId: '',
                toTokenId: '',
                buyTradeTitle: '买入 ',
                sellTradeTitle: '卖出 ',
                addressInfo: '', //默认账户信息
                balanceInfo: {},//当前账户余额信息
                fromBalanceInfo: {},//账户原TOKEN余额信息
                toBalanceInfo: {},//账户目标TOKEN余额信息
                accountAddress: JSON.parse(localStorage.getItem('accountInfo')),
                address: localStorage.getItem('accountInfo') != null ? JSON.parse(localStorage.getItem('accountInfo')).address : '',
                assetsList: [],//账户资产列表
                fee: 0.001, //手续费
                feeSymbol: "NULS",//手续费显示单位
                buyTokenOrderForm: {
                    price: '',
                    totalNum: '',
                    totalAmount: ''
                },
                sellTokenOrderForm: {
                    price: '',
                    totalNum: '',
                    totalAmount: ''
                },
                buyTokenForm: {
                    txNum: '',
                    remainNum: '',
                    maxTxNum: ''
                },
                sellTokenForm: {
                    txNum: '',
                    remainNum: '',
                    maxTxNum: ''
                },
                buyTokenVisible: false,
                sellTokenVisible: false,
                orderTradeVisible: false,
                isMobile: true,
                activeName: 'buyTab',
                depositActiveName: 'depositTab',
                //交易类型
                txType: 0,
                //交易数量
                txNum: 0,
                //订单ID
                orderId: '',
                orderPrice: '',
                orderInfo:{},
                tradeInfo:{},
                price: '',
                //订单交易ID
                txId: '',
                txHash: '',
                //订单交易HEX
                txHex: '',
                //可买挂单列表
                buyList: [],
                //可买挂单列表分页信息
                buyListPager: {
                    total: 0,
                    page: 1,
                    rows: 5,
                },
                //可买挂单列表加载动画
                buyListLoading: true,
                //可卖挂单列表
                sellList: [],
                //可卖挂单列表分页信息
                sellListPager: {
                    total: 0,
                    page: 1,
                    rows: 5,
                },
                //可卖挂单列表加载动画
                sellListLoading: true,
                //当前委托列表
                depositList: [],
                //当前委托列表分页信息
                depositListPager: {
                    total: 0,
                    page: 1,
                    rows: 5,
                },
                //当前委托列表加载动画
                depositListLoading: true,

                //订单交易列表
                tradeList: [],
                //订单交易列表分页信息
                tradeListPager: {
                    total: 0,
                    page: 1,
                    rows: 5,
                },
                //订单交易列表加载动画
                tradeListLoading: true,
                //余额定时器
                balanceInterval: null,
                buyTokenOrderRules: {
                    price: [
                        {validator: validatePrice, trigger: ['blur']},
                    ],
                    totalNum: [
                        {validator: validateTotalNum, trigger: ['blur']}
                    ]
                },
                sellTokenOrderRules:{
                    price: [
                        {validator: validatePrice, trigger: ['blur']},
                    ],
                    totalNum: [
                        {validator: validateTotalNum, trigger: ['blur']}
                    ]
                },
                //验证信息
                tradeTokendRules: {
                    txNum: [{validator: validateTxNum, trigger: ['blur', 'change']}]
                }
            }
        },
        components: {
            SelectTokenBar,
            Password
        },
        created() {
            this.isMobile = /(iPhone|iOS|Android|Windows Phone)/i.test(navigator.userAgent);
            this.addressInfo = addressInfo(1, this.address);
            if (this.accountAddress != null) {
                // balanceInfo
                this.getBalanceOrNonce(0, 2, 1, this.accountAddress.address);
                // fromBalanceInfo
                this.getBalanceOrNonce(1, 2, 1, this.accountAddress.address, 1);
                // toBalanceInfo
                this.getBalanceOrNonce(2, 2, 1, this.accountAddress.address, 1);
            }

            setInterval(() => {
                this.addressInfo = addressInfo(1, this.address);
            }, 30000);

            this.pagesBuyList();
            this.pagesDepositList();
            this.getAssetsListByAddress(this.accountAddress.address);
        },
        mounted() {
            //定时获取余额
            this.balanceInterval = setInterval(() => {
                if (this.accountAddress != null) {
                    // balanceInfo
                    this.getBalanceOrNonce(0, 2, 1, this.accountAddress.address);
                    // fromBalanceInfo
                    this.getBalanceOrNonce(1, 2, 1, this.accountAddress.address, 1);
                    // toBalanceInfo
                    this.getBalanceOrNonce(2, 2, 1, this.accountAddress.address, 1);
                }
            }, 60000);
        },
        watch: {
            addressInfo(val, old) {
                //this.activeName = 'buyTab';
                if (val.address !== old.address && old.address) {
                    this.getAssetsListByAddress(this.accountAddress.address);
                }
            },
            fromTokenInfo(val, old) {
                if (val.tokenId !== old.tokenId && val.tokenId) {
                    // fromBalanceInfo
                    this.getBalanceOrNonce(1, val.chainId, val.assetId, this.accountAddress.address, 1);
                }
            },
            toTokenInfo(val, old) {
                if (val.tokenId !== old.tokenId && val.tokenId) {
                    // toBalanceInfo
                    this.getBalanceOrNonce(2, val.chainId, val.assetId, this.accountAddress.address, 1);
                }
            },
        },
        beforeDestroy() {
            //离开界面清除定时器
            if (this.balanceInterval) {
                clearInterval(this.balanceInterval);
            }
        },
        methods: {
            /**
             * 获取地址余额信息
             *  @param type 1-原token 2-目标token
             *  @param assetChainId
             *  @param assetId
             *  @param address
             *  @param divDecimals 是否去除默认精度
             **/
            async getBalanceOrNonce(type, assetChainId, assetId, address, divDecimals) {
                await getBalanceOrNonceByAddress(assetChainId, assetId, address, divDecimals).then((response) => {
                    if (response.success) {
                        if (1 === type) {
                            this.fromBalanceInfo = response.data;
                        } else if (2 === type) {
                            this.toBalanceInfo = response.data;
                        } else {
                            this.balanceInfo = response.data;
                        }
                    } else {
                        this.$message({message: this.$t('public.getBalanceFail') + response.data, type: 'error', duration: 1000});
                    }
                }).catch((error) => {
                    this.$message({message: this.$t('public.getBalanceException') + error.data, type: 'error', duration: 1000});
                });
            },
            /**
             * 获取地址的资金列表
             * @param address
             **/
            async getAssetsListByAddress(address) {
                this.assetsList = [];
                //获取本链的基本资产
                let basicAssets = [];
                //记录主链id
                let chainId = 2;
                await this.$post_nuls('/', 'getAccountLedgerList', [address])
                    .then((response) => {
                        if (response.hasOwnProperty("result")) {
                            for (let item of response.result) {
                                basicAssets.push({
                                    type: 1,
                                    symbol: item.symbol,
                                    chainId: item.chainId,
                                    assetId: item.assetId,
                                    balance: divDecimals(item.balance)
                                });
                                chainId = item.chainId;
                            }
                        }
                    })
                    .catch((error) => {
                        console.log("getAccountLedgerList:" + error);
                    });

                //获取本连的合约资产
                let contractAssets = [];
                await this.$post_nuls('/', 'getAccountTokens', [1, 100, address])
                    .then((response) => {
                        if (response.hasOwnProperty("result")) {
                            for (let itme of response.result.list) {
                                contractAssets.push({
                                    type: 2,
                                    symbol: itme.tokenSymbol,
                                    chainId: chainId,
                                    balance: divDecimals(itme.balance, itme.decimals),
                                    contractAddress: itme.contractAddress,
                                    decimals: itme.decimals
                                })

                            }
                        }
                    })
                    .catch((error) => {
                        console.log("getAccountTokens:" + error);
                    });

                //获取跨链的基本资产
                let crossAssets = [];
                await this.$post_nuls('/', 'getAccountCrossLedgerList', [address])
                    .then((response) => {
                        if (response.hasOwnProperty("result")) {
                            for (let item of response.result) {
                                crossAssets.push({
                                    type: 1,
                                    symbol: item.symbol,
                                    chainId: item.chainId,
                                    assetId: item.assetId,
                                    balance: divDecimals(item.balance)
                                })
                            }
                        }
                    })
                    .catch((err) => {
                        console.log("getAccountCrossLedgerList:" + err);
                    });

                this.assetsList = [...basicAssets, ...contractAssets, ...crossAssets];
                let isNuls = false; //是否有nuls资产
                for (let item of this.assetsList) {
                    if (item.symbol === 'NULS') {
                        isNuls = true
                    }
                }
                //没有nuls 资产 添加一个为nuls资产
                if (!isNuls) {
                    let newNulsAssets = {
                        type: 1,
                        symbol: 'NULS',
                        chainId: 2,
                        assetId: 1,
                        balance: 0
                    };
                    this.assetsList.unshift(newNulsAssets);
                }
                this.getSymbol();
            },
            /**
             * 获取收付费单位
             **/
            getSymbol() {
                for (let item of this.assetsList) {
                    if (item.chainId === chainID() && item.type === 1) {
                        this.feeSymbol = item.symbol;
                    }
                }
            },
            /**
             * 买入/卖出挂单提交
             * @param formName
             **/
            async submitCreateOrder(formName,txType) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //校验余额是否充足
                        let totalAmount = multiDecimals(Times(this.buyTokenOrderForm.price, this.buyTokenOrderForm.totalNum), 8);
                        let balance = multiDecimals(this.toBalanceInfo.balance, 8);
                        if (txType == 2) {
                            totalAmount = multiDecimals(Times(this.sellTokenOrderForm.price, this.sellTokenOrderForm.totalNum), 8);
                            balance = multiDecimals(this.fromBalanceInfo.balance, 8);
                        }
                        if (totalAmount > balance)
                        {
                            this.$message({message: this.$t('switch.insufficientBalance'), type: 'error', duration: 2000});
                            return false;
                        }
                        this.txType=txType;
                        this.$refs.createOrderPassword.showPassword(true);
                    } else {
                        return false;
                    }
                });
            },
            /**
             *  创建挂单，获取密码框的密码
             * @param password
             **/
            async createOrderPassSubmit(password) {
                const pri = nuls.decrypteOfAES(this.accountAddress.aesPri, password);
                const newAddressInfo = nuls.importByKey(chainID(), pri, password);
                let price=this.txType === 1 ? this.buyTokenOrderForm.price : this.sellTokenOrderForm.price;
                let totalNum=this.txType === 1 ? this.buyTokenOrderForm.totalNum : this.sellTokenOrderForm.totalNum

                if (newAddressInfo.address === this.accountAddress.address) {
                    // 创建订单提交
                    let params = {
                        "txType": this.txType,
                        "address": newAddressInfo.address,
                        "fromTokenId": this.fromTokenId,
                        "toTokenId": this.toTokenId,
                        "price": multiDecimals(price, 8),
                        "totalNum": multiDecimals(totalNum, 8),
                        "totalAmount": multiDecimals(Number(Times(price, totalNum)), 8)
                    };
                    await createOrder(params).then((response) => {
                        if (response.success) {
                            this.buyTokenOrderForm.price = '';
                            this.buyTokenOrderForm.totalNum = '';
                            this.buyTokenOrderForm.totalAmount = '';
                            this.sellTokenOrderForm.price = '';
                            this.sellTokenOrderForm.totalNum = '';
                            this.sellTokenOrderForm.totalAmount = '';
                            // 重新加载当前委托
                            this.pagesDepositList();
                            this.$message({message: this.$t('switch.createOrderSuccess'), type: 'success', duration: 1000});
                        } else {
                            this.$message({message: this.$t('switch.createOrderError') + response.data, type: 'error', duration: 1000});
                        }
                    }).catch((err) => {
                        this.$message({message: this.$t('switch.createOrderError') + err.data, type: 'error', duration: 1000});
                    });
                }else {
                    this.$message({message: this.$t('public.errorPwd'), type: 'error', duration: 1000});
                }
            },

            /**
             * tab 选项
             **/
            handleClick(tab) {
                this.activeName = tab.name;
                if (tab.name === 'buyTab') {
                    // 查询可买挂单列表
                    this.buyListLoading = true;
                    this.pagesBuyList();
                } else if (tab.name === 'sellTab') {
                    // 查询可卖挂单列表
                    this.sellListLoading = true;
                    this.pagesSellList();
                }
            },

            /**
             * 点击买入按钮，弹出购买框
             */
            buyBtnClick(orderInfo) {
                this.orderInfo = orderInfo;
                this.orderId = orderInfo.orderId;
                this.price = orderInfo.price;
                this.buyTokenForm.remainNum = orderInfo.remainNum;
                this.buyTokenForm.maxTxNum = toFixed(Number(Division(this.toBalanceInfo.balance, orderInfo.price)));
                this.buyTokenVisible = true;
            },

            /**
             * 点击卖出按钮，弹出卖出框
             */
            sellBtnClick(orderInfo) {
                this.orderInfo = orderInfo;
                this.orderId = orderInfo.orderId;
                this.price = orderInfo.price;
                this.sellTokenForm.remainNum = orderInfo.remainNum;
                this.sellTokenForm.maxTxNum = toFixed(Number(Division(this.fromBalanceInfo.balance, orderInfo.price)));
                this.sellTokenVisible = true;
            },

            // 关闭买入窗口
            buyTokenFormClose() {
                this.$refs['buyTokenForm'].resetFields();
                this.buyTokenVisible = false;
            },
            // 关闭卖出窗口
            sellTokenFormClose() {
                this.$refs['sellTokenForm'].resetFields();
                this.sellTokenVisible = false;
            },

            // 点击确定买卖后，弹出密码输入框
            txTradeSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if(formName=='buyTokenForm')
                        {
                            //校验交易数量
                            if (Number(this.buyTokenForm.txNum) > Number(this.buyTokenForm.remainNum))
                            {
                                this.$message({message: this.$t('switch.txNumLgRemainNumError'), type: 'error', duration: 2000});
                                return false;
                            }
                            if (Number(this.buyTokenForm.txNum) > Number(this.buyTokenForm.maxTxNum))
                            {
                                this.$message({message: this.$t('switch.txNumLgBalanceNumError'), type: 'error', duration: 2000});
                                return false;
                            }
                            this.txNum=this.buyTokenForm.txNum;
                            this.buyTokenVisible = false;
                        }
                        if(formName=='sellTokenForm')
                        {
                            if (Number(this.sellTokenForm.txNum) > Number(this.sellTokenForm.remainNum))
                            {
                                this.$message({message: this.$t('switch.txNumLgRemainNumError'), type: 'error', duration: 2000});
                                return false;
                            }
                            if (Number(this.sellTokenForm.txNum) > Number(this.sellTokenForm.maxTxNum))
                            {
                                this.$message({message: this.$t('switch.txNumLgBalanceNumError'), type: 'error', duration: 2000});
                                return false;
                            }
                            this.txNum=this.sellTokenForm.txNum;
                            this.sellTokenVisible = false;
                        }
                        this.$refs.txTradePassword.showPassword(true);
                    } else {
                        return false
                    }
                })
            },
            /**
             *  确定买卖，获取密码框的密码
             * @param password
             **/
            async txTradePassSubmit(password) {
                const pri = nuls.decrypteOfAES(this.accountAddress.aesPri, password);
                const newAddressInfo = nuls.importByKey(chainID(), pri, password);
                if (newAddressInfo.address === this.accountAddress.address) {
                    let fromAddress = this.address;
                    let toAddress = this.orderInfo.address;
                    //B转出资产
                    let assetsChainId = this.fromTokenInfo.chainId != null ? this.fromTokenInfo.chainId : 2;
                    let assetsId = this.fromTokenInfo.assetId != null ? this.fromTokenInfo.assetId : 1;
                    let transferInfoB = {
                        fromAddress: fromAddress,
                        toAddress: toAddress,
                        assetsChainId: assetsChainId,
                        assetsId: assetsId,
                        fee: 0
                    };
                    let tAssemble = [];
                    let inOrOutputs = {};
                    let balanceInfoB = {};
                    //查询余额
                    await getBalanceOrNonceByAddress(assetsChainId, assetsId, fromAddress).then((response) => {
                        if (response.success) {
                            balanceInfoB = response.data;
                        } else {
                            this.$message({message: this.$t('public.getBalanceFail') + ": " + response.data, type: 'error', duration: 3000});
                        }
                    }).catch((error) => {
                        this.$message({message: this.$t('public.getBalanceException') + ": " + error.data, type: 'error', duration: 3000});
                    });
                    transferInfoB['amount'] = Number(Times(this.txNum, 100000000).toString());
                    inOrOutputs = await inputsOrOutputs(transferInfoB, balanceInfoB, 1);
                    if (!inOrOutputs.success) {
                        this.$message(inOrOutputs.data);
                        return false;
                    }
                    // 将A->B挂单人转出到吃单人的input、output写到这里
                    fromAddress = this.orderInfo.address;
                    toAddress = this.address;
                    //A转出资产
                    assetsChainId = this.toTokenInfo.chainId != null ? this.toTokenInfo.chainId : 2;
                    assetsId = this.toTokenInfo.assetId != null ? this.toTokenInfo.assetId : 1;
                    let transferInfoA = {
                        fromAddress: fromAddress,
                        toAddress: toAddress,
                        assetsChainId: assetsChainId,
                        assetsId: assetsId,
                        fee: 0
                    };
                    let inOrOutputsA = {};
                    let balanceInfoA = {};
                    //查询余额
                    await getBalanceOrNonceByAddress(assetsChainId, assetsId, fromAddress).then((response) => {
                        if (response.success) {
                            balanceInfoA = response.data;
                        } else {
                            this.$message({message: this.$t('public.getBalanceFail') + ": " + response.data, type: 'error', duration: 3000});
                        }
                    }).catch((error) => {
                        this.$message({message: this.$t('public.getBalanceException') + ": " + error.data, type: 'error', duration: 3000});
                    });
                    transferInfoA['amount'] = Number(Times(Times(this.orderInfo.price, this.txNum), 100000000).toString());
                    inOrOutputsA = await inputsOrOutputs(transferInfoA, balanceInfoA);
                    let inputs = [...inOrOutputs.data.inputs, ...inOrOutputsA.data.inputs];
                    let outputs = [...inOrOutputs.data.outputs, ...inOrOutputsA.data.outputs];
                    //交易组装
                    tAssemble = await nuls.transactionAssemble(inputs, outputs, '', 2);
                    //获取手续费
                    //let newFee = countFee(tAssemble, 1);
                    //交易签名
                    let txhex = await nuls.transactionSerialize(nuls.decrypteOfAES(this.addressInfo.aesPri, password), this.addressInfo.pub, tAssemble);
                    // 买卖TOKEN提交
                    let params = {
                        "txHash": tAssemble.hash.toString("hex"),
                        "txHex": txhex,
                        "address": newAddressInfo.address,
                        "orderId": this.orderId,
                        "txNum": multiDecimals(this.txNum, 8),
                        "toNum": multiDecimals(Number(Times(this.price, this.txNum)), 8)
                    };
                    await tradingOrder(params).then((response) => {
                        if (response.success) {
                            this.buyTokenForm.txNum = '';
                            this.sellTokenForm.txNum = '';
                            this.$message({message: this.$t('switch.tradingOrderSuccess'), type: 'success', duration: 2000});
                        } else {
                            this.$message({message: this.$t('switch.tradingOrderError') + response.data, type: 'error', duration: 3000});
                        }
                    }).catch((err) => {
                        this.$message({message: this.$t('switch.tradingOrderError') + err.data, type: 'error', duration: 3000});
                    });
                } else {
                    this.$message({message: this.$t('public.errorPwd'), type: 'error', duration: 3000});
                }
            },

            /**
             *  取消订单，获取密码框的密码
             * @param orderId
             **/
            cancelOrderClick(orderId) {
                this.orderId = orderId;
                this.$refs.cancelOrderPassword.showPassword(true);
            },
            /**
             *  取消订单提交
             * @param password
             **/
            async cancelOrderPassSubmit(password) {
                const pri = nuls.decrypteOfAES(this.accountAddress.aesPri, password);
                const newAddressInfo = nuls.importByKey(chainID(), pri, password);
                if (newAddressInfo.address === this.accountAddress.address) {
                    // 取消订单提交
                    let params = {
                        "orderId": this.orderId
                    };
                    await cancelOrder(params).then((response) => {
                        if (response.success) {
                            this.orderId = '';
                            this.$message({message: this.$t('switch.cancelOrderSuccess'), type: 'success', duration: 2000});
                        } else {
                            this.$message({message: this.$t('switch.cancelOrderError') + ": " + response.data, type: 'error', duration: 3000});
                        }
                    }).catch((err) => {
                        this.$message({message: this.$t('switch.cancelOrderError') + ": " + err.data, type: 'error', duration: 3000});
                    });
                }else {
                    this.$message({message: this.$t('public.errorPwd'), type: 'error', duration: 1000});
                }
            },

            /**
             *  关闭订单交易明细窗口
             **/
            orderTradeFormClose() {
                this.$refs['orderTradeForm'].resetFields();
                this.orderTradeVisible = false;
            },
            /**
             *  查询订单交易记录列表
             * @param orderId
             **/
            async getOrderTradeClick(orderId, price) {
                this.orderId = orderId;
                this.orderPrice = price;
                // 查询订单交易记录
                this.pagesTradeList();
            },
            /**
             * 订单交易列表 分页
             */
            async pagesTradeList(){
                let params = {"current":  this.tradeListPager.page, "pageSize": this.tradeListPager.rows, "orderId": this.orderId};
                await getOrderDetail(params).then((response) => {
                    if (response.success) {
                        // 展示订单交易详情
                        for (let item of response.data.records) {
                            item.price = this.orderPrice;
                            item.txNum = divDecimals(item.txNum, 8);
                            item.totalNum = divDecimals(item.totalNum, 8);
                            item.txAmount = Times(item.price, item.txNum);
                        }
                        this.tradeList = response.data.records;
                        this.tradeListPager.total = response.data.total;
                        this.tradeListLoading = false;
                        this.orderTradeVisible = true;
                    } else {
                        this.$message({message: this.$t('switch.getOrderTradeError') + response.data, type: 'error', duration: 1000});
                    }
                }).catch((err) => {
                    this.$message({message: this.$t('switch.getOrderTradeError') + err.data, type: 'error', duration: 1000});
                });
            },

            /**
             *  确认订单，获取密码框的密码
             * @param orderId
             **/
            confirmOrderClick(tradeInfo) {
                this.tradeInfo = tradeInfo;
                this.$refs.confirmOrderPassword.showPassword(true);
            },
            /**
             *  确认订单提交，该操作数据会上链
             * @param password
             **/
            async confirmOrderPassSubmit(password) {
                const pri = nuls.decrypteOfAES(this.accountAddress.aesPri, password);
                const newAddressInfo = nuls.importByKey(chainID(), pri, password);
                if (newAddressInfo.address === this.accountAddress.address) {
                    // 反序列化txHex,追加from、to,再次签名,组装交易数据上链
                    let newTx = deserializeTx(this.tradeInfo.txHex);
                    //追加签名
                    let sign = new Serializers();
                    sign.getBufWriter().write(newTx.signatures);
                    //新签名
                    let newSignature = nuls.transactionSignature(pri, newTx);
                    sign.writeBytesWithLength(buffer.hexToBuffer(newAddressInfo['pub']));
                    sign.writeBytesWithLength(newSignature);
                    newTx.signatures = sign.getBufWriter().toBuffer();
                    let txhex = newTx.txSerialize().toString('hex');

                    //验证并广播交易
                    let txHash;
                    await validateAndBroadcast(txhex).then((response) => {
                        if (response.success) {
                            txHash = response.hash;
                        } else {
                            this.$message({message: this.$t('error.' + response.data.code), type: 'error', duration: 3000});
                        }
                    }).catch((err) => {
                        this.$message({message: this.$t('public.err1') + err.data, type: 'error', duration: 1000});
                    });

                    if (txHash != null) {
                        // 确认订单提交
                        let params = {
                            "txId": this.tradeInfo.txId,
                            "txHash": txHash,
                            "dataHex": txhex
                        };
                        await confirmOrder(params).then((response) => {
                            if (response.success) {
                                this.orderId = '';
                                this.tradeInfo.status = 1;
                                this.$message({message: this.$t('switch.confirmOrderSuccess'), type: 'success', duration: 2000});
                            } else {
                                this.$message({message: this.$t('switch.confirmOrderError') + ": " +  response.data, type: 'error', duration: 3000});
                            }
                        }).catch((err) => {
                            this.$message({message: this.$t('switch.confirmOrderError') + ": " +  err, type: 'error', duration: 3000});
                        });
                    }
                }else {
                    this.$message({message: this.$t('public.errorPwd'), type: 'error', duration: 1000});
                }
            },

            /**
             * 根据地址获取可买挂单列表
             */
            getBuyListByAddress(page, rows, address) {
                let params = {
                    "current": page,
                    "pageSize": rows,
                    "address": address,
                    "fromTokenId": this.fromTokenId,
                    "toTokenId": this.toTokenId
                };
                this.$get('/v1/order/listOnSell', '', params)
                    .then((response) => {
                        if (response.hasOwnProperty("result")) {
                            for (let item of response.result.records) {
                                //item.createTime = moment(getLocalTime(item.createTime)).format('YYYY-MM-DD HH:mm:ss');
                                item.price = divDecimals(item.price, 8);
                                item.remainNum = divDecimals(item.totalNum - item.txNum, 8);
                                item.txNum = divDecimals(item.txNum, 8);
                                item.totalNum = divDecimals(item.totalNum, 8);
                            }
                            this.buyList = response.result.records;
                            this.buyListPager.total = response.result.total;
                            this.buyListLoading = false;
                        }
                    }).catch((error) => {
                    console.log(error)
                })
            },

            /**
             * 根据地址获取可买挂单列表 分页
             */
            pagesBuyList() {
                this.getBuyListByAddress(this.buyListPager.page, this.buyListPager.rows, this.address);
            },

            /**
             * 根据地址获取可卖挂单列表
             */
            getSellListByAddress(page, rows, address) {
                let params = {
                    "current": page,
                    "pageSize": rows,
                    "address": address,
                    "fromTokenId": this.fromTokenId,
                    "toTokenId": this.toTokenId
                };
                this.$get('/v1/order/listOnBuy', '', params)
                    .then((response) => {
                        if (response.hasOwnProperty("result")) {
                            for (let item of response.result.records) {
                                //item.createTime = moment(getLocalTime(item.time)).format('YYYY-MM-DD HH:mm:ss');
                                item.price = divDecimals(item.price, 8);
                                item.remainNum = divDecimals(item.totalNum - item.txNum, 8);
                                item.txNum = divDecimals(item.txNum, 8);
                                item.totalNum = divDecimals(item.totalNum, 8);
                            }
                            this.sellList = response.result.records;
                            this.sellListPager.total = response.result.total;
                            this.sellListLoading = false;
                        }
                    }).catch((error) => {
                    console.log(error)
                })
            },

            /**
             * 根据地址获取Token交易列表分页
             */
            pagesSellList() {
                this.getSellListByAddress(this.sellListPager.page, this.sellListPager.rows, this.address);
            },

            /**
             * 根据地址获取当前委托列表
             */
            getDepositListByAddress(page, rows, address) {
                let params = {"current": page, "pageSize": rows, "address": address};
                this.$get('/v1/order/queryMyCurrentOrder', '', params)
                    .then((response) => {
                        if (response.hasOwnProperty("result")) {
                            for (let item of response.result.records) {
                                //item.createTime = moment(getLocalTime(item.createTime)).format('YYYY-MM-DD HH:mm:ss');
                                item.price = divDecimals(item.price, 8);
                                item.txNum = divDecimals(item.txNum, 8);
                                item.totalNum = divDecimals(item.totalNum, 8);
                                item.totalAmount = divDecimals(item.totalAmount, 8);
                            }
                            this.depositList = response.result.records;
                            this.depositListPager.total = response.result.total;
                            this.depositListLoading = false;
                        }
                    }).catch((error) => {
                    console.log(error)
                })
            },
            /**
             * 根据地址获取当前委托列表 分页
             */
            pagesDepositList() {
                this.getDepositListByAddress(this.depositListPager.page, this.depositListPager.rows, this.address);
            },

            /**
             * 选择代币类型
             **/
            changeTokenType(fromTokenInfo, toTokenInfo) {
                this.fromTokenInfo = fromTokenInfo;
                this.toTokenInfo = toTokenInfo;
                this.fromTokenId = fromTokenInfo.tokenId;
                this.toTokenId = toTokenInfo.tokenId;
                this.buyTradeTitle = '买入' + fromTokenInfo.tokenSymbol;
                this.sellTradeTitle = '卖出' + fromTokenInfo.tokenSymbol;
            },

            /**
             * url 连接跳转
             * @param name
             * @param parmes
             */
            toUrl(name, parmes) {
                let newParmes = {};
                if (name === 'addressInfo') {
                    this.address = parmes;
                    newParmes = {address: parmes}
                } else {
                    newParmes = {hash: parmes}
                }
                this.$router.push({
                    name: name,
                    query: newParmes
                })
            },

        }

    }
</script>

<style lang="less">
    @import "./../../assets/css/style";


    .el-tabs .el-tabs__header {
        margin: 0 01px;
    }

    .switch-hall {
        //min-height: 800px;
        margin-bottom: 10px;

        .bg-white {
            height: 50px;

            .title {
                padding-bottom: 0px;
                margin: 0px auto 0;

                .click {
                    margin-left: 20px;
                }

                @media screen and (max-width: 1000px) {
                    padding: 0 0 1.8rem 0.5rem;
                    font-size: 0.9rem;
                    .click {
                        margin-left: 1rem;
                        font-size: 0.9rem;
                    }
                }
            }
        }

        .top {
            margin: -2px auto 0;
            height: 255px;
            @media screen and (max-width: 1000px) {
                height: auto;
            }

            .top-left {
                margin-left: 10px;
                width: 320px;
                height: 255px;
                border: @BD1;
                border-radius: 3px;
                background-color: @Bcolour1;
                @media screen and (max-width: 1000px) {
                    width: 95%;
                    height: auto;
                }

                .tabs_title {
                    padding: 5px 0 0 0px;
                    text-align: center;
                    height: 30px;
                }

                .tabs_header {
                    background: #F5F6F8;
                    font-size: 16px;
                    font-weight: 700;
                    color: #475472;
                }
            }

            .order {
                width: 680px;
                height: 255px;
                float: left
            }

            .order_left {
                .order_row {
                    padding-top: 15px;

                    .order_label {
                        width: auto;
                        float: left;
                        display: inline;
                        padding: 10px 0 0 10px;
                        font-size: 14px;
                    }

                    .order_input {
                        width: 180px;
                        float: left;
                        display: inline;
                    }

                    .order_span {
                        width: 60px;
                        float: left;
                        display: inline;
                        padding: 10px 0 0 10px;
                    }
                }

                .order_btn_row {
                    padding-top: 10px;
                    text-align: center
                }
            }

            .top-right {
                width: 500px;
                height: 255px;
                border: @BD1;
                border-radius: 3px;
                background-color: @Bcolour1;
                margin: 0 0 0 20px;
                @media screen and (max-width: 1000px) {
                    width: 95%;
                    height: auto;
                }

                .tabs_title {
                    padding: 0 0 0 30px;
                }

                .el-table {
                    width: 100%;
                    margin-top: 0px;
                    tr {
                        th {
                            background-color: @Bcolour;
                        }
                    }
                    .has-gutter {
                        tr {
                            th {
                                padding: 0;
                                background-color: @Bcolour;
                                .cell {
                                    color: @Acolor2;
                                    height: 30px;
                                    line-height: 30px;
                                    text-transform: Capitalize;
                                }
                            }
                        }
                    }
                    td {
                        padding: 1px 0 !important;
                    }
                }
                .el-button
                {
                    padding: 5px 10px;
                }
            }

            .top-left {
                @media screen and (max-width: 1000px) {
                    display: none;
                }
            }

        }

        .bottoms {
            margin: 0px auto 40px;
            @media screen and (max-width: 1000px) {
                margin: 1.5rem auto 1.5rem ;
                width: 95%;
            }

            .el-deposit-table {
                margin-bottom: 15px;
            }

        }
    }

    .order-trade-detail-dialog {
        .el-dialog {
            width: 700px;
            .el-dialog__body {
                background-color: #F5F6F9 !important;
                padding: 30px 20px 30px 20px !important;
                .el-form {
                    .el-form-item {
                        .el-form-item__label {
                            line-height: 0;
                            padding: 28px 0 20px 0;
                        }
                    }
                }
            }
            .el-dialog__footer {
                text-align: center;
                background-color: #F5F6F9 !important;
                padding: 1rem 1rem 0.1rem;
            }
        }
    }

    .trade-dialog {
        .el-dialog {
            width: 400px;
            .el-dialog__body {
                background-color: #F5F6F9 !important;
                padding: 10px 20px !important;
                .el-form {
                    .el-form-item {
                        .el-form-item__label {
                            line-height: 0;
                            padding: 28px 0 20px 0;
                        }
                    }
                }
            }
            .tradeToken {
                .trade_row {
                    padding-top: 8px;

                    span {
                        color: @Fcolour;
                        font-size: 14px;
                    }
                    .trade_label {
                        width: 105px;
                        float: left;
                        display: inline;
                        padding: 10px 0 0 5px;
                    }

                    .trade_input {
                        width: 180px;
                        height: 30px;
                        float: left;
                        display: inline;
                    }

                    .trade_span {
                        width: auto;
                        float: left;
                        display: inline;
                        padding: 10px 0 0 5px;
                    }
                }
            }
        }
    }
</style>
