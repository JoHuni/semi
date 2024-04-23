const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const inputEmail = document.querySelector("#inputEmail");
const emailAlert = document.querySelector("#alertMessage1");


inputEmail.addEventListener("input", () => {
    if(!regExp.test(inputEmail.value)){
        emailAlert.innerText = "이메일 형식이 맞지 않습니다";   
        emailAlert.classList.add("fail");
        emailAlert.classList.remove("success");
        return;  
    }
    const otpBtn = document.createElement("button");
    otpBtn.classList.add("btn");
    emailAlert.append(otpBtn);
    emailAlert.innerText = "유효한 이메일 형식입니다";   
    emailAlert.classList.add("success");
    emailAlert.classList.remove("fail");
    
    
});
    
