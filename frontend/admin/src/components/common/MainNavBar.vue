<template>
  <div class="navbar shadow">
    <button type="button" class="block lg:hidden" @click="showSideBar">
      <PhList :size="20" />
    </button>
    <ul class="navbar hstack ml-auto">
      <li><PhBell :size="20" /></li>
      <li><span>관리자</span></li>
      <li><span>{{isMobileSidebarVisible}}</span></li>
    </ul>
  </div>
</template>

<script setup>
import { storeToRefs } from "pinia";
import {useMenuStore} from "@/stores/menuStore.js";
import {PhList, PhBell} from "@phosphor-icons/vue";

const menuStore = useMenuStore();
const { isMobileSidebarVisible } = storeToRefs(menuStore);


const showSideBar = () => {
  isMobileSidebarVisible.value = true;
}

</script>

<style scoped>
.navbar{
  display             : flex;
  padding             : 10px;
  justify-content     : space-between;
}
.shadow {
  box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.125);
}
</style>