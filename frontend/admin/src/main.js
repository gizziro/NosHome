import "./assets/main.css"
import axios            from 'axios'
import { createApp }    from 'vue'
import { createPinia }  from 'pinia'
import router           from './router'
import App              from './App.vue'
import PrimeVue         from 'primevue/config';
import Aura             from '@primevue/themes/aura';
import ToastService from 'primevue/toastservice';

axios.defaults.withCredentials = true

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(PrimeVue, {
  theme: {
    preset: Aura
  }
});
app.use(ToastService);
app.mount('#app')
