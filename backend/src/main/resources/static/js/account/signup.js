
document.addEventListener('DOMContentLoaded', function() {
  let isPhoneVerified = false;
  let timerInterval;
  // CSRF 토큰 읽기
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
  // Axios 기본 설정: withCredentials 활성화
  axios.defaults.withCredentials = true;
  axios.defaults.headers.common[csrfHeader] = csrfToken;

  const sendOtpBtn = document.getElementById('sendOtpBtn')


  // 폼 검증 함수
  function validateForm() {
    // 필수 필드 ID 목록
    const requiredFields = {
      userId: "아이디를 입력해주세요.",
      name: "이름을 입력해주세요.",
      email: "이메일을 입력해주세요.",
      password: "비밀번호를 입력해주세요.",
      'password-confirm': "비밀번호 확인을 입력해주세요.",
    };

    // 이메일 정규식 패턴
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // 휴대폰 번호 정규식 패턴 (010-1234-5678 또는 01012345678 형식)
    const phonePattern = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
    // 비밀번호 정규식 (최소 8자, 영문, 숫자, 특수문자 포함)
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

    // 모든 에러 메시지 초기화
    clearErrorMessages();

    let isValid = true;

    // 필수 필드 검증
    for (const [fieldId, message] of Object.entries(requiredFields)) {
      const field = document.getElementById(fieldId);
      if (!field.value.trim()) {
        showError(fieldId, message);
        isValid = false;
      }
    }

    // 이메일 형식 검증
    const emailField = document.getElementById('email');
    if (emailField.value && !emailPattern.test(emailField.value)) {
      showError('email', '올바른 이메일 형식이 아닙니다.');
      isValid = false;
    }

    // 휴대폰 번호 형식 검증
    const phoneField = document.getElementById('phone');
    if (phoneField.value && !phonePattern.test(phoneField.value)) {
      showError('phone', '올바른 휴대폰 번호 형식이 아닙니다.');
      isValid = false;
    }

    // 비밀번호 형식 검증
    const passwordField = document.getElementById('password');
    if (passwordField.value && !passwordPattern.test(passwordField.value)) {
      showError('password', '비밀번호는 최소 8자 이상이며, 영문, 숫자, 특수문자를 포함해야 합니다.');
      isValid = false;
    }

    // 비밀번호 확인 일치 검증
    const passwordConfirmField = document.getElementById('password-confirm');
    if (passwordField.value !== passwordConfirmField.value) {
      showError('password-confirm', '비밀번호가 일치하지 않습니다.');
      isValid = false;
    }

    return isValid;
  }

// 에러 메시지 표시 함수
  function showError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const errorDiv = document.createElement('div');
    errorDiv.className = 'text-red-500 text-sm mt-1 error-message';
    errorDiv.textContent = message;

    // 기존 에러 메시지 제거
    const existingError = field.parentNode.querySelector('.error-message');
    if (existingError) {
      existingError.remove();
    }

    field.parentNode.appendChild(errorDiv);
    field.classList.add('border-red-500');
  }

// 에러 메시지 초기화 함수
  function clearErrorMessages() {
    document.querySelectorAll('.error-message').forEach(error => error.remove());
    document.querySelectorAll('input').forEach(input => {
      input.classList.remove('border-red-500');
    });
  }

// 폼 제출 이벤트 리스너
  document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault(); // 기본 제출 동작 중지

    if (validateForm()) {
      // 모든 검증을 통과하면 폼 제출
      this.submit();
    }
  });


  function validatePasswords() {
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('password_confirm').value;
    const errorMessage = document.getElementById('error-message');

    if (password !== passwordConfirm) {
      errorMessage.textContent = '비밀번호가 일치하지 않습니다.';
      return false;
    } else {
      errorMessage.textContent = '';
      return true;
    }
  }



  function handleError(error, customMessage = "요청 처리 중 오류가 발생했습니다.") {
    console.error("Error details:", error);

    const status = error.response?.status;
    const errorMessage = error.response?.data?.message || customMessage;

    // 상태 코드에 따른 처리
    switch (status) {
      case 400:
        alert("잘못된 요청입니다. 입력 정보를 확인하세요.");
        break;
      case 401:
        alert("인증이 필요합니다. 다시 로그인하세요.");
        break;
      case 500:
        alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        break;
      default:
        alert(errorMessage);
        break;
    }

    // 필요하면 추가 처리 (예: 에러 로그 전송)
  }

  async function postRequest(url, data, headers = {}, withCredentials = false) {
    try {
      const response = await axios.post(url, data, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          ...headers,
        },
        withCredentials,
      });

      // 상태 코드 검증 (200~299 범위만 성공으로 간주)
      if (response.status >= 200 && response.status < 300) {
        return response.data; // 성공 시 데이터 반환
      } else {
        throw new Error(response.data?.message || "요청 처리 중 오류가 발생했습니다.");
      }
    } catch (error) {
      console.error(`Error in POST request to ${url}:`, error);

      // 에러 메시지를 호출자에게 전달
      throw error.response?.data?.message || error.message || 'API 요청 중 오류 발생';
    }
  }

