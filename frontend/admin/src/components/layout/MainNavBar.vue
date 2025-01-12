<template>
  <div class="navbar shadow">
    <button type="button" class="block lg:hidden text-gray-500" @click="showSideBar">
      <PhList :size="20" />
    </button>
    <ul class="navbar hstack ml-auto">
      <li><PhBell :size="20" /></li>
      <li><span>{{ loginUser.userName }} ({{loginUser.userId}})</span></li>
    </ul>
  </div>
</template>

<script setup>
import { storeToRefs } from "pinia";
//----------------------------------------------------------------------------------------------------------------------
// 상태 관리 스토어
//----------------------------------------------------------------------------------------------------------------------
import {useAuthStore} from "@/stores/authStore.js";   // 사용자 권한 관련 스토어
import {useLayoutStore} from "@/stores/layoutStore.js";   // 메뉴 관련 스토어

import {PhList, PhBell} from "@phosphor-icons/vue";

//----------------------------------------------------------------------------------------------------------------------
// 스토어 사용
//----------------------------------------------------------------------------------------------------------------------
const authStore = useAuthStore();
const layoutStore = useLayoutStore();

const { loginUser } = storeToRefs(authStore);
const { isMobileSidebarVisible, isSidebarHovered } = storeToRefs(layoutStore);


const showSideBar = () => {
  isMobileSidebarVisible.value = true;
}

</script>

<style scoped>
.navbar{
  display             : flex;
  padding             : 10px;
  justify-content     : space-between;
  background-color    : white;
}
.shadow {
  box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.125);
}
</style>