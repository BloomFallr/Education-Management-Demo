<script setup>
import { ref } from 'vue'
import { Lock } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores'
import { admEditPasswordService } from '@/api/adm'
import { stuEditPasswordService } from '@/api/stu'
import { teaEditPasswordService } from '@/api/tea'
const props = defineProps({
  identity: String
})
const dialogVisible = ref(false)
const form = ref()
const userStore = useUserStore()
const formModel = ref({
  password: '',
  repassword: ''
})
const rules = {
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      pattern: /^\S{6,20}$/,
      message: '密码必须为6-20位非空字符',
      trigger: 'blur'
    }
  ],
  repassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (
          formModel.value.password !== '' &&
          value !== formModel.value.password
        ) {
          callback(new Error('两次密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}
const open = () => {
  formModel.value.password = ''
  formModel.value.repassword = ''
  dialogVisible.value = true
}
const onSubmit = async () => {
  await form.value?.validate()
  const data = {
    id: userStore.id,
    password: formModel.value.password
  }
  if (props.identity == 1) {
    console.log('学生')
    await stuEditPasswordService(data)
  } else if (props.identity == 2) {
    console.log('教师')
    await teaEditPasswordService(data)
  } else if (props.identity == 3) {
    console.log('管理员')
    await admEditPasswordService(data)
  }
  ElMessage.success('编辑成功')
  dialogVisible.value = false
}
defineExpose({ open })
</script>

<template>
  <el-dialog
    v-model="dialogVisible"
    title="修改密码"
    width="400"
    draggable
    overflow
  >
    <el-form
      :model="formModel"
      ref="form"
      size="large"
      autocomplete="off"
      :rules="rules"
    >
      <el-form-item prop="password">
        <el-input
          :prefix-icon="Lock"
          placeholder="请输入新密码"
          type="password"
          v-model="formModel.password"
        ></el-input>
      </el-form-item>
      <el-form-item prop="repassword">
        <el-input
          :prefix-icon="Lock"
          placeholder="请再次输入新密码"
          type="password"
          v-model="formModel.repassword"
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style></style>
