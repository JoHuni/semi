/* 이미지 */
const previewList = document.getElementsByClassName("preview");
const inputImageList = document.getElementsByClassName("inputImage");
const deleteImageList = document.getElementsByClassName("delete-image");


//이미지 선택 이후 취소 버튼을 눌렀을 때 백업 이미지
const backupInputList = new Array(inputImageList.length);

/**
 * 
 * @param {*} inputImage : 파일이 선택된 input 태그
 * @param {*} order : 이미지 순서
 */
const addImage=(inputImage, order)=>{

    //파일의 최대 크기
    const maxSize = 1024*1024*10;

    const file = inputImage.files[0];

    //파일 선택이 취소된 경우
    if(file==undefined){
        console.log("파일 선택 취소됨");

        //백업본을 temp에 복제
        const temp= backupInputList[order].cloneNode(true);

        inputImage.after(temp);
        inputImage.remove();
        inputImage=temp;

        inputImage.addEventListener("change",e=>{
            addImage(e.target,order);
        })
        return;
    }



    //파일 크기가 최대 크기를 초과한 경우
    if(file.size>maxSize){
        alert("파일의 크기가 용량을 초과했습니다");

        if(backupInputList[order]==undefined ||
            backupInputList[order].value==''){
                inputImage.value=''; //파일 값 삭제

                return;
            }

            const temp = backupInputList[order].cloneNode(true);

            inputImage.after(temp);
            inputImage.remove();
            inputImage=temp;

            inputImage.addEventListener("change", e=>{
                addImage(e.target,order);
            })
            return;
    }

    //이미지 미리보기
    const reader = new FileReader();  //JS에서 파일을 읽고 저장하는 객체

    reader.readAsDataURL(file);

    reader.addEventListener("load", e=>{
        const url = e.target.result;

        previewList[order].src=url;

        //backupInputList에 input 태그를 복제
        backupInputList[order]=inputImage.cloneNode(true);

    })

}

for(let i=0; i<inputImageList.length;i++){
    inputImageList[i].addEventListener("change",e=>{
        addImage(e.target,i);
    })

    //x 버튼 클릭 시
    deleteImageList[i].addEventListener("click", ()=>{
        previewList[i].src="";
        inputImageList[i].value="";
        backupInputList[i].value="";
    })
}



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