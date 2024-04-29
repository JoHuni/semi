package edu.kh.semi.board.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
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
	
	
	/** 글씨기 작성
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("insertBoard")
	public String insertBoard(
			@RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent,
			@RequestParam(value = "boardCheckPublic", required = false) String boardCheckPublic,
            @RequestParam(value = "boardCheckNotice", required = false) String boardCheckNotice,
			@RequestParam(value="images", required = false) List<MultipartFile> images,
            @SessionAttribute (value="loginMember", required=false ) Member loginMember,
			RedirectAttributes ra,
			Model model) throws IllegalStateException, IOException {
		
		
		int memberNo= loginMember.getMemberNo();
		
		int boardNo= service.insertBoard(boardTitle, boardContent,memberNo,boardCheckPublic,boardCheckNotice,images);
		
		String path = null;
		String message=null;
		

		String boardType ="member";
		
		if(boardCheckNotice=="Y") {
			if(boardCheckPublic==null) {
				boardCheckPublic="N";
			}
			boardType="notice";
		}
		
		if(boardCheckPublic=="Y") {
			if(boardCheckNotice==null) {
				boardCheckNotice="N";
			}
			boardType="public";
		}
		
		if(boardNo>0) {
			path="/board/"+boardType+"Board/boardDetail/"+boardNo;
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

	

	@GetMapping("{boardType}Board/boardDetail/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("boardNo") int boardNo,
			@PathVariable("boardType") String boardType,
			Model model,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			RedirectAttributes ra ) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardNo", boardNo);
		map.put("boardType", boardType);
		
		if (loginMember != null) {

			map.put("memberNo", loginMember.getMemberNo());
		}
		
		Board board = service.selectOne(map);
		
		String message=null;
		String path=null;
		
		 if (board != null) {
			 
		        model.addAttribute("board", board);
		        log.debug("boardType : " + boardType);
		        log.debug("board : " + board);
		        
		        if(board.getImageList() != null&&!board.getImageList().isEmpty()) {
		        	model.addAttribute("start", 0);
		        }
		       
		        
		        if(loginMember==null) {
			        if(boardType.equals("member")) {
			        	message="로그인 후 이용해주세요";
			        	ra.addFlashAttribute("message",message);
			        	return "redirect:/member/login";
			        }
		        }

		        path= "/board/boardDetail";
		        
		    } 
		 else {
		        path= "/board/" + boardType + "Board"; 
		    }
		
		
		return path;
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
	        Model model,
			@RequestParam Map<String, Object> paramMap) {

		Map<String, Object> map = null;

		if(paramMap.get("key") == null) {
			map = service.selectBoardList(boardType, cp);
		}
		
	    if (!Arrays.asList("member", "public", "notice").contains(boardType)) {
	        return "/";
	    }
	   
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));

	    return "board/boardList";
	}
}
