import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/LoginPage.vue')
    },
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/adm',
      component: () => import('@/views/administrator/LayoutPage.vue'),
      children: [
        {
          path: '/adm/course',
          component: () => import('@/views/administrator/CourseManagement.vue')
        },
        {
          path: '/adm/student',
          component: () => import('@/views/administrator/StudentManagement.vue')
        },
        {
          path: '/adm/teacher',
          component: () => import('@/views/administrator/TeacherManagement.vue')
        },
        {
          path: '/adm/info',
          component: () => import('@/views/administrator/MyInfo.vue')
        },
        {
          path: '/adm/home',
          component: () => import('@/views/administrator/HomePage.vue')
        },
        {
          path: '/adm',
          redirect: '/adm/home'
        }
      ]
    },
    {
      path: '/stu',
      component: () => import('@/views/student/LayoutPage.vue'),
      children: [
        {
          path: '/stu/course',
          component: () => import('@/views/student/SelectCourse.vue')
        },
        {
          path: '/stu/score',
          component: () => import('@/views/student/MyScore.vue')
        },
        {
          path: '/stu/timetable',
          component: () => import('@/views/student/MyTimetable.vue')
        },
        {
          path: '/stu/info',
          component: () => import('@/views/student/MyInfo.vue')
        },
        {
          path: '/stu/home',
          component: () => import('@/views/student/HomePage.vue')
        },
        {
          path: '/stu',
          redirect: '/stu/home'
        }
      ]
    },
    {
      path: '/tea',
      component: () => import('@/views/teacher/LayoutPage.vue'),
      children: [
        {
          path: '/tea/home',
          component: () => import('@/views/teacher/HomePage.vue')
        },
        {
          path: '/tea/course',
          component: () => import('@/views/teacher/MyCourse.vue')
        },
        {
          path: '/tea/info',
          component: () => import('@/views/teacher/MyInfo.vue')
        },
        {
          path: '/tea',
          redirect: '/tea/home'
        }
      ]
    }
  ]
})

export default router