// 기존 sendOtpBtn 이벤트 리스너 수정
  sendOtpBtn.addEventListener('click', async () => {
    const phoneNumber = document.getElementById('phone').value;

    if (!phoneNumber || !/^\d{10,11}$/.test(phoneNumber)) {
      alert("유효한 전화번호를 입력하세요 (10~11자리 숫자)");
      return;
    }

    const formData = new FormData();
    formData.append('phone', phoneNumber);

    try {
      const responseData = await postRequest('/sendOtp', formData, {}, true);
      if (responseData.result && responseData.result.code === "0000") {
        document.getElementById('otpInputSection').style.display = 'block';
        startTimer(180); // 3분 타이머 시작
        sendOtpBtn.disabled = true;
      }
    } catch (error) {
      handleError(error, "OTP 전송 중 오류가 발생했습니다.");
    }
  });


  document.getElementById('checkIdBtn').addEventListener('click', async () => {
    const userId = document.getElementById('userId').value;

    // 정규식 검사: 영문, 숫자, 언더스코어, 대시(-)만 허용
    const idRegex = /^[a-zA-Z0-9_-]*$/;

    if (!userId) {
      alert("아이디를 입력하세요.");
      return;
    }

    if (!idRegex.test(userId)) {
      alert("아이디는 영문, 숫자, 언더스코어(_), 하이픈(-)만 사용할 수 있습니다.");
      return;
    }

    if (userId.length < 5 || userId.length > 20) {
      alert("아이디는 5~20자로 입력해야 합니다.");
      return;
    }

    // FormData 객체 생성
    const formData = new FormData();
    formData.append('userId', userId);

    try {
      // 공통 POST 요청 함수 사용
      const responseData = await postRequest('/checkId', formData, {}, true);

      // 성공 처리
      if (responseData.result && responseData.result.code === "0000") {
        alert(responseData.body || '사용 가능한 아이디입니다.');
      } else {
        const errorMessage = responseData.result?.message || '이미 사용 중인 아이디입니다.';
        alert(errorMessage);
      }
    } catch (error) {
      // 공통 에러 처리 함수 호출
      handleError(error, "ID 중복 체크 중 오류가 발생했습니다.");
    }
  });


//----------------------------------------------------------------------------------------------------------------
// 전화번호 입력 후 OTP 요청
//----------------------------------------------------------------------------------------------------------------
  document.getElementById('verifyOtpBtn').addEventListener('click', async () => {
    const otpNumber = document.getElementById('otpNumber').value;

    if (!otpNumber) {
      alert("인증번호를 입력하세요.");
      return;
    }

    if (otpNumber.length !== 6) {
      alert("6자리의 인증번호를 입력하세요.");
      return;
    }

    const formData = new FormData();
    formData.append('otp', otpNumber);

    try {
      const responseData = await postRequest('/verifyOtp', formData, {}, true);
      if (responseData.result && responseData.result.code === "0000") {
        isPhoneVerified = true;
        clearInterval(timerInterval);
        document.getElementById('otpTimer').textContent = '인증이 완료되었습니다.';
        document.getElementById('verifyOtpBtn').disabled = true;
        document.getElementById('sendOtpBtn').disabled = true;
        document.getElementById('phone').readOnly = true;
        alert('인증이 완료되었습니다.');
      } else {
        const errorMessage = responseData.result?.message || '올바르지 않은 인증번호 입니다.';
        alert(errorMessage);
      }
    } catch (error) {
      handleError(error, "인증번호 확인 시 오류가 발생하였습니다.");
    }
  });

  // 타이머 함수 추가
  function startTimer(duration) {
    let timer = duration;
    const timerDisplay = document.getElementById('otpTimer');

    clearInterval(timerInterval);

    timerInterval = setInterval(function() {
      const minutes = Math.floor(timer / 60);
      const seconds = timer % 60;

      timerDisplay.textContent = `${minutes}:${seconds < 10 ? '0' + seconds : seconds}`;

      if (--timer < 0) {
        clearInterval(timerInterval);
        timerDisplay.textContent = '시간이 만료되었습니다. 다시 시도해주세요.';
        document.getElementById('verifyOtpBtn').disabled = true;
      }
    }, 1000);
  }



});