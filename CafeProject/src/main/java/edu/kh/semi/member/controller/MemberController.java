package edu.kh.semi.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.semi.member.dto.Member;
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
			@RequestParam("inputEmail") String memberEmail,
			@RequestParam("memberNickname") String memberNickname,
			@RequestParam("inputPass") String memberPw,
			@RequestParam("inputTel") String memberTel
			) {
		
		int member = service.signup(memberEmail, memberNickname, memberPw, memberTel);
		
	}

	@PostMapping("login")
	public String login(
			Member inputMember,
			RedirectAttributes ra,
			Model model) {
		
		Member loginMember = service.login(inputMember); 
		return null;

	}
}
