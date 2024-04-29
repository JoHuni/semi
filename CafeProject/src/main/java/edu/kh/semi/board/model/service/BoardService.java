package edu.kh.semi.board.model.service;

import java.util.List;
import java.util.Map;
import edu.kh.semi.board.model.dto.Board;

public interface BoardService {



	/** 글 작성
	 * @param boardTitle
	 * @param boardContent
	 * @param memberNo
	 * @return
	 */
	int insertBoard(String boardTitle, String boardContent,int memberNo,String boardCheckPublic,
			String boardCheckNotice );

	/** 게시글 상세조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Integer> map);

	
	
	/** 게시글 리스트 조회
	 * @param boardType
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoardList(String boardType, int cp);

	

}
