const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const inputEmail = document.querySelector("#memberEmail");
const emailAlert = document.querySelector("#alertMessage1");



const checkPass = document.querySelector("#checkPass");
const checkPassAlert =  document.querySelector("#alertMessage6");

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


const registerBtn= document.querySelector("#registerBtn");

const signUpForm = document.querySelector("#signUpForm");

const signUpBtn = document.querySelector("signUpBtn");


let min;
let sec;

let clickCount = 0;




const obj = {
    "memberEmail" : false,
    "checkEmail"  : false,
    "memberPw" : false,
    "checkPass" : false,
    "memberNickname" : false, 
    "memberTel" : false
};

//----------------------------------------이메일 유효성 검사------------------------------------------------------

//이메일 유효성 검사
inputEmail.addEventListener("input", () => {
    if(!regExp.test(inputEmail.value)){
        emailAlert.innerText = "이메일 형식이 맞지 않습니다";   
        emailAlert.classList.add("fail");
        emailAlert.classList.remove("success");
        obj.memberEmail = false;
       
        return;  
    }
    
 
    

    const memberEmailVal = inputEmail.value
   

    fetch("/member/checkEmailRedundancy",{
        method  : "POST",
        headers : {"Content-Type":"application/json"},
        body    : memberEmailVal
    })
    .then(resp => resp.text())
    .then(result => {

        
        if(result == 0){
            emailAlert.innerText = "사용가능한 이메일 입니다";
            emailAlert.classList.add("success");
            emailAlert.classList.remove("fail");
            obj.memberEmail = true;

            emailAlert.innerText = "유효한 이메일 형식입니다";   
            emailAlert.classList.add("success");
            emailAlert.classList.remove("fail");
            obj.memberEmail = true;

            otpBtn.textContent = "인증요청 보내기";
            otpBtn.classList.add("btn");
            otpBtn.setAttribute("type","button")
            emailAlert.append(otpBtn);
            return;
           
        }

        emailAlert.innerText = "이미 존재하는 이메일 입니다";
        emailAlert.classList.add("fail");
        emailAlert.classList.remove("success");
        obj.memberEmail = false;

    })
});



//----------------------------------------------------------이메일 인증 기능 구현-------------------------------------

//이메일 인증 기능 구현
checkEmail.addEventListener("input", () => {
    if(checkEmail.value.trim().length === 0){
        authKeyMessage.innerText = "인증키 입력을 해주세요";
        authKeyMessage.classList.add("red");
        obj.checkEmail = false;
        checkEmail.value = "";
        return;
    }
    obj.checkEmail = true;
});

//----------------------------------------------------------비밀번호 유효성 검사-----------------------------------------
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
       
        return;
    }
    
    pwAlert.innerText = "유효한 비밀번호 형식 입니다";
    pwAlert.classList.add("success");
    pwAlert.classList.remove("fail");
    obj.memberPw = true;

});

//-----------------------------------------------------------------비밀번호 인증 기능 구현-----------------------------------
//비밀번호 인증 기능 구현
checkPass.addEventListener("input", () => {
    if(checkPass.value.trim().length === 0){
        checkPassAlert.innerText = "비밀번호를 재입력 해야합니다";
        checkPass.value = "";
        return;
    }
    if(checkPass.value != inputPw.value){
        checkPassAlert.innerText = "비밀번호가 일치하지 않습니다";
        checkPassAlert.classList.add("fail");
        checkPassAlert.classList.remove("success");
        obj.checkPass = false;
        return;
    }
    checkPassAlert.innerText = "일치하는 비밀번호 입니다";
    checkPassAlert.classList.add("success");
    checkPassAlert.classList.remove("fail");
    obj.checkPass = true;
});

//--------------------------------------------------닉네임 유효성 검사---------------------------------------------
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
       
        return;
    }

    fetch("/member/checkNicknameRedundancy",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberNickname.value
    })
    .then(resp => resp.text())
    .then(result => {
        if(result != 0){
            nickAlert.innerText = "이미 존재하는 닉네임 입니다"
            nickAlert.classList.add("fail");
            nickAlert.classList.remove("sucess");
            obj.memberNickname = false;
            return;
        }
        nickAlert.innerText = "유효한 닉네임 형식입니다";
        nickAlert.classList.add("success");
        nickAlert.classList.remove("fail");
        obj.memberNickname = true;
    });
    
});

