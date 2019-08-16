module.exports = {
  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: false
    }
  },
  baseUrl: undefined,
  outputDir: undefined,
  assetsDir: undefined,
  runtimeCompiler: undefined,
  productionSourceMap: false,
  parallel: undefined,
  transpileDependencies: [
    'vue-echarts',
    'resize-detector'
  ],

  css: {
    sourceMap: true
  },
  devServer: {
    port: 8081,
    host: '0.0.0.0',
    https: false, // https:{type:Boolean}
    open: true, //配置自动启动浏览器
    proxy: {// 配置跨域处理
      '/api': {
        target: 'http://127.0.0.1:8010/',
        changeOrigin: true,  // 是否跨域
        pathRewrite: {
          '^/api': '/'
        }
      }
    }
  }

};
