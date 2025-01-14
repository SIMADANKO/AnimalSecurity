<template>
  <div class="auth-container">
    <div class="auth-card">
      <div class="auth-header">
        <img src="../assets/logo.jpg" alt="Logo" class="auth-logo" />
        <h2 class="auth-title">ペット保険システムへようこそ</h2>
      </div>
      
      <el-tabs v-model="activeTab" class="auth-tabs">
        <el-tab-pane label="ログイン" name="login">
          <el-form 
            :model="loginForm" 
            :rules="loginRules" 
            ref="loginFormRef" 
            label-position="top"
            @keyup.enter="handleLogin"
          >
            <el-form-item label="ユーザー名" prop="username">
              <el-input 
                v-model="loginForm.username" 
                placeholder="ユーザー名を入力してください"
              >
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            
            <el-form-item label="パスワード" prop="password">
              <el-input 
                v-model="loginForm.password" 
                :type="showPassword ? 'text' : 'password'"
                placeholder="パスワードを入力してください"
              >
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
                <template #suffix>
                  <el-icon 
                    class="cursor-pointer" 
                    @click="showPassword = !showPassword"
                  >
                    <View v-if="showPassword" />
                    <Hide v-else />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <div class="flex justify-between items-center mb-4">
              
              <el-button 
                type="text" 
                @click="showForgotPassword = true"
              >パスワードを忘れましたか？</el-button>
            </div>

            <el-button 
              type="primary" 
              class="w-full" 
              :loading="loading"
              @click="handleLogin"
            >ログイン</el-button>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="登録" name="register">
          <el-form 
            :model="registerForm" 
            :rules="registerRules" 
            ref="registerFormRef"
            label-position="top"
          >
          <el-form-item label="ユーザー名" prop="username">
  <el-input 
    v-model="registerForm.username" 
    placeholder="ユーザー名を入力してください"
  >
    <template #prefix>
      <el-icon><User /></el-icon>
    </template>
  </el-input>
</el-form-item>

<el-form-item label="メールアドレス" prop="email">
  <el-input 
    v-model="registerForm.email" 
    placeholder="メールアドレスを入力してください"
  >
    <template #prefix>
      <el-icon><Message /></el-icon>
    </template>
  </el-input>
</el-form-item>

<el-form-item label="パスワード" prop="password">
  <el-input 
    v-model="registerForm.password" 
    :type="showPassword ? 'text' : 'password'"
    placeholder="パスワードを入力してください"
  >
    <template #prefix>
      <el-icon><Lock /></el-icon>
    </template>
    <template #suffix>
      <el-icon 
        class="cursor-pointer" 
        @click="showPassword = !showPassword"
      >
        <View v-if="showPassword" />
        <Hide v-else />
      </el-icon>
    </template>
  </el-input>
</el-form-item>

<el-form-item label="パスワード確認" prop="confirmPassword">
  <el-input 
    v-model="registerForm.confirmPassword" 
    :type="showPassword ? 'text' : 'password'"
    placeholder="パスワードを再度入力してください"
  >
    <template #prefix>
      <el-icon><Lock /></el-icon>
    </template>
  </el-input>
</el-form-item>

            <el-button 
              type="primary" 
              class="w-full" 
              :loading="loading"
              @click="handleRegister"
            >登録</el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 忘记密码对话框 -->
    <el-dialog
  v-model="showForgotPassword"
  title="パスワードの回復"
  width="30%"
  center
>
  <el-form 
    :model="forgotPasswordForm"
    :rules="forgotPasswordRules"
    ref="forgotPasswordFormRef"
  >
    <el-form-item label="ユーザー名" prop="username">
      <el-input 
        v-model="forgotPasswordForm.username"
        placeholder="ユーザー名を入力してください"
      >
        <template #prefix>
          <el-icon><User /></el-icon>
        </template>
      </el-input>
    </el-form-item>
  </el-form>
  <template #footer>
    <span class="dialog-footer">
      <el-button @click="showForgotPassword = false">キャンセル</el-button>
      <el-button type="primary" @click="handleForgotPassword">確認</el-button>
    </span>
  </template>
</el-dialog>
  </div>
</template>

