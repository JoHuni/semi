package edu.kh.semi.board.model.dto;

public class Pagination {
	
	private int currentPage; //현재 페이지 
	private int listCount; //전체 게시물 수
	
	private int limit=10;//한 페이지에 보여지는 게시글 수 
	private int pageSize = 5; //보여질 페이지 번호 개수
	
	private int maxPage; //마지막 페이지 번호
	private int startPage; //보여지는 맨 앞 페이지 번호
	private int endPage; //보여지는 맨 뒤 페이지 번호

	
}
