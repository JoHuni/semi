const inputPass = document.querySelector('input[name="memberPw"]');
const pwChangeForm = document.querySelector("#pwChangeForm");

let memberPwCheck = false;

inputPass.addEventListener("input", () => {
    const regexp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
  
    if(regexp.test(inputPass.value)){
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