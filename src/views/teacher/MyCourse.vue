<script setup>
import { ref, onMounted } from 'vue'
import {
  teaGetTaughtCourseListService,
  teaFinishCourseService
} from '@/api/tea'
import { useUserStore } from '@/stores'
import GradeEdit from './components/GradeEdit.vue'
const loading = ref(false)
const courseData = ref([])
const userStore = useUserStore()
const getTaughtCourse = async () => {
  loading.value = true
  const res = await teaGetTaughtCourseListService(userStore.id)
  courseData.value = res.data.data
  loading.value = false
}
onMounted(() => {
  getTaughtCourse()
})
const setItemProgress = (row) => {
  return parseInt((row.selectedStudents / row.maxStudents).toFixed(1) * 100)
}
const setItemStatus = (row) => {
  if (row.selectedStudents === row.maxStudents) {
    return 'exception'
  }
  return 'success'
}
const setItemText = (row) => {
  return row.selectedStudents + '/' + row.maxStudents
}
const gradeEditRef = ref()
const onCheckEnrollment = (row) => {
  gradeEditRef.value.open(row)
}
const onFinishCourse = async (row) => {
  await ElMessageBox.confirm('您确定结束课程吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await teaFinishCourseService(row.id)
    ElMessage.success('操作成功')
  })
  getTaughtCourse()
}
</script>
<template>
  <el-table
    v-loading="loading"
    :data="courseData"
    border
    style="width: 100%"
    height="600px"
    stripe
  >
    <el-table-column prop="classId" label="课堂号" width="160" />
    <el-table-column prop="name" label="课程名称" width="160" />
    <el-table-column prop="teacher" label="授课老师" width="150" />
    <el-table-column prop="credit" label="学分" width="75" />
    <el-table-column prop="year" label="学年" width="100" />
    <el-table-column prop="semester" label="学期" width="150" />
    <el-table-column prop="time" label="上课时间" width="300" />
    <el-table-column prop="place" label="上课地点" width="100" />
    <el-table-column label="已选/人数上限" width="200">
      <template #default="{ row }">
        <el-progress
          :text-inside="true"
          :stroke-width="18"
          :percentage="setItemProgress(row)"
          :status="setItemStatus(row)"
        >
          <span style="color: black">{{ setItemText(row) }}</span>
        </el-progress>
      </template>
    </el-table-column>
    <el-table-column label="状态" width="160">
      <template #default="{ row }">
        <el-tag type="success" round v-if="row.selectState == 1"
          >正在选课</el-tag
        >
        <el-tag type="warning" round v-else>选课结束</el-tag>
        <el-tag type="success" round v-if="row.courseState == 1"
          >正在上课</el-tag
        >
        <el-tag type="warning" round v-else>课程结束</el-tag>
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="204">
      <template #default="{ row }">
        <el-button
          size="small"
          type="primary"
          plain
          @click="onCheckEnrollment(row)"
          >查看选课情况</el-button
        >
        <el-button
          size="small"
          type="primary"
          plain
          @click="onFinishCourse(row)"
          >结束课程</el-button
        >
      </template>
    </el-table-column>
    <template #empty>
      <el-empty description="没有数据" />
    </template>
  </el-table>
  <grade-edit ref="gradeEditRef"></grade-edit>
</template>
<style scoped></style>
