<template>
  <div id="app">
    <!-- 全局导航 -->
    <header class="app-header">
      <nav v-if="showNav">
        <button class="logout-button" @click="logout">ログアウト</button>
      </nav>
      <h1 class="app-title">ペット保険システム</h1>
    </header>

    <!-- 路由视图 -->
    <router-view />
  </div>
</template>

<script>
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

export default {
  name: 'App',
  setup() {
    const route = useRoute();
    const router = useRouter();

    // 根据当前路由决定是否显示导航
    const showNav = computed(() => route.path !== '/');

    // 退出登录
    const logout = () => {
      // 清除登录状态（例如清除 token）
      localStorage.removeItem('token');
      sessionStorage.removeItem('token');
      router.push('/'); // 返回登录页面
    };

    return {
      showNav,
      logout,
    };
  },
};
</script>

<style>
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  background: #f5f7fa;
}

#app {
  text-align: center;
}

.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: linear-gradient(90deg, #6957c2, #6a48e4, #4d40ff);
  background-size: 300% 300%;
  animation: gradient-flow 6s infinite;
  padding: 1rem 2rem;
  color: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
  position: relative;
}

@keyframes gradient-flow {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

.app-title {
  margin: 0;
  font-size: 1.8rem;
  font-weight: bold;
  color: white;
  text-align: right;
  flex-shrink: 0;
}

.logout-button {
  background: transparent;
  border: 2px solid white;
  color: white;
  padding: 0.5rem 1.2rem;
  border-radius: 20px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.logout-button:hover {
  background: white;
  color: #6552c6; /* 紫色渐变的主色 */
  border-color: #7e57c2;
}

.logout-button:focus {
  outline: none;
}
</style>