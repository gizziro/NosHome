import axios from "axios";

const getData = async (url, onError = (error) => { console.log(error)}) => {
  try
  {
    console.log("url : ", url);
    const response = await axios.get(url);
    // 서버 응답에서 유효한 데이터가 있는지 확인
    if (response.data && response.data.body)
    {
      console.log(response.data.result);
      console.log(response.data.body);
      return {result : response.data.result, data : response.data.body};
    }
    else
    {
      console.warn(`[수정 실패] 데이터 구조가 올바르지 않습니다`);
      return {result : response.data.result};
    }
  } catch (error) {
    console.log('Error fetching data:', error);
    onError(error);
    return null;  // 오류 발생 시 null 반환
  }
};

//----------------------------------------------------------------------------------------------------------------------
// POST 데이터 요청
//----------------------------------------------------------------------------------------------------------------------
const postData = async (url, data, onError = (error) => { console.log(error)}) => {
  try
  {
    const response = await axios.post(url, data);
    // 서버 응답에서 유효한 데이터가 있는지 확인
    if (response.data && response.data.body)
    {
      return {result : response.data.result, data : response.data.body};
    }
    else
    {
      console.warn(`[수정 실패] 데이터 구조가 올바르지 않습니다`);
      return {result : response.data.result};
    }
  } catch (error) {
    console.log('Error fetching data:', error);
    onError(error);
    return null;  // 오류 발생 시 null 반환
  }
};

const NetworkUtil = {
  getData,    // GET 요청
  postData,   // POST 요청
};

export default NetworkUtil;