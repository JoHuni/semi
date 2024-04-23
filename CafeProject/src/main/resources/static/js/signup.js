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
    fetch("/checkEmail?inputEmail=" + inputEmail.value)
    .then(response => response.text())
    .then(count => {
        if (count == 1){
            emailAlert.innerText = "이미 존재하는 이메일 입니다";
            emailAlert.classList.add("fail");
            emailAlert.classList.remove("success");
            return;
        }
    })
    
});
    
