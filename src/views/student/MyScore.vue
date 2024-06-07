<script setup>
import { stuGetGradeService } from '@/api/stu'
import { useUserStore } from '@/stores'
import { ref, onMounted } from 'vue'
const userStore = useUserStore()
const gradeData = ref([])
const getGrade = async () => {
  const res = await stuGetGradeService(userStore.id)
  gradeData.value = res.data.data
}
onMounted(() => {
  getGrade()
})
</script>
<template>
  <div>
    <el-card class="box-card">
      <el-descriptions
        title="我的成绩"
        :column="4"
        class="MyInfoDescriptions"
        border
      >
        <el-descriptions-item>
          <template #label>
            <span style="color: var(--el-color-primary)">总学分</span>
          </template>
          {{ gradeData.obtainedCredit + gradeData.failedCredit }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <span style="color: var(--el-color-primary)">已获学分</span>
          </template>
          {{ gradeData.obtainedCredit }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <span style="color: var(--el-color-danger)">不及格学分</span>
          </template>
          {{ gradeData.failedCredit }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <span style="color: var(--el-color-primary)">平均绩点</span>
          </template>
          {{ gradeData.gpa }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
    <el-table
      :data="gradeData.gradeList"
      border
      style="width: 100%; height: 600px"
      stripe
    >
      <el-table-column prop="year" label="学年" />
      <el-table-column prop="semester" label="学期" />
      <el-table-column prop="classId" label="课堂号" />
      <el-table-column prop="name" label="课程名称" />
      <el-table-column prop="credit" label="学分" />
      <el-table-column label="是否及格" width="150">
        <template #default="{ row }">{{
          row.isPassed ? '及格' : '不及格'
        }}</template>
      </el-table-column>
      <el-table-column prop="gradePoint" label="绩点" width="150" />
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
  </div>
</template>
<style scoped lang="scss">
.box-card {
  width: 1000px;
  margin-left: auto;
  margin-right: auto;
  background-color: rgb(75, 227, 238);
}
</style>
