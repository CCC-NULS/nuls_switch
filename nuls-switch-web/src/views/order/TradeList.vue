<template>
  <div class="his-order bg-gray">
    <div class="bg-white">
      <div class="title font24 w1200">
        <div class="fl"><span>{{$t('tradeInfo.createTime')}}：</span>
          <el-date-picker class="input-class"
                v-model="tradeTime"
                type="daterange"
                range-separator="-"
                :start-placeholder="$t('public.startTime')"
                :end-placeholder="$t('public.endTime')"
                format="yyyy-MM-dd HH:mm:ss"
                :default-time="['00:00:00', '23:59:59']">
          </el-date-picker>
        </div>
        <div class="fl ml_20"><span>{{$t('tradeInfo.tokenPair')}}：</span><SelectTokenBar @change="changeTokenType" :allType="1"></SelectTokenBar></div>
        <div class="fl ml_20"><span>{{$t('tradeInfo.status')}}：</span><SelectBar v-model="tradeStatus" :typeOptions="tradeStatusOptions" typeName="tradeStatus" @change="changeTradeStatus"></SelectBar></div>
        <div class="fl ml_20" ><el-button type="primary" @click="pagesTradeList()" size="small">{{$t('public.query')}}</el-button></div>
      </div>
    </div>
    <div class="cb"></div>
    <div class="bottoms w1200 cb">
      <!-- 订单交易详情列表 -->
      <el-table :data="tradeList" stripe border class="mt_0 el-trade-table"
                v-loading="tradeListLoading">
        <el-table-column label="" width="30">
        </el-table-column>
        <el-table-column :label="$t('tradeInfo.createTime')" width="180" align="left">
          <template slot-scope="scope">{{ scope.row.createTime }}</template>
        </el-table-column>
        <el-table-column :label="$t('tradeInfo.txType')" width="100" align="left">
          <template slot-scope="scope">
            <span v-if="scope.row.txType ===2">{{$t('switch.buy')}}</span>
            <span v-else>{{$t('switch.sell')}}</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('tradeInfo.tokenPair')" width="160" align="left">
          <template slot-scope="scope">{{ scope.row.tokenPair }}</template>
        </el-table-column>
        <el-table-column :label="$t('tradeInfo.price')" width="110" align="left">
          <template slot-scope="scope">{{ scope.row.price }}</template>
        </el-table-column>
        <el-table-column :label="$t('tradeInfo.outputNum')" width="160" align="left">
          <template slot-scope="scope">
            <span v-if="scope.row.txType ===2">{{ scope.row.toNum }}({{ scope.row.toTokenName }})</span>
            <span v-else>{{ scope.row.txNum }}({{ scope.row.fromTokenName }})</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('tradeInfo.inputNum')" width="160" align="left">
          <template slot-scope="scope">
            <span v-if="scope.row.txType ===2">{{ scope.row.txNum }}({{ scope.row.fromTokenName }})</span>
            <span v-else>{{ scope.row.toNum }}({{ scope.row.toTokenName }})</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('tradeInfo.status')" width="120" align="left">
          <template slot-scope="scope">{{$t('tradeStatus.'+ scope.row.status)}}</template>
        </el-table-column>
        <el-table-column :label="$t('operateType.operate')" width="155" align="left">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="cancelTradeClick(scope.row.txId)" v-if="scope.row.status===0">{{$t('operateType.cancel')}}</el-button>
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
    </div>

    <!-- 用于输入密码 -->
    <!-- 取消交易 -->
    <Password ref="cancelTradePassword" @passwordSubmit="cancelTradePassSubmit">
    </Password>
  </div>
</template>

