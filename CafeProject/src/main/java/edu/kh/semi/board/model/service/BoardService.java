package edu.kh.semi.board.model.service;

import edu.kh.semi.board.model.dto.Board;

public interface BoardService {


	Board selectBoard(String boardTitle, String boardContent, int memberNo);
	

	

}
