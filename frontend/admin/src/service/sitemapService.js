import NetworkUtil from "@/util/networkUtils.js";

const API_BASE_URL                    = '/api/v1/admin/sitemap';


const API_URL_GET_All_SITEMAP         = `${API_BASE_URL}/all`;    // 전체 사이트맵 요청

const API_URL_POST_REGISTER_SITEMAP   = `${API_BASE_URL}/register`;    // 사이트맥 등록

const API_URL_GET_SITEMAP_MENU              = (sitemapSeq) => `${API_BASE_URL}/${sitemapSeq}`;    // 특정 사이트 맵 데이터 요청
const API_URL_POST_REGISTER_MENU            = (sitemapSeq) => `${API_BASE_URL}/${sitemapSeq}/register`;    // 사이트맥 등록

//----------------------------------------------------------------------------------------------------------------------
// 사이트맵 정보 요청
//----------------------------------------------------------------------------------------------------------------------
const fetchAllSiteMap     = (onError) =>
  NetworkUtil.getData(API_URL_GET_All_SITEMAP, onError);

const fetchSiteMapMenu    = (sitemapSeq, onError) =>
  NetworkUtil.getData(API_URL_GET_SITEMAP_MENU(sitemapSeq), onError);

const registerMenu = (sitemapSeq, params, onError) =>
  NetworkUtil.postData(API_URL_POST_REGISTER_MENU(sitemapSeq), params, onError);



const SitemapService = {
  fetchAllSiteMap,    // 전체 사이트맵 요청
  fetchSiteMapMenu,   // 특정 사이트 맵 데이터 요청
  registerMenu,       // 메뉴 등록
};


export default SitemapService;