// 公共的请求方法
import axios from 'axios'
// import base from './base'
axios.defaults.baseURL = 'http://localhost:8081'
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
const api = {
  // 获取用户信息
  async login(user) {
    return await axios.post('/login',user)
  }
}

export default api
