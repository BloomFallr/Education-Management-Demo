import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore(
  'user',
  () => {
    const adm_token = ref('')
    const setAdmToken = (token) => {
      adm_token.value = token
    }
    const stu_token = ref('')
    const setStuToken = (token) => {
      stu_token.value = token
    }
    const tea_token = ref('')
    const setTeaToken = (token) => {
      tea_token.value = token
    }
    const id = ref(Number)
    const setId = (newId) => {
      id.value = newId
    }
    return {
      adm_token,
      setAdmToken,
      stu_token,
      setStuToken,
      tea_token,
      setTeaToken,
      id,
      setId
    }
  },
  {
    persist: true
  }
)
