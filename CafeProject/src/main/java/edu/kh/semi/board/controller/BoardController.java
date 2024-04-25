package edu.kh.semi.board.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	
	
	
	/** 글쓰기 페이지로 이동
	 * @return
	 */
	@GetMapping("writeBoard")
	public String writeBoard() {
		return "board/boardWrite";
	}
	
	
	/** 글씨기 작성
	 * @return
	 */
	@GetMapping("insertBoard")
	public String insertBoard(
			@RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent,
			@RequestParam(value = "boardCheckPublic", required = false) String boardCheckPublic,
            @RequestParam(value = "boardCheckNotice", required = false) String boardCheckNotice,
			@SessionAttribute (value="loginMember", required=false ) Member loginMember,
			RedirectAttributes ra,
			Model model) {
		
		
		int memberNo= loginMember.getMemberNo();
		
		int boardNo= service.insertBoard(boardTitle, boardContent,memberNo,boardCheckPublic,boardCheckNotice);
		
		String path = null;
		String message=null;
		

		
		
		if(boardNo>0) {
			path="/board/boardDetail/" + boardNo ;
			message="글쓰기 성공";
		}
		else {
			path="/";
			message="글쓰기 실패";
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:"+ path;
	}
	
	
	
	
	/** 상세조회
	 * @param boardNo
	 * @param model
	 * @param loginMember
	 * @return
	 */
	@GetMapping("boardDetail/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("boardNo") int boardNo,
			Model model,
			@SessionAttribute(value="loginMember", required=false) Member loginMember
			) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("boardNo", boardNo);
		
		if(loginMember!=null) { // 로그인된 상태
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		Board board = service.selectOne(map);
		
		
		if(board!=null) { 
			model.addAttribute("board", board);
		}

		
		
		return "/board/boardDetail";
	}
	
	
	
	/*
	 * @GetMapping("{boardType:(memberBoard|publicBoard|noticeBoard)Board}") public String
	 * boardList(
	 * 
	 * @PathVariable("boardType") String boardType,
	 * 
	 * @RequestParam(value="cp", required = false, defaultValue = "1") int cp, Model
	 * model) {
	 * 
	 * 
	 * List<Board> board = service.selectBoardList(boardType,cp);
	 * model.addAttribute("board", board); model.addAttribute("boardType",
	 * boardType);
	 * 
	 * return "board/boardList"; }
	 */
	
	
	
	/**게시판 조회
	 * @param boardType
	 * @param cp
	 * @param model
	 * @return
	 */
	@GetMapping("/{boardType}Board")
	public String boardList(
	        @PathVariable("boardType") String boardType,
	        @RequestParam(value="cp", required = false, defaultValue = "1") int cp,
	        Model model) {

	    if (!Arrays.asList("member", "public", "notice").contains(boardType)) {
	        return "/";
	    }

	    List<Board> boardList = service.selectBoardList(boardType,cp);
	   
	    model.addAttribute("boardList", boardList);
	    model.addAttribute("boardType", boardType);

	    return "board/boardList";
	}
	
	


	
	
	
	@GetMapping("findId")
	public String findId() {
		return "board/findId";
	}
	
}
