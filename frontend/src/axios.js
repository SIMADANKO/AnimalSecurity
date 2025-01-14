import axios from "axios";
import { ElMessage } from "element-plus";
import router from "./router";

// 创建 axios 实例，注意这里不要设置 baseURL
const instance = axios.create({
    timeout: 5000
});

// 请求拦截器
instance.interceptors.request.use(config => {
    // 打印请求信息以便调试
    console.log("发送请求:", config.url);
    
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

// 响应拦截器
instance.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        console.error("请求错误:", error);
        const { response } = error;
        let message = '请求错误';

        if (response) {
            switch (response.status) {
                case 401:
                    message = '请重新登录';
                    localStorage.removeItem('token');
                    router.push("/login");
                    break;
                case 403:
                    message = '权限不足，无法访问';
                    break;
                case 404:
                    message = '请求的资源不存在';
                    break;
                case 500:
                    message = '服务器错误';
                    break;
                default:
                    message = response.data?.message || '请求失败';
            }
        } else {
            message = '网络错误，请检查网络连接';
        }

        ElMessage({
            message: message,
            type: 'error',
            duration: 3000
        });

        return Promise.reject(error);
    }
);

export default instance;