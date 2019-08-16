<template>
  <div class="bottom">
    <div class="w1200">
      <div class="fl">
        <ul>
          <li class="clicks fl">官网</li>
          <li class="clicks fl">Github</li>
          <li class="clicks fl">节点客户端</li>
          <li class="clicks fl">轻钱包</li>
          <li class="clicks fl">浏览器</li>
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
    name: "bottom-bar"
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
