<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import CourseEdit from './components/CourseEdit.vue'
import {
  admGetCourseListService,
  admChangeSelectStateService,
  admDeleteCourseService
} from '@/api/adm.js'
import { onMounted, ref, markRaw } from 'vue'
const courseData = ref([])
const total = ref(0)
const multipleSelection = ref([])
const courseEditRef = ref()
const loading = ref(false)
const params = ref({
  name: '',
  teacher: '',
  year: '',
  semester: '',
  time: '',
  place: '',
  selectState: '',
  courseState: '',
  pageSize: 10,
  currentPage: 1
})
const getCourseList = async () => {
  loading.value = true
  const res = await admGetCourseListService(params.value)
  courseData.value = res.data.data.list
  total.value = res.data.data.total
  loading.value = false
}
const onSearch = () => {
  params.value.currentPage = 1
  getCourseList()
}
const onReset = () => {
  params.value.currentPage = 1
  params.value.name = ''
  params.value.teacher = ''
  params.value.year = ''
  params.value.semester = ''
  params.value.time = ''
  params.value.place = ''
  params.value.selectState = ''
  params.value.courseState = ''
  getCourseList()
}
onMounted(() => {
  getCourseList()
})
const onHandleCurrentChange = (value) => {
  params.value.currentPage = value
  getCourseList()
}
const onHandleSizeChange = (value) => {
  params.value.pageSize = value
  getCourseList()
}
const onHandleSelectionChange = (value) => {
  multipleSelection.value = value.map((item) => item.id)
}
const onChangeSelectionEndState = async (state) => {
  await admChangeSelectStateService(multipleSelection.value.join(','), state)
  getCourseList()
}
const onUpdateCourse = (row) => {
  courseEditRef.value.open(row)
}
const onInsertCourse = () => {
  courseEditRef.value.open({})
}
const onDeleteCourse = async (row) => {
  await ElMessageBox.confirm('您确认删除该课程吗？', '温馨提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
    icon: markRaw(Delete)
  }).then(async () => {
    await admDeleteCourseService(row.id)
    ElMessage.success('删除成功')
    getCourseList()
  })
}
const onSuccess = () => {
  getCourseList()
}
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
</script>

<template>
  <el-container>
    <el-header>
      <el-form inline>
        <el-form-item>
          <el-input
            v-model="params.name"
            placeholder="课程名称"
            style="width: 125px"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="params.teacher"
            placeholder="任课教师"
            style="width: 125px"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="params.year"
            placeholder="学年"
            style="width: 125px"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="params.semester"
            placeholder="学期"
            style="width: 125px"
            clearable
          >
            <el-option label="春季学期" value="春季学期" />
            <el-option label="夏季学期" value="夏季学期" />
            <el-option label="秋季学期" value="秋季学期" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="params.time"
            placeholder="时间"
            style="width: 125px"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="params.place"
            placeholder="地点"
            style="width: 125px"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="params.selectState"
            placeholder="选课状态"
            style="width: 125px"
            clearable
          >
            <el-option label="选课未结束" value="1" />
            <el-option label="选课已结束" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="params.courseState"
            placeholder="课程状态"
            style="width: 125px"
            clearable
          >
            <el-option label="未结课" value="1" />
            <el-option label="已结课" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item style="float: inline-end">
          <el-button
            type="primary"
            @click="onReset"
            round
            plain
            style="width: 30"
            >重置</el-button
          >
        </el-form-item>
        <el-form-item style="float: inline-end">
          <el-button
            type="primary"
            @click="onSearch"
            round
            plain
            style="width: 30"
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
        height="580px"
        @selection-change="onHandleSelectionChange"
      >
        <el-table-column type="selection" width="55" fixed="left" />
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
        <el-table-column fixed="right" label="操作" width="85">
          <template #default="{ row }">
            <el-button
              size="small"
              type="primary"
              @click="onUpdateCourse(row)"
              :icon="Edit"
              circle
              plain
            ></el-button>
            <el-button
              size="small"
              type="danger"
              @click="onDeleteCourse(row)"
              :icon="Delete"
              circle
              plain
            ></el-button>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="没有数据" />
        </template>
      </el-table>
      <el-form inline style="margin-top: 10px; text-align: right">
        <el-form-item>
          <el-button type="success" @click="onChangeSelectionEndState(1)" plain
            >开放选课</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="onChangeSelectionEndState(0)" plain
            >结束选课</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onInsertCourse" plain
            >添加课程</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-pagination
            v-model:current-page="params.currentPage"
            v-model:page-size="params.pageSize"
            :background="true"
            :page-sizes="[10, 15, 20, 25]"
            layout="sizes, total, prev, pager, next, jumper"
            :total="total"
            @current-change="onHandleCurrentChange"
            @size-change="onHandleSizeChange"
          />
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
  <course-edit ref="courseEditRef" @success="onSuccess"></course-edit>
</template>

<style lang="scss" scoped></style>
