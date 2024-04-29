package edu.kh.semi.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.semi.board.model.dto.Board;

public interface BoardService {



	/** 글 작성
	 * @param boardTitle
	 * @param boardContent
	 * @param memberNo
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int insertBoard(String boardTitle, String boardContent, int memberNo, String boardCheckPublic,
			String boardCheckNotice, List<MultipartFile> images) throws IllegalStateException, IOException;
	

	/** 게시글 상세조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Object> map);

	
	
	/** 게시글 리스트 조회
	 * @param boardType
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectBoardList(String boardType, int cp);





	

}
