<script setup>
import { ref } from 'vue'
import { User, Lock } from '@element-plus/icons-vue'
import { admLoginService } from '@/api/adm'
import { stuLoginService } from '@/api/stu'
import { teaLoginService } from '@/api/tea'
import { useUserStore } from '@/stores'
import router from '@/router'

const form = ref()
const userStore = useUserStore()
const formModel = ref({
  username: '',
  password: '',
  identity: ''
})
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: /^\S{6,20}$/,
      message: '密码必须为6-20位非空字符',
      trigger: 'blur'
    }
  ],
  identity: [{ required: true, message: '请选择身份', trigger: 'change' }]
}
const login = async () => {
  await form.value.validate()
  if (formModel.value.identity == 1) {
    const res = await stuLoginService(formModel.value)
    userStore.setStuToken(res.data.data.token)
    userStore.setAdmToken(null)
    userStore.setTeaToken(null)
    userStore.setId(res.data.data.id)
  } else if (formModel.value.identity == 2) {
    const res = await teaLoginService(formModel.value)
    userStore.setTeaToken(res.data.data.token)
    userStore.setAdmToken(null)
    userStore.setStuToken(null)
    userStore.setId(res.data.data.id)
  } else if (formModel.value.identity == 3) {
    const res = await admLoginService(formModel.value)
    userStore.setAdmToken(res.data.data.token)
    userStore.setTeaToken(null)
    userStore.setStuToken(null)
    userStore.setId(res.data.data.id)
  }
  ElMessage.success('登录成功')
  if (formModel.value.identity == 1) {
    router.push('/stu')
  } else if (formModel.value.identity == 2) {
    router.push('/tea')
  } else if (formModel.value.identity == 3) {
    router.push('/adm')
  }
}
</script>

<template>
  <div class="login-page">
    <el-card class="box-card">
      <el-form
        :model="formModel"
        ref="form"
        size="large"
        autocomplete="off"
        :rules="rules"
      >
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
            :prefix-icon="User"
            placeholder="请输入用户名"
            v-model="formModel.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            :prefix-icon="Lock"
            placeholder="请输入密码"
            type="password"
            v-model="formModel.password"
          ></el-input>
        </el-form-item>
        <el-form-item prop="identity" class="radio-group">
          <el-radio-group v-model="formModel.identity">
            <el-radio :value="1" border>学生</el-radio>
            <el-radio :value="2" border>教师</el-radio>
            <el-radio :value="3" border>管理员</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button
            @click="login"
            class="button"
            type="primary"
            auto-insert-space
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style lang="scss">
.login-page {
  background-image: url('@/assets/login-page-background.jpg');
  background-position: center center;
  background-repeat: no-repeat;
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  position: fixed;
}
.box-card {
  background-color: transparent;
  border: 0cap;
  margin-top: 225px;
  height: 410px;
  margin-left: auto;
  margin-right: auto;
  width: 540px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
