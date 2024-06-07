<script setup>
import { ref } from 'vue'
import { admUpdateTeacherService, admInsertTeacherService } from '@/api/adm'
const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const open = (row) => {
  dialogVisible.value = true
  formModel.value = { ...row }
}
defineExpose({ open })
const formModel = ref({
  id: '',
  username: '',
  name: ''
})
const rules = ref({
  username: [
    { required: true, message: '请输入工号', trigger: 'blur' },
    {
      pattern: /^\S{1,10}$/,
      message: '工号必须为1-10位非空字符',
      trigger: 'blur'
    }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    {
      pattern: /^\S{1,10}$/,
      message: '姓名必须为1-10位非空字符',
      trigger: 'blur'
    }
  ]
})
const formRef = ref()
const onSubmit = async () => {
  await formRef.value.validate()
  const isEdit = formModel.value.id
  if (isEdit) {
    await admUpdateTeacherService(formModel.value)
    ElMessage.success('编辑成功')
  } else {
    await admInsertTeacherService(formModel.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  emit('success')
}
</script>
<template>
  <el-dialog
    v-model="dialogVisible"
    :title="formModel.id ? '编辑教师' : '添加教师'"
    width="500"
    draggable
    overflow
  >
    <el-form
      ref="formRef"
      :model="formModel"
      :rules="rules"
      label-width="100px"
      style="padding-right: 30px"
    >
      <el-form-item label="工号" prop="username">
        <el-input
          placeholder="请输入工号"
          v-model="formModel.username"
        ></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input placeholder="请输入姓名" v-model="formModel.name"></el-input>
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
<style scoped></style>
