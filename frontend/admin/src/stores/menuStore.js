import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', () =>
{
  const isSidebarExpanded       = ref(true);    // 사이드바 확장 여부
  const isSidebarHovered        = ref(false);   // 사이드바 마우스 호버 여부
  const isMobileSidebarVisible = ref(false);   // 모바일에서 사이드바 표시 여부

  return { isSidebarExpanded, isSidebarHovered, isMobileSidebarVisible}
})
