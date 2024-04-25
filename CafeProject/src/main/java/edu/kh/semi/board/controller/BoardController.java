package edu.kh.semi.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.semi.board.model.dto.Board;
import edu.kh.semi.board.model.service.BoardService;
import edu.kh.semi.member.model.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;
	
	/** 글쓰기 페이지로 이동 */
	@GetMapping("writeBoard")
	public String writeBoard() {
		return "board/boardWrite";
	}
	
	/** 글쓰기 작성 후 상세조회 페이지로 이동 */
	@PostMapping("insertBoard")
	public String insertBoard(
			@RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			RedirectAttributes ra,
			Model model) {
		
		int memberNo = loginMember.getMemberNo();
		int result = service.insertBoard(boardTitle, boardContent, memberNo);
		
		String path;
		String message;

		if (result > 0) {
			message = "글쓰기 성공";
			path = "/board/boardDetail/" + result; // result is assumed to be boardNo
		} else {
			message = "글쓰기 실패";
			path = "/";
		}

		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}

	@GetMapping("boardDetail/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("boardNo") int boardNo,
			Model model,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("boardNo", boardNo);
		if (loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		Board board = service.selectOne(map);
		if (board != null) {
			model.addAttribute("board", board);
		}
		return "/board/boardDetail";
	}
}
