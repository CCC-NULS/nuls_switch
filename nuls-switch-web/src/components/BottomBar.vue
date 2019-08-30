<template>
  <div class="bottom">
    <div class="w1200">
      <div class="fl">
        <ul>
          <li class="clicks fl capitalize"><a href="https://nuls.io/home" target="_blank">{{$t('bottom.website')}}</a></li>
          <li class="clicks fl capitalize"><a href="https://github.com/nuls-io" target="_blank">Github</a></li>
          <li class="clicks fl capitalize"><a href="https://wallet.nuls.io/" target="_blank">{{$t('bottom.webWallet')}}</a></li>
          <li class="clicks fl capitalize"><a href="https://nuls.community/" target="_blank">{{$t('bottom.community')}}</a></li>
          <li class="clicks fl capitalize" @click="toBugReport">{{$t('bottom.about')}}</li>
        </ul>
      </div>
      <div class="fr">
        <p>Copyright @2019 NULS</p>
      </div>
    </div>

  </div>

</template>

<script>
  export default {
    name: "bottom-bar",
    methods:{
      /**
       *  问题反馈 跳转
       **/
      toBugReport() {
        window.open('https://nuls.community/d/134-collect-the-bugs-of-the-testnet-bugs/2', '_blank');
      },

    }
  }
</script>

<style lang="less">
  @import "./../assets/css/style";

  .bottom {
    height: 60px;
    position: fixed;
    bottom: 0;
    width: 100%;
    border-top: @BD1;
    .w1200{
      height: 100%;
      line-height: 60px;
      ul{
        li{
          padding: 0 20px;
          font-size: 14px;
          &:first-child{
            padding: 0;
          }
          &:last-child{
            font-size: 14px;
            padding: 0 0 0 20px;
          }
        }
      }
    }
  }
</style>
