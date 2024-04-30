package edu.kh.semi.board.model.mapper;


import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.web.multipart.MultipartFile;

import org.apache.ibatis.session.RowBounds;


import edu.kh.semi.board.model.dto.Board;
import edu.kh.semi.board.model.dto.Image;

@Mapper
public interface BoardMapper {

	int insertBoard(Board board);

	/** 게시판 상세조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Object> map);

	/**게시판 수 세기
	 * @param boardType
	 * @return
	 */
	int getListCount(String boardType);

	/** 게시물 삭제
	 * @param map
	 * @return
	 */
	int deleteBoard(Map<String, Object> map);

	/** 게시물 수정
	 * @param board
	 * @param images
	 * @return
	 */
	int updateBoard(Board board);

	/** 이미지 수정
	 * @param img
	 * @return
	 */
	int updateImage(Image img);

	/** 이미지 한 행 추가
	 * @param img
	 * @return
	 */
	int insertImage(Image img);

	/**게시글 이미지 삭제
	 * @param map
	 * @return
	 */
	int deleteImage(Map<String, Object> map);

	List<Board> selectBoard(String boardType, RowBounds bounds);

	int insertUploadList(List<Image> uploadList);
	
	
	
	/* 조회수 관련 mapper*/
	int updateReadCount(int boardNo);

	int selectReadCount(int boardNo);
}

