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
        <el-menu-item index="/adm/home">首页</el-menu-item>
        <el-menu-item index="/adm/course">课程管理</el-menu-item>
        <el-menu-item index="/adm/student">学生管理</el-menu-item>
        <el-menu-item index="/adm/teacher">教师管理</el-menu-item>
        <el-menu-item index="/adm/info">个人信息</el-menu-item>
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
