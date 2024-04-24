package edu.kh.semi.board.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.semi.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	int insertBoard(Board board);

}
