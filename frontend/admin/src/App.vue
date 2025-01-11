<script setup>
import { ref, toRef} from "vue";
import { storeToRefs } from "pinia";
import MainSideBar from "@/components/common/MainSideBar.vue";
import { RouterView } from "vue-router";
import {useMenuStore} from "@/stores/menuStore.js";
import MainNavBar from "@/components/common/MainNavBar.vue";

const menuStore = useMenuStore();

const { isSidebarExpanded } = storeToRefs(menuStore);

</script>

<template>
  <div class="page-content flex relative">
    <!-- Main sidebar -->
    <MainSideBar @toggle="toggleSidebar" />
    <!-- Main content -->
    <div class="flex-1" :class="[isSidebarExpanded ? 'lg:m-0' : 'lg:ml-20']">
      <div class="content-wrapper">
        <!-- Main navbar -->
        <MainNavBar />
        <!-- /main navbar -->
        <div class="content-inner">
          <RouterView />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 페이지 기본 레이아웃 */
.page-content {
  height: 100vh; /* 전체 높이 */
  overflow: hidden; /* 스크롤 숨김 */
}

.flex-1 {
  display: flex;
  flex-direction: column;
  overflow-y: auto; /* 메인 콘텐츠 영역 스크롤 */
}
</style>