//--------------------------------------------------------------전화번호 유효성 검사------------------------------------------
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
        return;
    }
    telAlert.innerText = "유효한 전화번호 형식입니다";
    telAlert.classList.add("success");
    telAlert.classList.remove("fail");
    obj.memberTel = true;
});



signUpForm.addEventListener("submit", () => {
   
    for(let key in obj){
     
        if(!obj[key]){
            let str;
            switch(key){
                case "memberEmail": str = "이메일을 입력해 주세요";return;
                case "checkEmail" : str = "이메일이 인증이 되지 않았습니다";return;
                case "memberPw": str = "비밀번호를  입력해 주세요";return;
                case "checkPass": str = "비밀번호가 인증이 되지 않았습니다";return;
                case "memberNickname": str = "닉네임을 입력해 주세요";return;
                case "memberTel": str = "전화번호를 입력해 주세요";return;
            }
            alert(str);
            document.getElementById(key).focus();
            document.getElementById(key).style.border = "1px solid red";
            
            
        }

        document.getElementById(key).style.border = "1px solid green";
        
        
    }
})





//-------------------------------------------------------이메일 인증 번호 발송------------------------------------------------------
//이메일 인증
let authTimer; // 타이머 역할을 할 setInterval을 저장할 변수

const initMin = 4;    //타이머 초기 값(분)
const initSec = 59;   //타이머 초기 값(초)
const initTime = "05:00"; 

// 실제 줄어드는 시간을 저장할 변수
min = initMin;
sec = initSec;

//인증 번호 받기 버튼 클릭 시
otpBtn.addEventListener("click" , () => {

    checkEmail.style.display = "block";
    checkBtn.style.display = "block";

 
    document.querySelector("#authKeyMessage").innerText = "";

    //중복되지 않은 유효한 이메일을 입력한 경우가 아니면
    if(!obj.memberEmail){
        alert("유효한 이메일 작성후 클릭해 주세요");
        return;
    }

    //클릭 시 타이머 숫자 초기화
     min = initMin;
    sec = initSec;

    //이전 동작중인 인터벌 클리어
    clearInterval(authTimer);

    obj.checkEmail = false; 

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
    alert("인증 번호가 발송되었습니다.")
    obj.checkEmail = true;
    authKeyMessage.innerText = initTime; // 05:00 세팅
    authKeyMessage.classList.remove("success", "fail"); // 검정 글씨

    

    
    authTimer = setInterval(() =>{
        authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

       
        if(min == 0 && sec == 0){
            obj.checkEmail = false; // 인증 못함
            clearInterval(authTimer); //interval 멈춤
            authKeyMessage.classList.add("fail");
            authKeyMessage.classList.remove("success");
            obj.checkEmail = false;
            return;
        }
       
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

checkEmail.addEventListener("input",() => {
    if(checkEmail.value.trim().length === 0){
        authKeyMessage.innerText = "인증번호를 입력해주세요"
        authKeyMessage.classList.add("fail");
        authKeyMessage.classList.remove("success");
        checkEmail.value = "";
        return;
    }
});

checkAuthKeyBtn.addEventListener("click",e => {
    clickCount++;
    if(min === 0 && sec === 0){
        alert("시간을 초과하였습니다");
        return;
    }
    if(checkEmail.value.length < 6){
        alert("인증번호를 정확히 입력해주세요");
    }

    const obj = {
        "email":inputEmail.value,
        "authKey": checkEmail.value
    }

    fetch("/email/checkAuthKey",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(response => response.text())
    .then(result => {
        
        if(result == 0){
            alert("인증번호가 일치하지 않습니다");
            obj.checkEmail=false;
            return;
        }

        clearInterval(authTimer);

        authKeyMessage.innerText = "인증 되었습니다";
        authKeyMessage.classList.add("success");
        authKeyMessage.classList.remove("fail");
        obj.checkEmail = true;
        

       if(clickCount > 1 ){
        alert("이미 입력한 인증 번호 이거나 인증 번호가 올바르지 않습니다") ;   
        clickCount = 0;  
        return; 
           
       
       }
       console.log(result);
        
    })
});

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
            e.preventDefault();
            return; 
        }
        document.getElementById(key).style.border = "1px solid green";
    }
});


