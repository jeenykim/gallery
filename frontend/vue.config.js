const path = require('path')

module.exports = {
  outputDir: path.resolve(__dirname, "../backend/src/main/resources/static"),
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    }
  }
}
