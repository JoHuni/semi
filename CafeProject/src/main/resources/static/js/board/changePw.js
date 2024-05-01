//--------------------------------- 비번 변경

const pwChangeForm = document.querySelector("#pwChangeForm");
const newPassword = document.querySelector("#newPassword");
const inputPass = document.querySelector('input[name="memberPw"]');


let memberPwCheck = false;

newPassword.addEventListener("input", () => {
  const regexp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

  if(regexp.test(newPassword.value)){
    memberPwCheck = true;
  } else {
    memberPwCheck = false;
  }
});

inputPass.addEventListener("input", () => {
    const regexp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
  
    if(regexp.test(newPassword.value)){
      memberPwCheck = true;
    } else {
      memberPwCheck = false;
    }
  });

pwChangeForm.addEventListener("submit", e => {
  if(!memberPwCheck){
    alert("비밀번호 유형이 올바르지 않습니다.");
    e.preventDefault();
  }
});