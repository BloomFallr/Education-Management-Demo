import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate' //引入持久化插件

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

export default pinia
export * from './user'
