<script setup>
import { ref, onMounted } from 'vue'
import { formateDate } from '@/utils/format'
const data = ref('')
onMounted(() => {
  data.value = formateDate(new Date())
})
</script>
<template>
  <div class="home-page">
    <div class="greeting">
      <span class="hello">欢迎进入选课系统</span>
      <hr />
      <span class="data">{{ data }}</span>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.home-page {
  background-image: url('@/assets/home-page-background.jpg');
  background-position: center center;
  background-repeat: no-repeat;
  width: 100%;
  height: 100vh;
  background-size: 100% 100%;
  position: fixed;
}
.greeting {
  margin-left: auto;
  margin-right: auto;
  margin-top: 200px;
  width: 400px;
  .hello {
    font-size: 50px;
    color: rgb(25, 85, 113);
  }
  .data {
    float: inline-end;
    font-size: larger;
    color: rgb(35, 108, 147);
  }
}
</style>
