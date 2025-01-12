import NetworkUtil from "@/util/networkUtils.js";

const API_BASE_URL              = '/api/v1/admin/auth';
const API_URL_GET_MY_PRIVILEGE  = `${API_BASE_URL}/myPrivilege`;    // 내 정보 요청

//----------------------------------------------------------------------------------------------------------------------
// 사용자 정보 요청 요청
//----------------------------------------------------------------------------------------------------------------------
const fetchPlatformCodeList     = (onError) =>
  NetworkUtil.getData(API_URL_GET_MY_PRIVILEGE, onError);


const AuthHandler = {
  fetchPlatformCodeList,  /// 사용자 정보 요청
};

export default AuthHandler;