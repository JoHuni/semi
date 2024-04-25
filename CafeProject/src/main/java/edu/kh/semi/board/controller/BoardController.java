package edu.kh.semi.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.semi.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;
	
	
	
	/** 글쓰기 페이지로 이동
	 * @return
	 */
	@GetMapping("writeBoard")
	public String writeBoard() {
		return "board/boardWrite";
	}
	
	
	/** 글씨기 작성 후 상세조회 페이지로 이동
	 * @return
	 */
	@PostMapping("boardDetail")
	public String boardDetail() {
		return "board/boardDetail";
	}
	
	/** 프로필 이미지 변경
	 * @return
	 */
	@GetMapping("findId")
	public String findId() {
		return "board/findId";
	}
	
	
}
