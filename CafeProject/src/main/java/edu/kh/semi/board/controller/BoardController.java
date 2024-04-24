package edu.kh.semi.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.semi.board.model.dto.Board;
import edu.kh.semi.board.model.service.BoardService;
import edu.kh.semi.member.model.dto.Member;
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
	public String boardDetail(
			@RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent,
			@SessionAttribute (value="loginMember", required=false ) Member loginMember,
			Model model) {
		
		
		int memberNo= loginMember.getMemberNo();
		
		Board board =service.selectBoard(boardTitle,boardContent,memberNo);

		
		
		
		return "board/boardDetail";
	}
	
	@GetMapping("findId")
	public String findId() {
		return "board/findId";
	}
	
}
