<script setup>
import { ref, toRef} from "vue";
import { storeToRefs } from "pinia";
import MainSideBar from "@/components/layout/MainSideBar.vue";
import { RouterView } from "vue-router";

import MainNavBar from "@/components/layout/MainNavBar.vue";

//----------------------------------------------------------------------------------------------------------------------
//
//----------------------------------------------------------------------------------------------------------------------
import {useAuthStore} from "@/stores/authStore.js";
import {useLayoutStore} from "@/stores/layoutStore.js";
import {useCommonStore} from "@/stores/commonStore.js";
import LoginForm from "@/components/login/LoginForm.vue";

const authStore = useAuthStore();
const commonStore = useCommonStore();
const layoutStore = useLayoutStore();

authStore.refreshCommonData();
const { isSynced, isLogin } = storeToRefs(authStore);
const { isSidebarExpanded } = storeToRefs(layoutStore);

</script>

<template>

  <div class="page-content flex relative min-w-[320px]" v-if="isSynced">
    <template v-if="isLogin">
      <!-- Main sidebar -->
      <MainSideBar/>

      <!-- Main content -->
      <div class="main-content" :class="[isSidebarExpanded ? 'lg:m-0' : 'lg:ml-20']">
        <div class="content-wrapper">
          <!-- Main navbar -->
          <MainNavBar />
          <!-- /main navbar -->
          <div class="content-inner">
            <RouterView />
          </div>
        </div>
      </div>
    </template>
    <!--- 로그인이 안되어 있을 경우 -->
    <template v-else>
      <LoginForm />
    </template>

  </div>
  <div v-else>
    <p>정보 로딩중</p>
  </div>
</template>

<style scoped>
/* 페이지 기본 레이아웃 */
.page-content {
  height: 100vh; /* 전체 높이 */
  overflow: hidden; /* 스크롤 숨김 */
  background-color: #f1f4f9;
}

.main-content {
  flex              : 1;
  display           : flex;
  flex-direction    : column;
  overflow-y        : auto; /* 메인 콘텐츠 영역 스크롤 */
}

.content-inner
{
  word-wrap       : break-word;
  overflow-wrap   : break-word;
  word-break      : break-all;
  padding         : 20px;
}

</style>
