/* 삭제 */
const deleteBtn = document.querySelector("#deleteBtn"); //삭제 버튼


if(deleteBtn!=null){
    deleteBtn.addEventListener("click", ()=>{

        if(!confirm("게시물을 삭제하시겠습니까?")){
            //취소 버튼 누른 경우
            alert("취소되었습니다.");
            return;
        }

        //확인 버튼 누른 경우 

        /* /board/memberBoard/boardDetail/299 */

        /* /board/memberBoard/boardDetail/299/delete */
        const url = location.pathname + "/delete";

        //form 태그 생성
        const form = document.createElement("form");
        form.action=url;
        form.method="POST";

        document.querySelector("body").append(form);
        form.submit();


    } 

)

}


/* 수정 */
const updateBtn = document.querySelector("#updateBtn");

if(updateBtn!= null){
    updateBtn.addEventListener("click",()=>{
        location.href=location.pathname+"/update";
    })
}


