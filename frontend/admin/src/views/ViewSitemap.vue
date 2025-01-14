.pt-button<template>
  <p>메뉴관리</p>

  <div class="menu-container w-full mb-3 text-gray-800 text-xs">

    <div class="sitemap-box bg-white p-2 lg:w-[400px]">
      <div class="sitemap-btn-box">
        <Button label="사이트맵 추가" class="btn-add-sitemap"
        />
      </div>
      <template v-for="(item) in sitemapList" :key="item.sitemapSeq">
        <SitemapItem :siteMapItem="item" @onSelectSitemap="onSelectSitemapHandler" />
      </template>
    </div>

    <div class="bg-white p-2 flex-1">

      <template v-if="isSitemapSelected===false">
        <div class="flex justify-center items-center h-full">
          <p>사이트맵을 선택해주세요.</p>
        </div>
      </template>

      <template v-else>
        <div class="menu-btn-box">
          <Button label="메뉴 추가" class="btn-add-menu" @click="menuVisible = true"
          />
        </div>

        {{ menuList}}

        <div class="bg-white rounded-lg shadow mt-3">
          <div class="p-3">
            <NestedMenu :menus="menuList"/>
          </div>
        </div>
      </template>


    </div>
  </div>

  <Dialog v-model:visible="menuVisible" modal header="메뉴 추가">
    <MenuRegisterDialog @onCancel="menuVisible = false" @onRegister="onMenuRegister" />
  </Dialog>
</template>

<script setup>
//======================================================================================================================
// << 임포트 >>
//======================================================================================================================
//----------------------------------------------------------------------------------------------------------------------
// Vue Composition API
//----------------------------------------------------------------------------------------------------------------------
import { ref } from "vue";
import { storeToRefs } from "pinia";
import {useSitemapStore} from "@/stores/sitemapStore.js";
//----------------------------------------------------------------------------------------------------------------------
// 커스텀 컴포넌트
//----------------------------------------------------------------------------------------------------------------------
import SitemapItem from "@/components/sitemap/SitemapItem.vue";
import NestedMenu from "@/components/sitemap/NestedMenu.vue";
import MenuRegisterDialog from "@/components/sitemap/MenuRegisterDialog.vue";
//----------------------------------------------------------------------------------------------------------------------
// PrimeVue UI 컨트롤러
//----------------------------------------------------------------------------------------------------------------------
import Button from 'primevue/button';
import Dialog from 'primevue/dialog';

//----------------------------------------------------------------------------------------------------------------------

//======================================================================================================================
//
//======================================================================================================================

//----------------------------------------------------------------------------------------------------------------------
const sitemapStore = useSitemapStore();
sitemapStore.fetchAllSiteMap();
const { sitemapList } = storeToRefs(sitemapStore);


//----------------------------------------------------------------------------------------------------------------------

//======================================================================================================================
// 사이트 맵 등록 다이얼로그 관련
//======================================================================================================================

const isSitemapSelected = ref(false);
const selectedSitemapSeq = ref(0);

//----------------------------------------------------------------------------------------------------------------------
//
//----------------------------------------------------------------------------------------------------------------------
const onSelectSitemapHandler = async (sitemapSeq) => {
  console.log("사이트맵 선택")
  menuList.value = await sitemapStore.fetchSiteMapMenu(sitemapSeq);

  console.log("========================================")
  console.log(menuList.value)

  isSitemapSelected.value   = true;
  selectedSitemapSeq.value  = sitemapSeq;
}


//======================================================================================================================
// 메뉴 추가 관련
//======================================================================================================================
const menuVisible = ref(false);
const menuList = ref([])

const onMenuRegister = async ({name, slug, moduleId, description }) => {
  await sitemapStore.registerMenu(selectedSitemapSeq.value, moduleId, name, slug, "_self", description)


  menuList.value = await sitemapStore.fetchSiteMapMenu(selectedSitemapSeq.value);
  menuVisible.value = false;
}



const menuList2 = ref([
  {
    id: 1,
    name: "대시보드",
    url: "/dashboard",
    isVisible: true,
    menus: []
  },
  {
    id: 2,
    name: "사용자 관리",
    url: "/users",
    isVisible: true,
    menus: [
      {
        id: 3,
        name: "사용자 목록",
        url: "/users/list",
        isVisible: true,
        menus: []
      },
      {
        id: 4,
        name: "권한 관리",
        url: "/users/permissions",
        isVisible: true,
        menus: []
      }
    ]
  },
  {
    id: 5,
    name: "설정",
    url: "/settings",
    isVisible: true,
    menus: [
      {
        id: 6,
        name: "일반 설정",
        url: "/settings/general",
        isVisible: true,
        menus: []
      },
      {
        id: 7,
        name: "보안 설정",
        url: "/settings/security",
        isVisible: false,
        menus: []
      }
    ]
  }
])

const updateMenus = (newMenus) => {
  menuList.value          = newMenus

}

</script>

<style>

.menu-container
{
  display : flex;
  width : 100%;
  flex-wrap: wrap;
  gap     : 5px;
}



.sitemap-btn-box
{
  display           : flex;
  justify-content   : flex-start;
  margin-bottom     : 10px;
}


@media (max-width: 768px)
{
  .menu-container
  {
    flex-direction: column;
  }
}


.btn-add-sitemap.p-button {
  background-color: #a86dff;
  color : #fff;
  font-size : 0.8rem;
  padding: 5px 10px;
  border : 1px solid #57299a;
}
.btn-add-sitemap.p-button:not(:disabled):hover
{
  background-color: #822aff;
  color : #fff;
  padding: 5px;
  border : 1px solid #9ca3af;
}



</style>
