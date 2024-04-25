package edu.kh.semi.member.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.semi.member.model.dto.Member;
import edu.kh.semi.member.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("member")
@Controller
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
public class MemberController {
	private final MemberService service;
	
	@GetMapping("login")
	public String login() {
		return "/board/Login";
	}
	

	@GetMapping("signup")
	public String register() {
		return "board/signup";
	}
	
	@PostMapping("register")
	public String signup( 
			Member member,
			RedirectAttributes ra
			) {
		
		int result = service.signup(member);
		
		String message = null;
		if(result > 0) {
			message = "가입 성공!";
			ra.addFlashAttribute("message", message);
			return "redirect:/";
		}else {
			message = "가입 실패";
			return "board/signup";
		}
		
	}

	/** 로그인
	 * @param inputMember 사용자 입력 값
	 * @param ra
	 * @param model
	 * @return
	 */
	@PostMapping("login")
	public String login(
			Member inputMember,
			RedirectAttributes ra,
			Model model) {
		
		Member loginMember = service.login(inputMember); 

		String message = null;
		if(loginMember == null) {
			message = "아이디 또는 비밀번호가 일치하지 않습니다";
			ra.addFlashAttribute("message", message);
			return "redirect:/member/login";
		}
		else {
			model.addAttribute("loginMember", loginMember);
			return "redirect:/";
		}
	}
	
	/** 사이드 메뉴에 나타나는 회원 수
	 * @return 
	 */
	@GetMapping("countMember")
	@ResponseBody
	public int countMember() {
		return service.countMember();
	}
	

	/** 로그아웃
	 * @param status
	 * @return
	 */
	@PostMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
	
	@GetMapping("findId")
	public String findId() {
		return "board/findId";
	}
	
	@GetMapping("myPage")
	public String myPage() {
		return "member/myPage";
	}
	@PostMapping("findId")
	public String findId(
	        @RequestParam("memberTel") String memberTel,
	        RedirectAttributes ra,
	        Model model) {
	    String memberId = service.findId(memberTel);
	    if(memberId.isEmpty()) {
	        ra.addFlashAttribute("message", "일치하는 회원 정보가 없습니다.");
	        return "redirect:/member/findId";
	    }
	    else {
	    	model.addAttribute("memberId", memberId);
	    	return "board/successFindId";
	    }
	}
	
	@ResponseBody
	@PostMapping("checkEmailRedundancy")
	public int emailRedundancy(@RequestBody String memberEmail) {
		int emailCheck =  service.emailRedundancy(memberEmail);
		
		return emailCheck;
		
	}
	
	@ResponseBody
	@PostMapping("checkNicknameRedundancy")
	public int nickNameRedundancy(@RequestBody String memberNickname) {
		return service.nickNameRedundancy(memberNickname);
	}
  
	@PostMapping("profile")
	public String profile(@RequestParam("profileImg") MultipartFile profileImg,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("memberNickname") String memberNickanme,
			RedirectAttributes ra,
			HttpSession session)  throws IllegalStateException, IOException {
		
		// 서비스 호출
		// -> /myPage/test/변경된 파일명 형태의 문자열
		// 	  현재 로그인한 회원의 PROFILE_IMG 컬럼 값으로 수정
		
		int result = service.profile(loginMember, profileImg, memberNickanme);
		
		String message = null;
		
		if(result > 0) {
			message = "변경 성공!";
			loginMember.setMemberNickname(memberNickanme);
			// 세션에 저장된 로그인 회원 정보에
		}
		else {
			message = "변경 실패...";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:myPage";
	}

}
