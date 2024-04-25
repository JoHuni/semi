package edu.kh.semi.board.model.service;

import java.util.Map;

import edu.kh.semi.board.model.dto.Board;

public interface BoardService {


	/** 글 작성
	 * @param boardTitle
	 * @param boardContent
	 * @param memberNo
	 * @return
	 */
	int insertBoard(String boardTitle, String boardContent,int memberNo );

	/** 게시글 상세조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Integer> map);
	

	

}
