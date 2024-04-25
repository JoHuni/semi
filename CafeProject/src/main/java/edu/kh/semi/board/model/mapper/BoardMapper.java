package edu.kh.semi.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.semi.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	int insertBoard(Board board);

	/** 게시글 리스트 조회
	 * @param boardType
	 * @return
	 */
	List<Board> selectBoard(String boardType);



	/** 게시판 상세조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Integer> map);

	/**게시판 수 세기
	 * @param boardType
	 * @return
	 */
	int getListCount(String boardType);

}
