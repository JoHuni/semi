package edu.kh.semi.board.model.service;

import org.springframework.stereotype.Service;

import edu.kh.semi.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper mapper;
}


