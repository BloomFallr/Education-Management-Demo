<script setup>
import { ref } from 'vue'
import { admUpdateCourseService, admInsertCourseService } from '@/api/adm.js'

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formModel = ref({
  id: '',
  classId: '',
  name: '',
  teacher: '',
  credit: '',
  selectedStudents: '',
  maxStudents: '',
  year: '',
  semester: '',
  time: '',
  place: ''
})
const rules = {
  classId: [
    { required: true, message: '请输入课堂号', trigger: 'blur' },
    { min: 1, max: 10, message: '课堂号长度不超过10', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入课程名称', trigger: 'blur' },
    { min: 1, max: 15, message: '课程名称长度不超过15', trigger: 'blur' }
  ],
  teacher: [
    { required: true, message: '请输入教师姓名', trigger: 'blur' },
    { min: 1, max: 10, message: '教师姓名长度不超过10', trigger: 'blur' }
  ],
  credit: [
    { required: true, message: '请输入学分', trigger: 'blur' },
    { pattern: /^[1-9]\d*$/, message: '输入必须为非零整数' }
  ],
  maxStudents: [
    { required: true, message: '请输入人数上限', trigger: 'blur' },
    { pattern: /^\d+$/, message: '输入必须为非负整数' },
    {
      validator: (rule, value, callback) => {
        if (parseInt(value) < parseInt(formModel.value.selectedStudents)) {
          callback(new Error('人数上限不能少于已选人数'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  year: [
    { required: true, message: '请输入学年', trigger: 'blur' },
    { pattern: /^[1-9]\d*$/, message: '输入必须为非零整数' }
  ],
  semester: [{ required: true, message: '请选择学期', trigger: 'change' }],
  time: [
    { required: true, message: '请输入上课时间', trigger: 'change' },
    { min: 1, max: 50, message: '上课时间长度不超过50', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        let regex1 = /^周(一|二|三|四|五|六|日)第(\d+)到(\d+)节$/
        let regex2 = /^周(一|二|三|四|五|六|日)第(\d+)节$/
        let substrings = value.split(',')
        for (let i = 0; i < substrings.length; i++) {
          let matches1 = regex1.exec(substrings[i])
          let matches2 = regex2.exec(substrings[i])
          if (!matches1 && !matches2) {
            callback(new Error('时间格式错误'))
          }
          if (matches1) {
            let sectionStart = parseInt(matches1[2])
            let sectionEnd = parseInt(matches1[3])
            if (sectionEnd < sectionStart || sectionEnd > 13) {
              callback(new Error('时间格式错误'))
            }
          } else {
            let section = parseInt(matches2[2])
            if (section > 13) {
              callback(new Error('时间格式错误'))
            }
          }
        }
        callback()
      },
      trigger: 'blur'
    }
  ],
  place: [
    { required: true, message: '请输入上课地点', trigger: 'blur' },
    { min: 1, max: 15, message: '上课地点长度不超过15', trigger: 'blur' }
  ]
}
const open = (row) => {
  dialogVisible.value = true
  formModel.value = { ...row }
  if (JSON.stringify(row) == '{}') {
    formModel.value.selectedStudents = 0
  }
}
defineExpose({ open })
const formRef = ref()
const onClose = () => {
  formRef.value.clearValidate()
  dialogVisible.value = false
}
const onSubmit = async () => {
  await formRef.value?.validate()
  const isEdit = formModel.value.id
  if (isEdit) {
    await admUpdateCourseService(formModel.value)
    ElMessage.success('编辑成功')
  } else {
    await admInsertCourseService(formModel.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  emit('success')
}
</script>

<template>
  <el-dialog
    v-model="dialogVisible"
    :title="formModel.id ? '编辑课程' : '添加课程'"
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
      <el-form-item label="课堂号" prop="classId">
        <el-input
          placeholder="请输入课堂号"
          v-model="formModel.classId"
        ></el-input>
      </el-form-item>
      <el-form-item label="课程名称" prop="name">
        <el-input
          placeholder="请输入课程名称"
          v-model="formModel.name"
        ></el-input>
      </el-form-item>
      <el-form-item label="授课老师" prop="teacher">
        <el-input
          placeholder="请输入教师姓名"
          v-model="formModel.teacher"
        ></el-input>
      </el-form-item>
      <el-form-item label="学分" prop="credit">
        <el-input
          placeholder="请输入学分"
          v-model="formModel.credit"
        ></el-input>
      </el-form-item>
      <el-form-item label="学年" prop="year">
        <el-input placeholder="请输入学年" v-model="formModel.year"></el-input>
      </el-form-item>
      <el-form-item label="学期" prop="semester">
        <el-select v-model="formModel.semester" placeholder="学期" clearable>
          <el-option label="春季学期" value="春季学期" />
          <el-option label="夏季学期" value="夏季学期" />
          <el-option label="秋季学期" value="秋季学期" />
        </el-select>
      </el-form-item>
      <el-form-item label="上课时间" prop="time">
        <el-input
          placeholder="请输入上课时间"
          v-model="formModel.time"
        ></el-input>
      </el-form-item>
      <el-form-item label="上课地点" prop="place">
        <el-input
          placeholder="请输入上课地点"
          v-model="formModel.place"
        ></el-input>
      </el-form-item>
      <el-form-item label="已选人数" prop="selectedStudents">
        <el-input disabled v-model="formModel.selectedStudents"></el-input>
      </el-form-item>
      <el-form-item label="人数上限" prop="maxStudents">
        <el-input
          placeholder="请输入人数上限"
          v-model="formModel.maxStudents"
        ></el-input>
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
<style></style>
