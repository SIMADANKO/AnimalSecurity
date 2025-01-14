import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from './axios' // 修改导入路径

const app = createApp(App);

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 将 axios 实例添加到全局属性
app.config.globalProperties.$axios = axios;

app.use(ElementPlus)
app.use(router);
app.mount('#app');