document.addEventListener("DOMContentLoaded", () => {
    const memberCount = document.querySelector("#memberCount");
    function updateMemberCount() {
        fetch("/member/countMember")
        .then(response => response.text())
        .then(result => {
            console.log(result);
            memberCount.innerHTML = result + "명";
        })
    }
    updateMemberCount();


    setInterval(updateMemberCount, 300000);
});

 