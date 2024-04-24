package edu.kh.semi.board.model.service;

import org.springframework.stereotype.Service;

import edu.kh.semi.board.model.dto.Board;
import edu.kh.semi.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper mapper;
	
	@Override
	public Board selectBoard(String boardTitle, String boardContent, int memberNo) {
		
		Board board = new Board();
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setMemberNo(memberNo);
		
		int result = mapper.insertBoard(board);
		
		if(result>0) {
	
			log.debug("result "+ result);
		}
	return null;
	}
}


