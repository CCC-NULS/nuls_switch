<template>
  <div class="header bg-white">
    <div class="w1200">
      <div class="logo fl">
        <img class="clicks" @click="toUrl('home')" src="../assets/logo.png">
      </div>
      <div class="nav fl">
        <el-menu :default-active="activeIndex" class="fl" mode="horizontal" @select="handleSelect">
          <el-menu-item index="switchHall" :disabled="addressList.length === 0"><i class="el-icon-video-camera-solid"></i>{{$t('nav.switch')}}</el-menu-item>
          <el-menu-item index="myAsset" :disabled="addressList.length === 0"><i class="el-icon-share"></i>{{$t('nav.myAsset')}}</el-menu-item>
          <el-menu-item index="myOrder" :disabled="addressList.length === 0"><i class="el-icon-video-camera-solid"></i>{{$t('nav.myOrder')}}</el-menu-item>
        </el-menu>
        <el-link type="primary" @click="toUrl('newAddress')" class="user click fr tc" v-if="!accountAddress">登陆
        </el-link>
        <div v-else>
          <i class="el-icon-s-custom click " @click="toUrl('backupsAddress')"></i>&nbsp;
          <span class="click tc" @click="signOut">退出</span>
        </div>

      </div>
    </div>
    <div class="cb"></div>
  </div>
</template>

<script>
  import {superLong, chainIdNumber, addressInfo} from '@/api/util'
  export default {
    data() {
      return {
        activeIndex: 'switchHall',//导航选中
        accountInfo: {},//账户信息
        accountAddress: '',
        addressList: [] //地址列表
      };
    },
    created() {
      this.getAddressList();
    },
    mounted() {
      setInterval(() => {
        this.getAddressList();
      }, 5000)
    },

    methods: {

      /**
       * 导航切换
       * @param key
       * @param keyPath
       */
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
        if (keyPath.length > 1) {
          if (keyPath[0] === "address") {
            for (let item  of this.addressList) {
              //清除选中
              if (item.selection) {
                item.selection = false;
              }
              //添加选中
              if (item.address === keyPath[1]) {
                item.selection = true;
              }
            }
            localStorage.setItem(chainIdNumber(), JSON.stringify(this.addressList));
          } else if (keyPath[0] === "set") {
            this.$router.push({
              name: keyPath[1]
            })
          } else if (keyPath[0] === "lang") {
            this.selectLanguage(key)
          }
        } else {
          this.$router.push({
            name: key
          })
        }
      },

      /**
       * 导航栏的选中
       * @param val
       **/
      navActives(val) {
        if (val.indexOf('/switchHall') === 0) {
          return 'switchHall'
        } else if (val.indexOf('/myAsset') === 0) {
          return 'myAsset'
        } else if (val.indexOf('/myOrder') === 0) {
          return 'myOrder'
        } else {
          return 'home'
        }
      },

      /**
       * 获取账户列表
       */
      getAddressList() {
        this.addressList = addressInfo(0);
        if (this.addressList) {
          for (let item  of this.addressList) {
            item.addresss = superLong(item.address, 8);
          }
        }
        if (localStorage.hasOwnProperty('accountInfo')) {
          this.accountInfo = JSON.parse(localStorage.getItem('accountInfo'));
          this.accountAddress = this.accountInfo.address;
        }
      },

      /**
       * 语言切换
       * @param e
       */
      selectLanguage(e) {
        this.lang = e;
        this.$i18n.locale = this.lang;
      },

      /**
       * 退出
       */
      signOut() {
        localStorage.removeItem(chainIdNumber());
        localStorage.removeItem('accountInfo');
        this.accountInfo = {};
        this.accountAddress = '';
        this.addressList = {};
      },

      /**
       * url 连接
       * @param name
       */
      toUrl(name) {
        this.$router.push({
          name: name,
        })
      }
    }
  }
</script>

<style lang="less">
  @import "./../assets/css/style";

  .header {
    border-bottom: @BD1;
    height: 80px;
    line-height: 80px;
    .logo {
      width: 150px;
      margin: 18px 0 0 0;
      height: 62px;
    }
    .nav {
      width: 1050px;
      .el-menu.el-menu--horizontal {
        border-bottom: 0;
        width: 950px;
        .el-menu-item {
          height: 79px;
          line-height: 80px;
          font-size: 16px;
          &:hover {
            color: @Ncolour;
          }
        }
        .is-active {
          color: @Ncolour;
          border-bottom: 0 solid transparent;
        }
      }
      .user {
        width: 100px;
        line-height: 80px;
      }
    }
  }
</style>
