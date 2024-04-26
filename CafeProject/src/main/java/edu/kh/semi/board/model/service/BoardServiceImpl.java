package edu.kh.semi.board.model.service;


import java.util.List;

import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.semi.board.model.dto.Board;
import edu.kh.semi.board.model.dto.Pagination;

import edu.kh.semi.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper mapper;
	

	//글작성
	@Override
	public int insertBoard(String boardTitle, String boardContent, int memberNo,String boardCheckPublic,
			String boardCheckNotice) {

	
		Board board = new Board();
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setMemberNo(memberNo);


		
		if(boardCheckNotice==null) {
			board.setBoardCheckNotice("N");
		}else {
			board.setBoardCheckNotice(boardCheckNotice);
		}
		
		if(boardCheckPublic==null) {
			board.setBoardCheckPublic("N");
		}else {
			board.setBoardCheckPublic(boardCheckPublic);
		}

		
		int result = mapper.insertBoard(board);
		log.debug("result : " + result );
		
		
		if(result>0) {
			

			int boardNo = board.getBoardNo();
			return boardNo;
		}
	
		return 0;
	}
	
	
	
	
	
	//글 상세조회
	@Override
	public Board selectOne(Map<String, Object> map) {
		
		return mapper.selectOne(map);
	}
	
	
	
	//게시글 리스트 조회
	@Override
	public List<Board> selectBoardList(String boardType, int cp) {

		
		//페이지네이션
		/*
		 * int listCount = mapper.getListCount(boardType); Pagination pagination = new
		 * Pagination();
		 */
		
		
		
		//보드 리스트 조회
		List<Board> boardList = mapper.selectBoard(boardType);
		
		return boardList;
	}

}


