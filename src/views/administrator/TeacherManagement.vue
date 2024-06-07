<script setup>
import { ref, markRaw, onMounted } from 'vue'
import { Search, Edit, Delete } from '@element-plus/icons-vue'
import { admGetTeacherListService, admDeleteTeacherService } from '@/api/adm'
import TeacherEdit from './components/TeacherEdit.vue'
const params = ref({
  name: '',
  currentPage: 1,
  pageSize: 10
})
const teacherData = ref([])
const loading = ref(false)
const total = ref(0)
const getTeacherList = async () => {
  loading.value = true
  const res = await admGetTeacherListService(params.value)
  teacherData.value = res.data.data.list
  total.value = res.data.data.total
  loading.value = false
}
onMounted(() => {
  getTeacherList()
})
const onSearch = () => {
  params.value.currentPage = 1
  getTeacherList()
}
const onReset = () => {
  params.value.name = ''
  params.value.currentPage = 1
  getTeacherList()
}
const teacherEditRef = ref()
const onInsertTeacher = () => {
  teacherEditRef.value.open({})
}
const onUpdateTeacher = (row) => {
  teacherEditRef.value.open(row)
}
const onDeleteCourse = async (row) => {
  await ElMessageBox.confirm('您确认删除该教师信息吗？', '温馨提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
    icon: markRaw(Delete)
  }).then(async () => {
    await admDeleteTeacherService(row.id)
    ElMessage.success('删除成功')
    getTeacherList()
  })
}
const onSuccess = () => {
  getTeacherList()
}
const onHandleCurrentChange = (value) => {
  params.value.currentPage = value
  getTeacherList()
}
const onHandleSizeChange = (value) => {
  params.value.pageSize = value
  getTeacherList()
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
        :data="teacherData"
        border
        style="width: 100%"
        height="580px"
        v-loading="loading"
      >
        <el-table-column prop="username" label="工号" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column fixed="right" label="操作">
          <template #default="{ row }">
            <el-button
              size="small"
              type="primary"
              @click="onUpdateTeacher(row)"
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
          <el-button type="primary" @click="onInsertTeacher" plain
            >添加教师</el-button
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
  <teacher-edit ref="teacherEditRef" @success="onSuccess"></teacher-edit>
</template>

<style lang="scss" scoped></style>
