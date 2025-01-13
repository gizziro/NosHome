import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import SitemapService from "@/service/sitemapService.js";

export const useSitemapStore = defineStore('sitemap', () =>
{
  //--------------------------------------------------------------------------------------------------------------------
  // 사이드바 메뉴 관련
  //--------------------------------------------------------------------------------------------------------------------
  const sitemapList = ref([]);
  //--------------------------------------------------------------------------------------------------------------------

  const fetchAllSiteMap = async () => {
    const {result, data} = await SitemapService.fetchAllSiteMap();

    if (data)
    {
      sitemapList.value = data;
    }
  }

  //--------------------------------------------------------------------------------------------------------------------
  // 메뉴 등록
  //--------------------------------------------------------------------------------------------------------------------
  const registerMenu = async (sitemapSeq, moduleId, title, url, target, description) => {
    const {result, data} = await SitemapService.registerMenu(sitemapSeq, {sitemapSeq, moduleId, title, url, target, description});
    if (data)
    {

    }
  }

  const fetchSiteMapMenu = async (sitemapSeq) => {
    const {result, data} = await SitemapService.fetchSiteMapMenu(sitemapSeq);
    console.log("hihi")
    console.log(data);
    if (data)
    {

    }
    return data;
  }



  return { sitemapList, fetchAllSiteMap, registerMenu, fetchSiteMapMenu}
})
