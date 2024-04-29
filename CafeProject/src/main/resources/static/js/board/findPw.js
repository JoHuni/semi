const inputPass = document.querySelector("#inputPass");

const registerBtn = document.querySelector("#registerBtn");
const alertMessage = document.querySelector("#alertMessage");

const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


const obj= {
    "memberEmail":false
}

inputPass.addEventListener("input",() => {
    if(!regExp.test(inputPass.value)){
        alertMessage.innerText = "등록된이메일이 아닙니다"
        alertMessage.classList.add("fail");
        alertMessage.classList.remove("success");
        obj.memberEmail =false;
        return;
    }
    alertMessage.innerText = "등록된 이메일 입니다"
    alertMessage.classList.add("success");
    alertMessage.classList.remove("fail");
    obj.memberEmail =true;

})
