<script setup>
import { ref, computed } from "vue";
import { PhArrowsLeftRight, PhX } from "@phosphor-icons/vue";
import { storeToRefs } from "pinia";
import NavItem from "@/components/common/NavItem.vue";
import {useMenuStore} from "@/stores/menuStore.js";

const menuStore = useMenuStore();
const { isSidebarExpanded, isSidebarHovered, isMobileSidebarVisible} = storeToRefs(menuStore);

// 사이드바 토글 핸들러
const sidebarResizeBtn = () => {
  isSidebarHovered.value = false;
  isSidebarExpanded.value = !isSidebarExpanded.value;
};


const nav_item_header = computed(() => {
  return {
    'sidebar-resize-hide': !(isSidebarExpanded.value || isSidebarHovered.value),
  };
});

</script>


<template>
  <div class="h-full lg:block"
       :class="[isSidebarExpanded ? 'lg:w-[18.75rem]' : 'absolute w-20',
       isSidebarHovered ? 'w-[18.75rem]' : 'w-20',
       isMobileSidebarVisible ? 'block absolute w-[18.75rem]' : 'hidden'

  ]">
    <!-- Sidebar -->
    <div
        :class="[
        'flex flex-col bg-[#252b36] text-white h-full']"
        @mouseenter="isSidebarHovered = true"
        @mouseleave="isSidebarHovered = false"
    >
      <!-- Sidebar header -->
      <div class="sidebar-section flex w-full justify-between items-center border-b border-b-white/10">
        <!-- logo -->
        <div class="sidebar-logo flex w-full justify-center items-center px-[20px] py-[10px]">
          <a href="/" class="inline-flex py-2 items-center py-[10px]">
            <img src="/images/logo_icon.svg" class="sidebar-logo-icon h-[22px]" alt="Logo Icon" />
            <img v-if="isSidebarExpanded || isSidebarHovered"
                src="/images/logo_text_light.svg"
                class="h-[14px] ml-[20px]"
                alt="Logo Text"
            />
          </a>

          <!-- 사이드바 토글러 -->
          <div  v-if="isSidebarExpanded || isSidebarHovered" class="sidebar-toggler flex items-center ml-auto">
            <button class="hidden lg:block lg:bg-[#383D46] lg:rounded-full lg:p-2 lg:color-white lg:font-600"
                @click="sidebarResizeBtn"
            >
              <PhArrowsLeftRight :size="20" />
            </button>

            <button class="lg:hidden lg:bg-[#383D46] lg:rounded-full lg:p-2 lg:color-white lg:font-600"
                    @click="isMobileSidebarVisible = false"
            >
              <PhX :size="20" />
            </button>



          </div>
        </div>
      </div>

      <!-- Sidebar content -->
      <div class="sidebar-content">

        <ul class="nav nav-sidebar">

          <li class="nav-item-header" :class="nav_item_header">
            <div class="text-uppercase fs-sm lh-sm opacity-50">Main</div>
            <i class="ph-dots-three sidebar-resize-show"></i>
          </li>

          <NavItem title="대시보드" url="/" />
          <NavItem title="회원관리" url="/members" />
          <NavItem title="그룹관리" url="/groups" />
          <NavItem title="메뉴관리" url="/menus" />


        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.nav {
  display       : flex;
  flex-wrap     : wrap;
  padding-left  : 0;
  list-style    : none;
}
.nav-sidebar
{
  flex-direction: column;
}

.nav-item-header
{
  padding : 10px 20px;
}
.sidebar-content
{
  flex : 1;
  overflow-y : auto;
}
</style>
