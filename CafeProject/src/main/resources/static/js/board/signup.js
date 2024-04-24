const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const inputEmail = document.querySelector("#inputEmail");
const emailAlert = document.querySelector("#alertMessage1");

const inputPw = document.querySelector("#inputPw");
const pwAlert = document.querySelector("#alertMessage4");

const memberNickname = document.querySelector("#memberNickname");
const nickAlert = document.querySelector("#alertMessage3");

const inputTel = document.querySelector("#inputTel");
const telAlert = document.querySelector("#alertMessage7");


//이메일 유효성 검사
inputEmail.addEventListener("input", () => {
    if(!regExp.test(inputEmail.value)){
        emailAlert.innerText = "이메일 형식이 맞지 않습니다";   
        emailAlert.classList.add("fail");
        emailAlert.classList.remove("success");
        return;  
    }
 
    emailAlert.innerText = "유효한 이메일 형식입니다";   
    emailAlert.classList.add("success");
    emailAlert.classList.remove("fail");

    const otpBtn = document.createElement("button");
    otpBtn.textContent = "인증요청 보내기";
    otpBtn.classList.add("btn");
    emailAlert.append(otpBtn);
});


//비밀번호 유효성 검사
inputPw.addEventListener("input",()=>{
    
    if(inputPw.value.trim().length === 0){
        pwAlert.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
        pwAlert.classList.remove("success", "fail"); // 검은 글씨
        inputPw.value = ""; //처음 뛰어쓰기 입력 못하게 하기
        return;
    }
    

    const regexp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
    if(!regexp.test(inputPw.value)){
        pwAlert.innerText = "비밀번호 형식이 올바르지 않습니다";
        pwAlert.classList.add("fail");
        emailAlert.classList.remove("success");
        return;
    }

    pwAlert.innerText = "유효한 비밀번호 형식 입니다";
    pwAlert.classList.add("success");
    pwAlert.classList.remove("fail");
});


//닉네임 유효성 검사
memberNickname.addEventListener("input", () => {

    if(memberNickname.value.trim().length === 0){
        nickAlert.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
        nickAlert.classList.remove("success", "fail");
        memberNickname.value = "";
        return;
    }

    const regExp = /^[a-zA-Z0-9!@#_-]{2,10}$/;
    if(!regExp.test(memberNickname.value)){
        nickAlert.innerText = "닉네임 형식이 올바르지 않습니다";
        nickAlert.classList.add("fail");
        nickAlert.classList.remove("success");
        return;
    }
    nickAlert.innerText = "유효한 닉네임 형식입니다";
    nickAlert.classList.add("success");
    nickAlert.classList.remove("fail");
});


//전화번호 유효성 검사
inputTel.addEventListener("input", () => {
    if(inputTel.value.trim().length === 0){
        telAlert.innerText = "숫자 0-9 사이를 입력해 주세요";
        telAlert.classList.remove("success", "fail");
        inputTel.value = "";
        return;
    }

    const regexp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
    if(!regexp.test(inputTel.value)){
        telAlert.innerText = "올바른 전화번호 형식이 아닙니다";
        telAlert.classList.add("fail");
        telAlert.classList.remove("success");
        return;
    }
    telAlert.innerText = "유효한 전화번호 형식입니다";
    telAlert.classList.add("success");
    telAlert.classList.remove("fail");

});


    
