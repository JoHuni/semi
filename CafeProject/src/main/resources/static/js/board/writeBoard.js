/* 이미지 */




/* 글쓰기 유효성 검사 */
const boardForm = document.querySelector("#boardForm");  /* 등록 버튼 */

boardForm.addEventListener("submit",e=>{
    const boardTitle = document.querySelector("#boardTitle") // 글 제목 input
    const boardContent = document.querySelector("#boardContent") // 글 내용 textarea

    if(boardTitle.value.trim().length==0){
        alert("제목 입력 후 등록버튼을 눌러주세요");
        boardTitle.focus();
        e.preventDefault();
        return;
    }
    
    if(boardContent.value.trim().length==0){
        alert("내용 입력 후 등록버튼을 눌러주세요 ");
        boardContent.focus();
        e.preventDefault();
        return;
    }

    alert("게시글 작성이 완료되었습니다.");
    return;

})