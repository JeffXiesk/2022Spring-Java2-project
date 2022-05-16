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
          <el-input v-model="password" class="inputflex" placeholder="Please input your password" clearable/>
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
    const user = reactive({
      account: '',
      password: '',
      if_reg: 'register',
      load:false
    })
    // Login
    const signin = async () => {
      user.load = true
      // console.log(user.account)
      // console.log(user.password)
      var userlist = []
      // console.log('resp.data')
      // const { cxt, proxy } = getCurrentInstance();
      axios.get('http://localhost:8181/getuser/findAlluser').then(function (resp) {
        userlist = resp.data
        // console.log(userlist)
        for (let entry of userlist){
          // console.log(entry)
          if (entry.account === user.account) {
            if (entry.password === user.password) {
              alert('Account login successfully !')
              user.load = false
              router.push("/")
              return true
            }else{
              alert('Password Error !')
              user.load = false
              return false
            }
          }
        }
        alert('Account not exist !')
        user.load = false
        return false
      })
    }

    // Register
    const register = async  () => {
      user.load = true
      axios.post('http://localhost:8181/getuser/adduser',user).then(function (resp){
        console.log(resp);
      })
      alert('Register successfully')
      user.load = false
    }
    return {...toRefs(user), signin, register}
  }
}
</script>