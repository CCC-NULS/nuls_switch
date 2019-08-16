<template>
  <div class="his-order bg-gray">
    <div class="bg-white">
      <div class="title font24 w1200">
        <div class="fl"><span>{{$t('orderInfo.depositTime')}}：</span>
          <el-date-picker class="input-class"
                v-model="depositTime"
                type="daterange"
                range-separator="-"
                :start-placeholder="$t('public.startTime')"
                :end-placeholder="$t('public.endTime')"
                format="yyyy-MM-dd HH:mm:ss"
                :default-time="['00:00:00', '23:59:59']">
          </el-date-picker>
        </div>
        <div class="fl"><span>{{$t('orderInfo.tokenPair')}}：</span><SelectTokenBar @change="changeTokenType" :allType="1"></SelectTokenBar></div>
        <div class="fl"><span>{{$t('orderInfo.status')}}：</span><SelectBar v-model="orderStatus" :typeOptions="orderStatusOptions" typeName="orderStatus" @change="changeOrderStatus"></SelectBar></div>
        <div class="fl" style="width: 50px;height: 20px"><el-button type="primary" @click="pagesDepositList()">{{$t('public.query')}}</el-button></div>
      </div>
    </div>
    <div class="cb"></div>
    <div class="bottoms w1200 cb">
      <el-table :data="depositList" stripe border class="mt_0 el-deposit-table"
                v-loading="depositListLoading">
        <el-table-column label="" width="30">
        </el-table-column>
        <el-table-column :label="$t('orderInfo.createTime')" width="180" align="left">
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
        <el-table-column :label="$t('orderInfo.price')" width="120" align="left">
          <template slot-scope="scope">{{ scope.row.price }}</template>
        </el-table-column>
        <el-table-column :label="$t('orderInfo.num')" width="170" align="left">
          <template slot-scope="scope">{{ scope.row.txNum }}/{{ scope.row.totalNum }}</template>
        </el-table-column>
        <el-table-column :label="$t('orderInfo.totalAmount')" width="130" align="left">
          <template slot-scope="scope">{{ scope.row.totalAmount }}</template>
        </el-table-column>
        <el-table-column :label="$t('orderInfo.status')" width="120" align="left">
          <template slot-scope="scope">{{$t('orderStatus.'+ scope.row.status)}}</template>
        </el-table-column>
        <el-table-column :label="$t('operateType.operate')" width="155" align="left">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="cancelOrderClick(scope.row.orderId)" v-if="scope.row.status!==9">{{$t('operateType.cancel')}}</el-button>
            <span v-if="scope.row.status==1"> | </span>
            <el-button type="text" size="mini" @click="getOrderTradeClick(scope.row.orderId, scope.row.price)" >{{$t('operateType.details')}}</el-button>
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
    </div>

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
  import buffer from 'nuls-sdk-js/lib/utils/buffer'
  import {chainID, getLocalTime, Times, divDecimals, deserializeTx} from '@/api/util'
  import moment from 'moment'
  import Password from '@/components/PasswordBar'
  import SelectTokenBar from '@/components/SelectTokenBar'
  import SelectBar from '@/components/SelectBar'
  import Serializers from 'nuls-sdk-js/lib/api/serializers'
  import {
    cancelOrder,
    confirmOrder,
    getOrderDetail,
    queryMyHisOrder,
    validateAndBroadcast
  } from '@/api/requestData'

  export default {
    data() {
      return {
        accountAddress: JSON.parse(localStorage.getItem('accountInfo')),
        address: localStorage.getItem('accountInfo') != null ? JSON.parse(localStorage.getItem('accountInfo')).address : '',
        fromTokenInfo:{},
        toTokenInfo: {},
        //历史委托列表
        depositList: [],
        //历史委托列表分页信息
        depositListPager: {
          total: 0,
          page: 1,
          rows: 20,
        },
        //交易状态
        orderStatusOptions: [
          {value: 10, label: '10'},
          {value: 0, label: '0'},
          {value: 1, label: '1'},
          {value: 2, label: '2'},
          {value: 9, label: '9'},
        ],
        orderStatus: 10,
        depositTime: '',
        startQueryTime: '',
        endQueryTime: '',
        //历史委托列表加载动画
        depositListLoading: true,
        //订单交易明细
        orderTradeVisible: false,
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
        //订单ID
        orderId: '',
        orderPrice: '',
        tradeInfo: {}
      };
    },
    components: {
      Password,
      SelectTokenBar,
      SelectBar
    },
    created() {
      this.pagesDepositList();
    },
    methods: {
      /**
       * 根据地址获取当前委托列表
       */
      queryMyHisOrderPage(page, rows, address, fromTokenId, toTokenId, orderStatus, startQueryTime, endQueryTime) {
          let params = {
              "current": page,
              "pageSize": rows,
              "address": address,
              "fromTokenId": fromTokenId,
              "toTokenId": toTokenId,
              "status": orderStatus,
              "startQueryTime": startQueryTime,
              "endQueryTime": endQueryTime
          };
          queryMyHisOrder(params)
              .then((response) => {
                  if (response.success) {
                      for (let item of response.data.records) {
                          item.price = divDecimals(item.price, 8);
                          item.txNum = divDecimals(item.txNum, 8);
                          item.totalNum = divDecimals(item.totalNum, 8);
                          item.totalAmount = divDecimals(item.totalAmount, 8);
                      }
                      this.depositList = response.data.records;
                      this.depositListPager.total = response.data.total;
                      this.depositListLoading = false;
                  } else {
                      this.$message({
                          message: this.$t('public.queryMyHisOrderFail') + response.data,
                          type: 'error',
                          duration: 3000
                      });
                  }
              });
      },
      /**
       * 查询历史委托列表分页
       */
      pagesDepositList() {
        if(this.depositTime) {
          let startTime=this.depositTime[0];
          let endTime=this.depositTime[1];
          this.startQueryTime = moment(getLocalTime(startTime.getTime())).format('YYYY-MM-DD HH:mm:ss');
          this.endQueryTime = moment(getLocalTime(endTime.getTime())).format('YYYY-MM-DD HH:mm:ss');
        }
        let orderStatus = this.orderStatus;
        if(orderStatus === 10) {
          orderStatus = '';
        }
        this.queryMyHisOrderPage(this.depositListPager.page, this.depositListPager.rows, this.address, this.fromTokenInfo.tokenId, this.toTokenInfo.tokenId, orderStatus, this.startQueryTime, this.endQueryTime);
      },

      /**
       * 选择代币类型
       **/
      changeTokenType(fromTokenInfo, toTokenInfo) {
        this.fromTokenInfo = fromTokenInfo;
        this.toTokenInfo = toTokenInfo;
      },

      /**
       *  选择交易状态
       **/
      changeOrderStatus(type) {
        this.status = type;
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
              this.pagesDepositList();
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
      }

    }
  }
</script>

<style lang="less">
  @import "./../../assets/css/style";

  .his-order {
    margin: 0px auto 0;
    .bg-white {
      height: 50px;
    }
    .title {
      height: 40px;
      line-height: 40px;
      font-size: 12px;
    }
    .tokenBar{
      float:right;
    }
    .input-class{
      width: 355px !important;
      .el-range-input{
        width: 50%;
      }
    }
    .el-input__inner
    {
      font-size: 12px;
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
