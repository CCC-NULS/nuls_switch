<template>
  <div class="backups-address bg-gray">
    <div class="bg-white">
      <h3 class="title pt_5">{{$t('user.Backup')}}</h3>
    </div>
    <div class="tab bg-white w1200 mt_30">
      <div class="address w630 pt_10">
        <p>
          {{$t('user.YourAddress')}}:
          &nbsp;<span class="yellow">{{accountAddress.address}}</span>
          &nbsp;<i class="el-icon-document-copy click" @click="copy(accountAddress.address)"></i></p>
      </div>
      <div class="tips bg-gray w630">
        <p class="font14"><i></i>{{$t('user.createAccountTip1')}}</p>
        <p class="font14"><i></i>{{$t('user.createAccountTip2')}}</p>
      </div>

      <div class="w630 tc btn-next">
        <div>
          <el-button type="success" @click="backupsKey">{{$t('user.PrikeyBackup')}}</el-button>
        </div>
        <div class="mt_20">
          <el-button @click="toUrl('home')">{{$t('user.CompleteBackup')}}</el-button>
        </div>
      </div>
    </div>
    <Password ref="password" @passwordSubmit="passSubmit">
    </Password>
    <el-dialog :title="$t('user.SafeWarning')" width="40%"
               :visible.sync="keyDialog"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
    >
      <span></span>
      <p class="bg-white">
        {{accountAddress.pri}}
      </p>
      <span slot="footer" class="dialog-footer">
        <el-button type="success" @click="copy(accountAddress.pri)">复制</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import nuls from 'nuls-sdk-js'
  import Password from '@/components/PasswordBar'
  import {chainID, copys} from '@/api/util'

  export default {
    components: {Password},
    data() {
      return {
        accountAddress: JSON.parse(localStorage.getItem('accountInfo')),
        keyDialog: false, //key弹框
      };
    },
    methods: {

      /**
       *  获取密码框的密码
       * @param password
       **/
      async passSubmit(password) {
        const pri = nuls.decrypteOfAES(this.accountAddress.aesPri, password);
        const newAddressInfo = nuls.importByKey(chainID(), pri, password);
        if (newAddressInfo.address === this.accountAddress.address) {
          this.keyDialog = true;
          this.accountAddress.pri = pri;
        }
      },

      /**
       *  备份私钥
       **/
      backupsKey() {
        this.$refs.password.showPassword(true);
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

      /**
       * 复制方法
       * @param sting
       **/
      copy(sting) {
          copys(sting);
          this.$message({message: this.$t('public.copySuccess'), type: 'success', duration: 1000});
      },
    }
  }
</script>

<style lang="less">
  @import "./../../assets/css/style";

  .backups-address {
    .bg-white {
    }
    .tab {
      .address {
        margin: 20px auto 0;
        font-size: 18px;
      }
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

</style>