<script>
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { User, Lock, Message, View, Hide } from '@element-plus/icons-vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  name: 'AuthPage',
  components: {
    User,
    Lock,
    Message,
    View,
    Hide
  },
  setup() {
    const router = useRouter();
    const activeTab = ref('login');
    const loading = ref(false);
    const showPassword = ref(false);
    const rememberMe = ref(false);
    const showForgotPassword = ref(false);

    const loginForm = reactive({
      username: '',
      password: '',
    });

    const registerForm = reactive({
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
    });

    const forgotPasswordForm = reactive({
      username: '',
    });

    const loginRules = {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
      ],
    };

    const registerRules = {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, message: '用户名长度不能少于3位', trigger: 'blur' },
      ],
      email: [
        { required: true, message: '请输入邮箱', trigger: 'blur' },
        { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' },
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, message: '密码长度不能少于6位', trigger: 'blur' },
      ],
      confirmPassword: [
        { required: true, message: '请确认密码', trigger: 'blur' },
        {
          validator: (rule, value, callback) => {
            if (value !== registerForm.password) {
              callback(new Error('两次输入的密码不一致'));
            } else {
              callback();
            }
          },
          trigger: 'blur',
        },
      ],
    };

    const forgotPasswordRules = {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
      ],
    };

    const loginFormRef = ref(null);
    const registerFormRef = ref(null);
    const forgotPasswordFormRef = ref(null);

    const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true;
      axios
        .post('http://localhost:8081/users/login', loginForm)
        .then((response) => {
          // 判断后端返回的状态码是否为 200
          if (response.data.code === 200) {
            const jwt = response.headers['authorization']; // 获取 Authorization header
            console.log(jwt);
            console.log(response.data);
            console.log(response.headers);
            

            // 根据 rememberMe 判断保存 token 的位置
            if (rememberMe.value) {
              localStorage.setItem('token', jwt);  // 如果选择记住我，将 token 存储到 localStorage
            } else {
              sessionStorage.setItem('token', jwt); // 否则存储到 sessionStorage
            }

            
           

            ElMessage.success('登录成功');
            router.push({ name: 'Insurance' }); // 登录成功后跳转到 Insurance 页面
          } else {
            // 后端返回状态码不为 200，处理登录失败的情况
            const errorMessage = response.data.msg;
            console.log(errorMessage);
            ElMessage.error(errorMessage); // 显示后端返回的错误信息
          }
        })
        .catch((error) => {
          // 捕获请求错误
          if (error.response && error.response.status === 400) {
            // 如果用户名或密码错误，服务器返回状态码 400
            ElMessage.error(error.response.data.msg || '登录失败');
          } else {
            // 其他错误处理
            ElMessage.error('登录失败，请检查用户名或密码');
          }
        })
        .finally(() => {
          loading.value = false; // 无论请求成功还是失败，都关闭 loading
        });
    }
  });
};

    const handleRegister = () => {
      registerFormRef.value.validate(async (valid) => {
        if (valid) {
          loading.value = true;
          try {
            await axios.post('http://localhost:8081/users/register', registerForm);
            ElMessage.success('注册成功');
            activeTab.value = 'login';
          } catch (error) {
            ElMessage.error(error.response?.data?.message || '注册失败，请稍后重试');
          } finally {
            loading.value = false;
          }
        }
      });
    };

    const handleForgotPassword = () => {
      forgotPasswordFormRef.value.validate(async (valid) => {
        if (valid) {
          try {
            await axios.post('/api/forgot-password', null, {
              params: {
                username: forgotPasswordForm.username
              }
            });
            ElMessage.success('密码重置链接已发送到您的邮箱');
            showForgotPassword.value = false;
          } catch (error) {
            ElMessage.error(error.response?.data?.message || '找回密码失败，请稍后重试');
          }
        }
      });
    };

    return {
      activeTab,
      loading,
      showPassword,
      rememberMe,
      showForgotPassword,
      loginForm,
      registerForm,
      forgotPasswordForm,
      loginRules,
      registerRules,
      forgotPasswordRules,
      loginFormRef,
      registerFormRef,
      forgotPasswordFormRef,
      handleLogin,
      handleRegister,
      handleForgotPassword,
    };
  },
};
</script>

<style scoped>
.auth-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.auth-card {
  width: 100%;
  max-width: 440px;
  padding: 2rem;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

.auth-header {
  text-align: center;
  margin-bottom: 2rem;
}

.auth-logo {
  width: 68px;
  height: 60px;
  margin-bottom: 1rem;
}

.auth-title {
  font-size: 1.5rem;
  color: #374151;
  margin: 0;
}

.auth-tabs {
  margin-top: 1rem;
}


:deep(.el-tabs__nav-wrap::after) {
  height: 1px;
}

:deep(.el-tabs__active-bar) {
  height: 2px;
}

:deep(.el-form-item__label) {
  font-weight: 500;
}

:deep(.el-input__wrapper) {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
}

:deep(.el-button--primary) {
  height: 40px;
  font-size: 1rem;
}

.cursor-pointer {
  cursor: pointer;
}

:deep(.el-input__prefix) {
  margin-right: 8px;
}

:deep(.el-input__suffix) {
  margin-left: 8px;
}
</style>