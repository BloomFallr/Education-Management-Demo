<script setup>
import { useUserStore } from '@/stores'
import { ref, onMounted } from 'vue'
import { admGetInfoService } from '@/api/adm'
import PasswordEdit from '@/components/PasswordEdit.vue'
const userStore = useUserStore()
const info = ref({})
const getInfo = async () => {
  const res = await admGetInfoService(userStore.id)
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
            <span style="color: var(--el-color-primary)">用户名</span>
          </template>
          {{ info.username }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
  <password-edit ref="passwordEditRef" identity="3"></password-edit>
</template>
<style lang="scss" scoped>
.box-card {
  width: 600px;
  margin-left: auto;
  margin-right: auto;
  background-color: antiquewhite;
}
</style>
