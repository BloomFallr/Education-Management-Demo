<script setup>
import { ref, onMounted } from 'vue'
import {
  stuGetCourseListService,
  stuSelectCourseService,
  stuCancelCourseService,
  stuGetSelectedCourseListService
} from '@/api/stu'
import { useUserStore } from '@/stores'
const loading = ref(false)
const courseData = ref([])
const selectedCourseData = ref([])
const total = ref(0)
const userStore = useUserStore()
const params = ref({
  name: '',
  teacher: '',
  time: '',
  place: '',
  selectState: '',
  courseState: 1,
  currentPage: 1,
  pageSize: 10
})
const getCourse = async () => {
  loading.value = true
  const res = await stuGetCourseListService(params.value)
  courseData.value = res.data.data.list
  total.value = res.data.data.total
  loading.value = false
  const selectedRes = await stuGetSelectedCourseListService(userStore.id)
  selectedCourseData.value = selectedRes.data.data
}
const isItemInSelectedCourse = (id) => {
  if (selectedCourseData.value.length === 0) {
    return false
  }
  return selectedCourseData.value.some((item) => item.id === id)
}
const onSearch = () => {
  params.value.currentPage = 1
  getCourse()
}
const onReset = () => {
  params.value.currentPage = 1
  params.value.name = ''
  params.value.teacher = ''
  params.value.time = ''
  params.value.place = ''
  params.value.selectState = ''
  params.value.currentPage = 1
  params.value.pageSize = 10
  getCourse()
}
onMounted(() => {
  getCourse()
})
//分页条
const onHandleCurrentChange = (value) => {
  params.value.currentPage = value
  getCourse()
}
const onHandleSizeChange = (value) => {
  params.value.pageSize = value
  getCourse()
}
//进度条
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
//选课，退课
const onSelectCourse = async (row) => {
  if (row.selectedStudents === row.maxStudents) {
    ElMessage.warning('该课程已满')
    return
  }
  await stuSelectCourseService(userStore.id, row.id)
  ElMessage.success('选课成功')
  getCourse()
}
const onCancelCourse = async (row) => {
  await ElMessageBox.confirm('您确认退课吗？', '温馨提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await stuCancelCourseService(userStore.id, row.id)
    ElMessage.success('退课成功')
  })
  getCourse()
}
</script>
<template>
  <el-container>
    <el-header>
      <el-form inline>
        <el-form-item>
          <el-input v-model="params.name" placeholder="课程名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="params.teacher" placeholder="任课教师"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="params.time" placeholder="上课时间"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="params.place" placeholder="上课地点"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="params.selectState"
            placeholder="选课状态"
            style="width: 200px"
            clearable
          >
            <el-option label="选课未结束" value="1" />
            <el-option label="选课已结束" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item style="float: inline-end">
          <el-button type="primary" @click="onReset" round plain
            >重置</el-button
          >
        </el-form-item>
        <el-form-item style="float: inline-end">
          <el-button type="primary" @click="onSearch" round plain
            >搜索</el-button
          >
        </el-form-item>
      </el-form>
    </el-header>
    <el-main>
      <el-table
        v-loading="loading"
        :data="courseData"
        border
        style="width: 100%"
        height="550px"
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
        <el-table-column label="状态" width="85">
          <template #default="{ row }">
            <el-tag type="success" round v-if="row.selectState == 1"
              >正在选课</el-tag
            >
            <el-tag type="warning" round v-else>选课结束</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="140">
          <template #default="{ row }">
            <el-button
              size="small"
              type="primary"
              plain
              @click="onSelectCourse(row)"
              :disabled="isItemInSelectedCourse(row.id) || row.selectState == 0"
              >选课</el-button
            >
            <el-button
              size="small"
              type="danger"
              plain
              @click="onCancelCourse(row)"
              :disabled="
                !isItemInSelectedCourse(row.id) || row.selectState == 0
              "
              >退课</el-button
            >
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="没有数据" />
        </template>
      </el-table>
      <el-pagination
        v-model:current-page="params.currentPage"
        v-model:page-size="params.pageSize"
        :background="true"
        :page-sizes="[10, 15, 20, 25]"
        layout="sizes, total, prev, pager, next, jumper"
        :total="total"
        @current-change="onHandleCurrentChange"
        @size-change="onHandleSizeChange"
        style="float: inline-end; margin-top: 10px"
      />
    </el-main>
  </el-container>
</template>
<style scoped></style>
