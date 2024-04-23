package edu.kh.semi.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.semi.member.model.dto.Member;
import edu.kh.semi.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequestMapping("member")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService service;
	
	@GetMapping("login")
	public String login() {
		return "board/Login";
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

	@PostMapping("login")
	public String login(
			Member inputMember,
			RedirectAttributes ra,
			Model model) {
		
		Member loginMember = service.login(inputMember); 

		if(loginMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
		}
		
		
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember);
			ra.addFlashAttribute("message", "로그인 성공");
		}
		return "redirect:/";
	}
}
