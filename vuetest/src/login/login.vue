<template>
  <div>
    <div class="login-content">
      <div class="login-cont">
        <div class="login-title">Welcome to our 2022-Spring-Java2-Project</div>
        <div class="login-user">
          <p>Account</p>
          <el-input v-model="account" class="inputflex" placeholder="Please input your account" clearable/>
        </div>
        <div class="login-user">
          <p>PWD</p>
          <el-input v-model="password" class="inputflex"  type="password" placeholder="Please input your password" clearable/>
          </div>
        <div v-if="if_reg==='login'" class="login-user">
          <p>Confirm</p>
          <el-input v-model="confirm" class="inputflex"  type="password" placeholder="Please confirm your password" clearable/>
        </div>
        <!-- 登录注册切换       -->
        <div class="reg-view" @click="if_reg = if_reg==='login'?'register':'login'" ><p>{{ if_reg }}</p></div>
        <!-- 登陆注册按钮 -->
        <el-button v-if="if_reg==='register'" @click="signin" type="primary" size="large" :loading="load" class="reg-btn">Login
        </el-button>
        <el-button v-else @click="register" type="primary" size="large" :loading="load" class="reg-btn">Register</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {reactive, toRefs} from 'vue'
import {useRouter} from 'vue-router'
import axios from 'axios'

export default {
  setup() {
    const router = useRouter();
    const _this = this;
    const user = reactive({
      account: '',
      password: '',
      confirm: '',
      if_reg: 'register',
      load:false
    })
    // Login
    const signin = async () => {
      user.load = true
      const url = 'http://localhost:8181/User_Account/Log_in/'+user.account+'/'+user.password
      console.log(url)
      axios.post(url).then(function (resp) {
        console.log(resp.data)
        if (resp.data['user_name']==='Log in Failed!'){
          alert('Login Fail !')
          user.account = ''
          user.password = ''
          user.confirm = ''
          user.load = false
          return false
        }else {
          alert('Login Successfully !')
          user.load = false
          router.push('/home')
        }
        user.load = false
      })
    }

    // Register
    const register = async  () => {
      user.load = true
      const url = 'http://localhost:8181/User_Account/Sign_in/'+user.account+'/'+user.password+'/'+user.confirm
      console.log(url)
      axios.post(url).then(function (resp){
        console.log(resp.data)
        if (resp.data['user_name']==='Sign in Failed!'){
          alert('Register With Failure !')
          user.account = ''
          user.password = ''
          user.confirm = ''
          user.load = false
        }else {
          alert('Register successfully')
          router.push('/home')
          user.load = false
        }
      })
      user.load = false
    }
    return {...toRefs(user), signin, register}
  }
}
</script>