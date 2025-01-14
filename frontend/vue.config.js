const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8080,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '' // 将 /api 前缀重写为空
        },
        // 添加调试日志
        onProxyReq(proxyReq, req) {
          console.log('原始请求:', req.url);
          console.log('代理请求:', proxyReq.path);
        }
      }
    }
  }
})