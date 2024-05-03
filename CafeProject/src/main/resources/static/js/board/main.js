const container = document.querySelector("#container");

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
