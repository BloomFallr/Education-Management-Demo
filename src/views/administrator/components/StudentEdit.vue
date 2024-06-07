<script setup>
import { ref } from 'vue'
import { admInsertStudentService, admUpdateStudentService } from '@/api/adm'
const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formModel = ref({
  id: '',
  username: '',
  name: '',
  department: '',
  obtainedCredit: '',
  failedCredit: '',
  gpa: ''
})
const rules = ref({
  username: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    {
      pattern: /^\S{1,10}$/,
      message: '学号必须为1-10位非空字符',
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
  ],
  department: [
    { required: true, message: '请选择学院', trigger: 'blur' },
    {
      pattern: /^\S{1,15}$/,
      message: '学院必须为1-15位非空字符',
      trigger: 'blur'
    }
  ]
})
const open = (row) => {
  dialogVisible.value = true
  formModel.value = { ...row }
  if (JSON.stringify(row) == '{}') {
    formModel.value.obtainedCredit = 0
    formModel.value.failedCredit = 0
    formModel.value.gpa = 0
  }
}
defineExpose({ open })
const formRef = ref()
const onClose = () => {
  formRef.value.clearValidate()
  dialogVisible.value = false
}
const onSubmit = async () => {
  await formRef.value.validate()
  const isEdit = formModel.value.id
  if (isEdit) {
    await admUpdateStudentService(formModel.value)
    ElMessage.success('编辑成功')
  } else {
    await admInsertStudentService(formModel.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  emit('success')
}
</script>
<template>
  <el-dialog
    v-model="dialogVisible"
    :title="formModel.id ? '编辑学生' : '添加学生'"
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
      <el-form-item label="学号" prop="username">
        <el-input
          placeholder="请输入学号"
          v-model="formModel.username"
        ></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input placeholder="请输入姓名" v-model="formModel.name"></el-input>
      </el-form-item>
      <el-form-item label="学院" prop="department">
        <el-input
          placeholder="请输入学院"
          v-model="formModel.department"
        ></el-input>
      </el-form-item>
      <el-form-item label="已获学分" prop="obtainedCredit">
        <el-input disabled v-model="formModel.obtainedCredit"></el-input>
      </el-form-item>
      <el-form-item label="不及格学分" prop="failedCredit">
        <el-input disabled v-model="formModel.failedCredit"></el-input>
      </el-form-item>
      <el-form-item label="平均绩点" prop="gpa">
        <el-input disabled v-model="formModel.gpa"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="onClose">取消</el-button>
        <el-button type="primary" @click="onSubmit">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<style scoped></style>
