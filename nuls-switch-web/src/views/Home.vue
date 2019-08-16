<template>
  <div class="home">
    home
  </div>
</template>

<script>
  export default {
    data() {
      return {};
    },
    created() {
    },
    methods: {}
  }
</script>

<style lang="less">
  .home {
    .carousel {
      height: 400px;
      width: 100%;
      border-bottom: 1px solid #5e6983;
    }
    .w1200 {
      height: 450px;
      .left, .right {
        width: 50%;
        p {
          font-size: 18px;
          font-weight: bold;
          line-height: 36px;
          color: #000000;
          width: 500px;
          margin: 100px auto 70px;
        }
        .el-button {
          display: block;
          width: 200px;
          height: 40px;
          margin: 0 0 0 50px;
        }
        .el-button--success {
          margin: 20px 0 0 50px;
        }
      }
    }
  }

</style>
