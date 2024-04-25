package edu.kh.semi.board.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.semi.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	int insertBoard(Board board);

	Board selectBoard(Board board);
	//상세조회
	Board selectOne(Map<String, Integer> map);
}
