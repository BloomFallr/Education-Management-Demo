<script setup>
import { useUserStore } from '@/stores'
import { teaGetInfoService } from '@/api/tea'
import { ref, onMounted } from 'vue'
import PasswordEdit from '@/components/PasswordEdit.vue'
const userStore = useUserStore()
const info = ref({})
const getInfo = async () => {
  const res = await teaGetInfoService(userStore.id)
  info.value = res.data.data
}
onMounted(() => {
  getInfo()
})
const passwordEditRef = ref()
const onEditPassword = () => {
  passwordEditRef.value.open()
}
</script>
<template>
  <div class="info-page">
    <el-card class="box-card">
      <el-descriptions
        title="我的信息"
        :column="1"
        class="MyInfoDescriptions"
        border
      >
        <template #extra>
          <el-button size="small" type="primary" @click="onEditPassword" plain
            >修改密码</el-button
          >
        </template>
        <el-descriptions-item>
          <template #label>
            <span style="color: var(--el-color-primary)">姓名</span>
          </template>
          {{ info.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <span style="color: var(--el-color-primary)">工号</span>
          </template>
          {{ info.username }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
  <password-edit ref="passwordEditRef" identity="2"></password-edit>
</template>
<style scoped>
.box-card {
  width: 600px;
  margin-left: auto;
  margin-right: auto;
  background-color: antiquewhite;
}
</style>
