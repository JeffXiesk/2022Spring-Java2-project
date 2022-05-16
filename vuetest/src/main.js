import Vue,{ createApp } from 'vue'
import axios from './plugins/axios.js'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 全局引入css
import '../style/headtap.css'

const app = createApp(App);
app.use(store).use(axios).use(ElementPlus).use(router).mount('#app')
