<script setup>
import { ref } from 'vue'
import { teaUpdateGradeService, teaGetEnrollmentService } from '@/api/tea'
const dialogVisible = ref(false)
const course = ref()
const data = ref([])
const loading = ref(false)
const open = async (row) => {
  dialogVisible.value = true
  loading.value = true
  course.value = row
  const res = await teaGetEnrollmentService(course.value.id)
  data.value = res.data.data
  loading.value = false
}
defineExpose({ open })
const onSubmit = async () => {
  console.log('submit')
  await teaUpdateGradeService(data.value)
  ElMessage.success('编辑成功')
  dialogVisible.value = false
}
</script>
<template>
  <el-dialog
    v-model="dialogVisible"
    width="500"
    draggable
    overflow
    style="width: 600px"
  >
    <template #header>
      <div class="my-header">
        <h4>{{ course.name }}/{{ course.classId }}</h4>
      </div>
    </template>
    <el-table
      v-loading="loading"
      :data="data"
      border
      style="width: 100%"
      height="400px"
      stripe
      :header-cell-style="{
        background: '#b69e9e',
        color: 'black',
        fontWeight: 1000
      }"
    >
      <el-table-column prop="studentUsername" label="学号" />
      <el-table-column prop="studentName" label="姓名" />
      <el-table-column label="是否及格">
        <template #default="{ row }">
          <el-select v-model="row.isPassed" clearable>
            <el-option label="及格" value="true" />
            <el-option label="不及格" value="false" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="绩点">
        <template #default="{ row }">
          <el-select v-model="row.gradePoint" clearable>
            <el-option-group label="及格">
              <el-option label="4.3" value="4.3" />
              <el-option label="4.0" value="4.0" />
              <el-option label="3.7" value="3.7" />
              <el-option label="3.3" value="3.3" />
              <el-option label="3.0" value="3.0" />
              <el-option label="2.7" value="2.7" />
              <el-option label="2.3" value="2.3" />
              <el-option label="2.0" value="2.0" />
              <el-option label="1.7" value="1.7" />
              <el-option label="1.5" value="1.5" />
              <el-option label="1.3" value="1.3" />
              <el-option label="1.0" value="1.0" />
            </el-option-group>
            <el-option-group label="不及格">
              <el-option label="0" value="0" />
            </el-option-group>
          </el-select>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<style scoped></style>
