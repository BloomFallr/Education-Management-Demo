<template>
  <el-container>
    <el-header>
      <el-menu
        :default-active="$route.path"
        class="el-menu-demo"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        mode="horizontal"
        :ellipsis="false"
        router
      >
        <div class="flex-grow" />
        <el-menu-item index="/tea/home">首页</el-menu-item>
        <el-menu-item index="/tea/course">我的课程</el-menu-item>
        <el-menu-item index="/tea/info">个人信息</el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script setup></script>
<style scoped>
.flex-grow {
  flex-grow: 1;
}
</style>
