<script setup>
import { ref, markRaw, onMounted } from 'vue'
import { Search, Edit, Delete } from '@element-plus/icons-vue'
import StudentEdit from './components/StudentEdit.vue'
import { admGetStudentListService, admDeleteStudentService } from '@/api/adm'

const loading = ref(false)
const studentData = ref([])
const total = ref(0)
const params = ref({
  name: '',
  department: '',
  pageSize: 10,
  currentPage: 1
})
const getStudentList = async () => {
  loading.value = true
  const res = await admGetStudentListService(params.value)
  studentData.value = res.data.data.list
  total.value = res.data.data.total
  loading.value = false
}
const onSearch = () => {
  params.value.currentPage = 1
  getStudentList()
}
const onReset = () => {
  params.value.name = ''
  params.value.department = ''
  params.value.currentPage = 1
  getStudentList()
}
onMounted(() => {
  getStudentList()
})
const studentEditRef = ref()
const onInsertStudent = () => {
  studentEditRef.value.open({})
}
const onUpdateStudent = (row) => {
  studentEditRef.value.open(row)
}
const onSuccess = () => {
  getStudentList()
}
const onDeleteStudent = async (row) => {
  await ElMessageBox.confirm('您确定要删除该学生吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    icon: markRaw(Delete)
  }).then(async () => {
    await admDeleteStudentService(row.id)
    ElMessage.success('删除成功')
  })
  getStudentList()
}
const onHandleCurrentChange = (value) => {
  params.value.currentPage = value
  getStudentList()
}
const onHandleSizeChange = (value) => {
  params.value.pageSize = value
  getStudentList()
}
</script>

<template>
  <el-container>
    <el-header>
      <el-form inline>
        <el-form-item>
          <el-input
            v-model="params.name"
            placeholder="姓名"
            :prefix-icon="Search"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="params.department"
            placeholder="学院"
            :prefix-icon="Search"
          ></el-input>
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
        :data="studentData"
        border
        style="width: 100%"
        height="580px"
        v-loading="loading"
      >
        <el-table-column prop="username" label="学号" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="department" label="学院" />
        <el-table-column prop="obtainedCredit" label="已获学分" />
        <el-table-column prop="failedCredit" label="不及格学分" />
        <el-table-column prop="gpa" label="平均绩点" />
        <el-table-column fixed="right" label="操作">
          <template #default="{ row }">
            <el-button
              size="small"
              type="primary"
              @click="onUpdateStudent(row)"
              :icon="Edit"
              circle
              plain
            ></el-button>
            <el-button
              size="small"
              type="danger"
              @click="onDeleteStudent(row)"
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
          <el-button type="primary" @click="onInsertStudent" plain
            >添加学生</el-button
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
  <student-edit ref="studentEditRef" @success="onSuccess"></student-edit>
</template>

<style lang="scss" scoped></style>
