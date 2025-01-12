import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import CommonService from '@/service/commonService.js'

export const useCommonStore = defineStore('common', () =>
{
  //--------------------------------------------------------------------------------------------------------------------
  //
  //--------------------------------------------------------------------------------------------------------------------
  const serviceModuleList = ref([]);

  const fetchServiceModules = async () => {
    const {result, data} = await CommonService.fetchServiceModuleList();
    if (data)
    {
      console.log("서비스 모듈 조회 성공")
      serviceModuleList.value = data;
    }
    else
    {

    }
  };

  fetchServiceModules();

  return {
    serviceModuleList,
    fetchServiceModules,
  }
})
