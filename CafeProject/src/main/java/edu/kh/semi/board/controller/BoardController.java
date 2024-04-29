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
		        	model.addAttribute("memberNo", board.getMemberNo());
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
	
	/** 게시물 삭제
	 * @param boardType
	 * @param boardNo
	 * @param loginMember
	 * @param ra
	 * @return
	 */
	@PostMapping("{boardType}Board/boardDetail/{boardNo:[0-9]+}/delete")
	public String deleteBoard(
			@PathVariable("boardType") String boardType,
			@PathVariable("boardNo") int boardNo,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardType", boardType);
		map.put("boardNo", boardNo);
		map.put("memberNo", loginMember.getMemberNo());
		
		int result = service.deleteBoard(map);
		
		String path=null;
		String message = null;
		
		log.debug("result : " + result);
		
		if(result>0) {
			path=String.format("/board/%s", boardType + "Board");
			message="삭제되었습니다";
			
		}
		else {
			path=String.format("/board/%s/boardDetail/%d", boardType + "Board",boardNo);
			message="삭제 실패되었습니다";
		}
		
		ra.addFlashAttribute("message", message);
		
		
		return "redirect:"+ path;
		
	}
	
	
	
	
	@GetMapping("{boardType}Board/boardDetail/{boardNo:[0-9]+}/update")
	public String updateBoard(
			@PathVariable("boardType") String boardType,
			@PathVariable("boardNo") int boardNo,
			@SessionAttribute("loginMember") Member loginMember,
			Model model,
			RedirectAttributes ra) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardType", boardType);
		map.put("boardNo", boardNo);
		
		Board board = service.selectOne(map);
		
		String path=null;
		String message = null;
		
		if(board ==null) {
			message="해당 게시물이 존재하지 않습니다";
			path="redirect:";
			
			ra.addFlashAttribute("message", message);
		}
		else
		{
			path="board/boardUpdate";
			model.addAttribute("board", board);
					
		}
		
		return path;		
	}
	
	
	
	/** 게시물 수정하기
	 * @param boardType
	 * @param boardNo
	 * @param boardTitle
	 * @param boardContent
	 * @param images
	 * @param loginMember
	 * @param model
	 * @param ra
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("{boardType}Board/boardDetail/{boardNo:[0-9]+}/update")
	public String updateBoard(
			@PathVariable("boardType") String boardType,
			@PathVariable("boardNo") int boardNo,
			@RequestParam("boardTitle") String boardTitle, 
	        @RequestParam("boardContent") String boardContent, 
			@RequestParam(value="images", required = false) List<MultipartFile> images,
			@RequestParam(value = "deleteOrder", required=false) String deleteOrder, 
			@SessionAttribute("loginMember") Member loginMember,
			Model model,
			RedirectAttributes ra
			) throws IllegalStateException, IOException {
		
		Board board = new Board();
		
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setBoardNo(boardNo);
		
		int memberNo = loginMember.getMemberNo();
		board.setMemberNo(memberNo);
		
		int result = service.updateBoard(board,images,memberNo,deleteOrder);
		
		String path =null;
		String message=null;
		
		if(result > 0) {
			message="게시물 수정이 완료되었습니다.";
			path=String.format("/board/%s/boardDetail/%d", boardType + "Board",boardNo);
		}else {
			message="게시물 수정이 실패했습니다.";
			path="update";
		}
		
		ra.addFlashAttribute("message", message);
		model.addAttribute("board", board);
		
		return "redirect:"+path;
	}
	
}
