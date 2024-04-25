<template>
  <div class="container">
    <div style="width: 500px;">
      <img src="@/assets/imgs/bg.jpg" alt="" style="width: 100%; margin-top: -150px">
    </div>
    <div style="flex: 1; background-color: #f8f8f8">
      <div style="height: 50px; display: flex; align-items: center; background-color: white; padding-left: 20px">
        <img src="@/assets/imgs/logo1.png" alt="" style="width: 40px">
        <span style="font-size: 24px; margin-left: 5px">闲宝</span>
      </div>
      <div style="height: calc(100vh - 50px); display: flex; align-items: center; justify-content: center">
        <div style="width: 400px; padding: 40px; background-color: white; border-radius: 5px;">
          <div style="display: flex; margin-bottom: 50px; font-size: 24px">
            <a style="color: #333" href="/login">
              <div>登录</div>
            </a>
            <div style="margin-left: 10px; border-bottom: 2px solid orangered; padding-bottom: 10px">注册</div>
          </div>
          <el-form :model="form" :rules="rules" ref="formRef">
            <el-form-item prop="username">
              <el-input size="medium" prefix-icon="el-icon-user" placeholder="请输入账号"
                        v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password
                        v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPassword">
              <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请确认密码" show-password
                        v-model="form.confirmPassword"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button size="medium"
                         style="width: 100%; background-color: orangered; border-color: orangered; color: white"
                         @click="login">注 册
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      form: {role: 'USER'},
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirmPassword: [
          {validator: validatePassword, trigger: 'blur'}
        ]
      }
    }
  },
  created() {

  },
  methods: {
    login() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/login')
              this.$message.success('注册成功')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  display: flex;
}

a {
  color: #2a60c9;
}
</style>