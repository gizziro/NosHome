<template>
  <div class="w-full px-4 py-1 lg:py-2 border border-gray-200 bg-white mb-1 lg:mb-3 flex items-center">
    <div class="flex flex-col">
      <span class="mr-4 text-gray-700  ">{{siteMapItem.title}} ({{siteMapItem.sitemapSeq}})</span>
      <span>{{siteMapItem.description}}</span>
    </div>

    <div class="flex ml-auto gap-1 lg:gap-3">
      <button class=" bg-blue-500 px-2 py-1 rounded-lg text-white" @click="$emit('onSelectSitemap', siteMapItem.sitemapSeq)">
        <i class="bi bi-wrench"></i>
        <span>수정</span>
      </button>
      <button class=" bg-red-500 px-2 py-1 rounded-lg text-white">
        <i class="bi bi-wrench"></i>
        <span>삭제</span>
      </button>
    </div>
  </div>
</template>
<script setup>

const props = defineProps({
  siteMapItem : Object
});



</script>

<style scoped>

</style>