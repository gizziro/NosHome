<template>
  <li class="nav-item nav-item-submenu">


    <RouterLink :to="props.url" class="nav-link " :class="nav_link_css">
      <PhLayout :size="20" />
      <span v-show="isSidebarExpanded || isSidebarHovered">
        {{props.title}}
      </span>
    </RouterLink>

  </li>
</template>

<script setup>
import {computed} from "vue";
import { storeToRefs } from "pinia";
import {useLayoutStore} from "@/stores/layoutStore.js";
import {PhLayout} from "@phosphor-icons/vue";


const layoutStore = useLayoutStore();
const { isSidebarExpanded, isSidebarHovered } = storeToRefs(layoutStore);

const props = defineProps({
  title: String,
  url: String,
});


const nav_link_css = computed(() => {
  return {
    "nav-link-expanded": isSidebarExpanded.value || isSidebarHovered.value,
    'nav-link-folded': !(isSidebarExpanded.value || isSidebarHovered.value),
  };
});
</script>

<style scoped>
.nav-item
{
  padding : 10px 20px;
  width: 100%;
  display: list-item;
  box-sizing: border-box;
}

.nav-item:hover
{
  background-color: #2b3348;
}

.nav-link
{
  display             : flex;
  position            : relative;
  align-items         : center;
  gap                 : 10px;
  color               : #fff;
  text-decoration     : none;
}

.nav-link-expanded
{
  justify-content: flex-start;
}

.nav-link-folded
{
  justify-content: center;
}

</style>