import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 更改浏览器默认样式
import 'normalize.css'
import '@/assets/css/base.css'
// elements icons引入
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// element plus全部导入
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// axios引入
import axios from 'axios'
// axios.defaults.baseURL = 'http://localhost:8081'
axios.defaults.baseURL = 'https://www.xinyouyanxue.com/backstageEnd'
// axios.defaults.headers.post['Authorization'] = localStorage.getItem('token');
axios.defaults.headers['Authorization'] = localStorage.getItem('token');
// 全局注册axios
import { provide } from 'vue'
provide('axios', axios)

const app = createApp(App)
app.use(store)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}