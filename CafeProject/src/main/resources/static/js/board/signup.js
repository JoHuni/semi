const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const inputEmail = document.querySelector("#memberEmail");
const emailAlert = document.querySelector("#alertMessage1");



const checkPass = document.querySelector("#checkPass");

const inputPw = document.querySelector("#memberPw");
const pwAlert = document.querySelector("#alertMessage4");

const memberNickname = document.querySelector("#memberNickname");
const nickAlert = document.querySelector("#alertMessage3");

const inputTel = document.querySelector("#memberTel");
const telAlert = document.querySelector("#alertMessage7");

const otpBtn = document.createElement("button"); // 이메일 인증번호 받기 버튼
const checkEmail =  document.querySelector("#checkEmail"); // 이메일 인증번호 입력 창 
const checkAuthKeyBtn = document.querySelector("#checkBtn");
const authKeyMessage =document.querySelector("#authKeyMessage");




const obj = {
    "memberEmail" : false,
    "checkEmail"  : false,
    "memberPassword" : false,
    "checkPass" : false,
    "memberNickname" : false, 
    "memberTel" : false
};

const alertss = {"alertMessage1":false, "alertMessage3":false, "alertMessage4":false, "alertMessage7":false};

//이메일 유효성 검사
inputEmail.addEventListener("input", () => {
    if(!regExp.test(inputEmail.value)){
        emailAlert.innerText = "이메일 형식이 맞지 않습니다";   
        emailAlert.classList.add("fail");
        emailAlert.classList.remove("success");
        obj.memberEmail = false;
        alertss.alertMessage1=false;
        return;  
    }
    emailAlert.innerText = "유효한 이메일 형식입니다";   
    emailAlert.classList.add("success");
    emailAlert.classList.remove("fail");
    obj.memberEmail = true;
    alertss.alertMessage1=true;

    

    otpBtn.textContent = "인증요청 보내기";
    otpBtn.classList.add("btn");
    emailAlert.append(otpBtn);

    

    
});

otpBtn.addEventListener("click", () => {
    checkEmail.style.display = "block";
    checkBtn.style.display = "block";
})

//이메일 인증 기능 구현
checkEmail.addEventListener("input", () => {
    if(checkEmail.value.trim().length === 0){
        obj.checkEmail = false;
        return;
    }
    obj.checkEmail = true;
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
        obj.memberPw = false;
        alertss.alertMessage4=false;
        return;
    }

    pwAlert.innerText = "유효한 비밀번호 형식 입니다";
    pwAlert.classList.add("success");
    pwAlert.classList.remove("fail");
    obj.memberPw = true;
    alertss.alertMessage4=true;
});

//비밀번호 인증 기능 구현
checkPass.addEventListener("input", () => {
    if(checkPass.value.trim().length === 0){
        obj.checkPass = false;
    }

    obj.checkPass = true;
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
        obj.memberNickname = false;
        alertss.alertMessage3=false;
        return;
    }
    nickAlert.innerText = "유효한 닉네임 형식입니다";
    nickAlert.classList.add("success");
    nickAlert.classList.remove("fail");
    obj.memberNickname = true;
    alertss.alertMessage3=false;
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
        obj.memberTel = false;
        alertss.alertMessage7=false;
        return;
    }
    telAlert.innerText = "유효한 전화번호 형식입니다";
    telAlert.classList.add("success");
    telAlert.classList.remove("fail");
    obj.memberTel = true;
    alertss.alertMessage7=true;
});

const signUpForm = document.querySelector("#signUpForm");

signUpForm.addEventListener("submit", e => {
   
    for(let key in obj){
     
        if(!obj[key]){
            let str;
            switch(key){
                case "memberEmail": str = "이메일을 입력해 주세요";break;
                case "checkEmail" : str = "이메일이 인증이 되지 않았습니다";break
                case "memberPw": str = "비밀번호를  입력해 주세요";break;
                case "checkPass": str = "비밀번호가 인증이 되지 않았습니다";break;
                case "memberNickname": str = "닉네임을 입력해 주세요";break;
                case "memberTel": str = "전화번호를 입력해 주세요";break;
            }
            alert(str);
            document.getElementById(key).focus();
            document.getElementById(key).style.border = "1px solid red";
            for(let message in alertss){
                if(!alertss[message])
                document.getElementById(message).innerText = str;
                document.getElementById(message).style.border = "1px solid red";
            }
            e.preventDefault();
            return;
        }
        
        
    }

});

//이메일 인증
let authTimer; // 타이머 역할을 할 setInterval을 저장할 변수

const initMin = 4;    //타이머 초기 값(분)
const initSec = 59;   //타이머 초기 값(초)
const initTime = "05:00"; 

// 실제 줄어드는 시간을 저장할 변수
let min = initMin;
let sec = initSec;

//인증 번호 받기 버튼 클릭 시
otpBtn.addEventListener("click" , () => {

    obj.checkEmail = false;
    document.querySelector("#authKeyMessage").innerText = "";

    //중복되지 않은 유효한 이메일을 입력한 경우가 아니면
    if(!obj.memberEmail){
        alert("유효한 이메일 작성후 클릭해 주세요");
        return;
    }

    //클릭 시 타이머 숫자 초기화
    let min = initMin;
    let sec = initSec;

    //이전 동작중인 인터벌 클리어
    clearInterval(authTimer);

    obj.checkEmail = false; // 인증 유효성 검사 여부 false

    //*************************************************
    //비동기로 서버에서 메일 보내기

    fetch("/email/signup", {
        method : "POST",
        headers: {"Content-Type" : "application/json"},
        body:  memberEmail.value
    })
    .then(response => response.text())
    .then(result => {
        if(result == 1){
            console.log("인증 번호 발송 성공");
        }else{
            console.log("인증 번호 발송 실패");
        }
    })
    

    //*************************************************

    //메일은 비동기로 서버에서 보내라고 하고
    // 화면에서는 타이머 시작하기

    authKeyMessage.innerText = initTime; // 05:00 세팅
    authKeyMessage.classList.remove("success", "fail"); // 검정 글씨

    alert("인증 번호가 발송되었습니다.")

    // setInterval(함수, 지연시간(ms))
    // - 지연시간 (ms)만큼 시간이 지날 떄 마다 함수 수행
    //clearInterval(Interval 이 저장된 변수)
    //- 매개변수로 전달 받은 interval 을 멈춤

    //인증 시간 출력(1초 마다 동작)
    authTimer = setInterval(() =>{
        authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

        //0분 0초인 경우(00:00초 출력 후)
        if(min == 0 && sec == 0){
            obj.checkEmail = false; // 인증 못함
            clearInterval(authTimer); //interval 멈춤
            authKeyMessage.classList.add("fail");
            authKeyMessage.classList.remove("success");
            return;
        }
        // 0초인 경우(0초를 출력한 후)
        if(sec == 0){
            sec = 60;
            min--;
        }

        sec--; //1초 감소
    }, 100)
});

//전달 받은 숫자가 10미만인 경우(한자리) 앞에 0을 붙여서 반환
function addZero(number){
    if( number < 10 ) return "0" + number;
    else              return number;
}




    
