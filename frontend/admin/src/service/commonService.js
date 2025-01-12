import NetworkUtil from "@/util/networkUtils.js";

const API_BASE_URL              = '/api/v1/admin/common';
const API_URL_GET_MY_PRIVILEGE  = `${API_BASE_URL}/moduleList`;    // 내 정보 요청

//----------------------------------------------------------------------------------------------------------------------
// 사용자 정보 요청 요청
//----------------------------------------------------------------------------------------------------------------------
const fetchServiceModuleList     = (onError) =>
  NetworkUtil.getData(API_URL_GET_MY_PRIVILEGE, onError);

const CommonService = {
  fetchServiceModuleList,  /// 사용자 정보 요청
};

export default CommonService;