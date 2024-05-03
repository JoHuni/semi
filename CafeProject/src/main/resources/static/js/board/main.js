document.addEventListener("DOMContentLoaded", () => {
    const memberCount = document.querySelector("#memberCount");
    function updateMemberCount() {
        fetch("/member/countMember")
        .then(response => response.text())
        .then(result => {
            console.log(result);
            memberCount.innerHTML = "회원 수 : "  + result + "명";
        })
    }
    updateMemberCount();


    setInterval(updateMemberCount, 30000);
});

 
const images = document.querySelectorAll(".image");
const container = document.querySelector("#container");
let slideIndex = 0;
setInterval(() => {
 
    for(let i = 0; i<images.length; i++){
        images[i].style.display = "none";
    }
    slideIndex++;
    if(slideIndex > images.length){
        slideIndex=1;
    }
   images[slideIndex-1].style.display = "block";
}, 2000)
