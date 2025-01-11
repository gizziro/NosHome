import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ViewMenus from '../views/ViewMenus.vue'
import ViewMembers from '../views/ViewMembers.vue'
import ViewGroups from '../views/ViewGroups.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/menus',
      name: 'menus',
      component: () => import('../views/ViewMenus.vue'),
    },
    {
      path: '/members',
      name: 'members',
      component: () => import('../views/ViewMembers.vue'),
    },
    {
      path: '/groups',
      name: 'groups',
      component: () => import('../views/ViewGroups.vue'),
    },
  ],
})

export default router
