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

        //cp저장
        const input = document.createElement("input");
        input.type="hidden";
        input.name="cp";

        //쿼리스트링에서 원하는 파라미터 얻어오기
        const param = new URLSearchParams(location.search)
        const cp = param.get("cp");
        input.value=cp;

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



/* 목록으로 가기 */
const goListBtn = document.querySelector("#goListBtn");

// location.pathname+location.search='/board/memberBoard?cp=1' => 이런 결과를 만들어야함
//'/board/memberBoard/boardDetail/313'
// location.pathname :   /board/memberBoard/boardDetail/313
//location.pathname.substring(0,location.pathname.indexOf("/boardDetail/"))
//location.pathname.substring(0,location.pathname.indexOf("/boardDetail/"))+location.search : '/board/memberBoard?cp=1'
goListBtn.addEventListener("click",()=>{

    let url = location.pathname;
    url = url.substring(0,url.indexOf("/boardDetail/"));
    
    location.href=url+location.search;
})

