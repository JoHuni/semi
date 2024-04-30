let authTimer;
const inputPass = document.querySelector("#inputPass");

const registerBtn = document.querySelector("#registerBtn");
const alertMessage = document.querySelector("#alertMessage");

const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

const sendAuthKeyBtn = document.createElement("button");
sendAuthKeyBtn.classList.add("btn");
sendAuthKeyBtn.setAttribute("type", "button")
sendAuthKeyBtn.innerText = "인증번호 발송하기"

const btn = document.querySelector(".btn");

const checkAuthField = document.querySelector("#checkEmail");
const checkBtn = document.querySelector("#checkBtn");

const findPwdForm = document.querySelector("#findPwdForm");

let min;
let sec;




const obj= {
    "memberEmail":false,
    "checkAuthField": false
}

inputPass.addEventListener("input",() => {

    authKeyAlert.innerText = "";
    if(inputPass.value.trim().length === 0){
        
        inputPass.value = "";
        alertMessage.classList.add("fail");
        alertMessage.classList.remove("success");
        alertMessage.innerText = "이메일을 입력하셔야 인증번호가 발송이 됩니다!!"
        obj.memberEmail = false;
        return;
      
    }
    if(!regExp.test(inputPass.value)){
     
        alertMessage.innerText = "올바른 이메일 형식으로 입력해주세요"
        alertMessage.classList.add("fail");
        alertMessage.classList.remove("success");
        obj.memberEmail =false;
        return;
    }
    

    fetch("/member/checkEmailRedundancy",{
        method : "POST",
        headers : {"Content-type":"application/json"},
        body : inputPass.value
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 1){
            alertMessage.innerText = "등록된 이메일 입니다"
            alertMessage.classList.add("success");
            alertMessage.classList.remove("fail");
            obj.memberEmail =true;
            alertMessage.append(sendAuthKeyBtn);
            return;
        }
        alertMessage.innerText = "등록되지 않은 이메일 입니다"
            alertMessage.classList.add("fail");
            alertMessage.classList.remove("success");
            obj.memberEmail =false;
    })
})




sendAuthKeyBtn.addEventListener("click",() => {
    checkAuthField.style.display = "block";
    checkBtn.style.display = "block";
    fetch("/email/signup",{
        method : "POST",
        headers: {"Content-type":"application/json"},
        body: inputPass.value
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 1){
            alert("인증번호가 발송이 되었습니다")
        }else{
            alert("인증번호 발송 실패")
        }
    })


    //-------------------------------------------- 타이머----------------------------------------
    let initTime = "05:00";
    authKeyAlert.innerText = initTime;
    min =4;
    sec =59;
    authTimer = setInterval(() => {
       
        --sec;
        authKeyAlert.innerText = `${addZero(min)}:${addZero(sec)}`;
        if(min==0 && sec == 0){
            authKeyAlert.classList.add("fail");
            alert("시간이 초과되었습니다")
            clearInterval(authTimer);
            return;
        }
        if(sec == 0){
            sec=60;
            --min;
        }
    },100);
})
function addZero(number){
    if (number < 10)  return '0' + number;
    else               return number;
}


checkAuthField.addEventListener("input", () => {
    if(checkAuthField.value.trim().length === 0 ){
        authKeyAlert.classList.add("fail");
        authKeyAlert.classList.remove("remove");
        authKeyAlert.innerText = "인증번호를 입력하세요";
        checkAuthField.value = "";
        obj.checkAuthField=false;
        return;
    }

    obj.checkAuthField = true;
})



checkBtn.addEventListener("click", () => {
    if(min === 0 && sec === 0){
        alert("시간이 초과되었습니다");
        return;
    }
    const memberObj = {
        "email" : inputPass.value,
        "authKey" : checkAuthField.value
    }
    fetch("/email/checkAuthKey",{
        method : "POST",
        headers : {"Content-type":"application/json"},
        body : JSON.stringify(memberObj)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 0){
            alert("인증번호가 일치하지 않습니다");
            obj.checkEmail = false;
            return;
        }

        clearInterval(authTimer);
        alert("인증 되었습니다")
        obj.checkEmail = true;
    })
});

findPwdForm.addEventListener("submit", e => {
    for(key in obj){
        if(!obj[key]){
            let str;
            switch(key){
                case "memberEmail": str = "이메일을 입력해 주세요"; break;
                case "checkEmail": str = "인증번호를 입력해 주세요"; break;

            }
            alert(str);
            e.preventDefault();
            return;
        }

    }

});







