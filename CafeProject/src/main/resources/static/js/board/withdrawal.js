const agree = document.querySelector("#agree");

const withdrawalBtn = document.querySelector("#withdrawalBtn");

withdrawalBtn.addEventListener("click", e => {
    if(!agree.checked){
        alert("약관에 동의해주세요.");
        e.preventDefault();
        agree.focus();
        return;
    }
})