<script>
  import nuls from 'nuls-sdk-js'
  import {chainID, getLocalTime, Times, divDecimals} from '@/api/util'
  import moment from 'moment'
  import Password from '@/components/PasswordBar'
  import SelectTokenBar from '@/components/SelectTokenBar'
  import SelectBar from '@/components/SelectBar'
  import {
    cancelTrade,
    queryTradeByPage,
  } from '@/api/requestData'

  export default {
    data() {
      return {
        accountAddress: JSON.parse(localStorage.getItem('accountInfo')),
        address: localStorage.getItem('accountInfo') != null ? JSON.parse(localStorage.getItem('accountInfo')).address : '',
        fromTokenInfo:{},
        toTokenInfo: {},
        //交易状态
        tradeStatusOptions: [
          {value: 10, label: '10'},
          {value: 0, label: '0'},
          {value: 1, label: '1'},
          {value: 2, label: '2'},
          {value: 3, label: '3'},
          {value: 9, label: '9'},
        ],
        tradeStatus: 10,
        tradeTime: '',
        startQueryTime: '',
        endQueryTime: '',
        //订单交易列表加载动画
        tradeListLoading: false,
        //订单交易列表
        tradeList: [],
        //订单交易列表分页信息
        tradeListPager: {
          total: 0,
          page: 1,
          rows: 20,
        },
        //交易ID
        txId: '',
        tradeInfo: {}
      };
    },
    components: {
      Password,
      SelectTokenBar,
      SelectBar
    },
    created() {
      this.pagesTradeList();
    },
    methods: {
      /**
       * 根据地址获取历史交易列表
       */
      queryTradeByPage(page, rows, address, fromTokenId, toTokenId, tradeStatus, startQueryTime, endQueryTime) {
          let params = {
              "current": page,
              "pageSize": rows,
              "address": address,
              "fromTokenId": fromTokenId,
              "toTokenId": toTokenId,
              "status": tradeStatus,
              "startQueryTime": startQueryTime,
              "endQueryTime": endQueryTime
          };
          queryTradeByPage(params)
              .then((response) => {
                  if (response.success) {
                      for (let item of response.data.records) {
                          item.price = divDecimals(item.price, 8);
                          item.txNum = divDecimals(item.txNum, 8);
                          item.toNum = divDecimals(item.toNum, 8);
                      }
                      this.tradeList = response.data.records;
                      this.tradeListPager.total = response.data.total;
                      this.tradeListLoading = false;
                  } else {
                      this.$message({
                          message: this.$t('public.queryTradeByPageFail') + ", " + response.data,
                          type: 'error',
                          duration: 3000
                      });
                  }
              });
      },
      /**
       * 查询历史交易列表分页
       */
      pagesTradeList() {
        if(this.tradeTime) {
          let startTime=this.tradeTime[0];
          let endTime=this.tradeTime[1];
          this.startQueryTime = moment(getLocalTime(startTime.getTime())).format('YYYY-MM-DD HH:mm:ss');
          this.endQueryTime = moment(getLocalTime(endTime.getTime())).format('YYYY-MM-DD HH:mm:ss');
        }
        let tradeStatus = this.tradeStatus;
        if(tradeStatus === 10) {
          tradeStatus = '';
        }
        this.queryTradeByPage(this.tradeListPager.page, this.tradeListPager.rows, this.address, this.fromTokenInfo.tokenId, this.toTokenInfo.tokenId, tradeStatus, this.startQueryTime, this.endQueryTime);
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
      changeTradeStatus(tradeStatus) {
        this.tradeStatus = tradeStatus;
      },

      /**
       *  取消交易，获取密码框的密码
       * @param txId
       **/
      cancelTradeClick(txId) {
        this.txId = txId;
        this.$refs.cancelTradePassword.showPassword(true);
      },

      /**
       *  取消交易提交
       * @param password
       **/
      async cancelTradePassSubmit(password) {
        const pri = nuls.decrypteOfAES(this.accountAddress.aesPri, password);
        const newAddressInfo = nuls.importByKey(chainID(), pri, password);
        if (newAddressInfo.address === this.accountAddress.address) {
          // 取消交易提交
          let params = {
            "txId": this.txId
          };
          await cancelTrade(params).then((response) => {
            if (response.success) {
              this.pagesTradeList();
              this.$message({message: this.$t('switch.cancelTradeSuccess'), type: 'success', duration: 2000});
            } else {
              this.$message({message: this.$t('switch.cancelTradeError') + ", " + response.data, type: 'error', duration: 3000});
            }
          }).catch((err) => {
            this.$message({message: this.$t('switch.cancelTradeError') + ", " + err.data, type: 'error', duration: 3000});
          });
        }else {
          this.$message({message: this.$t('public.errorPwd'), type: 'error', duration: 1000});
        }
      },

    }
  }
</script>

<style lang="less">
  @import "./../../assets/css/style";

  .his-order {
    margin: 0 auto 0;
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

    }
  }

  .el-select .el-input .el-input__inner {
    border-radius: 2px;
    line-height: 32px;
    height: 32px;
    width: 150px;
  }
</style>
