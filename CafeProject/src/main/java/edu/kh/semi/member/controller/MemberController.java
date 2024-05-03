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
		return "board/Login";
	}
	
	@GetMapping("myPage")
	public String myPage() {
		return "/member/myPage";
	}
	
	@GetMapping("withdrawal")
	public String withdrawal() {
		return "/member/withdrawal";
	}
	
	@PostMapping("withdrawal")
	public String withdrawal(
			@RequestParam("currentPassword") String currentPassword,
			@SessionAttribute("loginMember") Member loginMember,
			SessionStatus status,
			RedirectAttributes ra) {

		int memberNo = loginMember.getMemberNo();

		int result = service.withdrawalMember(currentPassword, memberNo);

		String message = null;

		if(result > 0) {
			message = "탈퇴가 완료되었습니다.";
			ra.addFlashAttribute("message", message);
			status.setComplete();
			return "redirect:/";
		}
		else {
			message = "탈퇴가 안 돼";
			ra.addFlashAttribute("message", message);
			return "redirect:/";
		}
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
	
	@GetMapping("findPw")
	public String findPw() {
		return "board/findPw";
	}
	
    @PostMapping("findPw")
     public String findPw(
             @RequestParam("memberEmail") String memberEmail,
                          RedirectAttributes ra,
             Model model,
             HttpSession session) {
         
         int result = service.findPw(memberEmail);
         String message = null;
        
         if(result > 0) {
             session.setAttribute("memberEmail", memberEmail);
             return "board/successFindPw";
         }
         else {
             message = "일치하는 회원 정보가 없습니다.";
             ra.addFlashAttribute("message", message);
             return "redirect:/member/findPw";
         }
     }
     
     
     @GetMapping("updatePw")
     public String updatePw() {
         return "/board/successFindPw";
    }
    
	 @PostMapping("updatePw")
	 public String updatePw(
	         @RequestParam("memberPw") String memberPw,
	         @RequestParam("memberPwCheck") String memberPwCheck,
	         @SessionAttribute("memberEmail") String memberEmail,
	             RedirectAttributes ra) {
	         String message = null;
	 
	         if(!memberPw.equals(memberPwCheck)) {
	             message = "비밀번호를 제대로 입력해주세요.";
	         ra.addFlashAttribute("message", message);
	         return "redirect:/member/updatePw";
	     }
	     
	     int result = service.updatePw(memberPw, memberEmail);
	     
	     if(result > 0) {
	         message = "비밀번호가 변경되었습니다.";
	         ra.addFlashAttribute("message", message);
	         return "redirect:/";
	     }
	     else{
	         return "redirect:/";
	     }
	 }
	 
   @PostMapping("profile")
   public String profile(@RequestParam("profileImg") MultipartFile profileImg,
         @SessionAttribute("loginMember") Member loginMember,
         @RequestParam("memberNickname") String memberNickanme,
         RedirectAttributes ra,
         HttpSession session)  throws IllegalStateException, IOException {

      
      int result = service.profile(loginMember, profileImg, memberNickanme);
      
      String message = null;
      
      if(result > 0) {
         message = "변경 성공!";
         loginMember.setMemberNickname(memberNickanme);
      }
      else {
         message = "변경 실패...";
      }
      
      ra.addFlashAttribute("message", message);
      
      return "redirect:myPage";
   }
   
   @GetMapping("changePw")
   public String changePw() {
      return "member/changePw";
   }
	   
	@PostMapping("changePw")
	public String changePw(
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) {


		int result = service.changePw(currentPassword, newPassword, loginMember);

		String message = null;

		if(currentPassword.equals(newPassword)) {
			message = "현재 비밀번호와 다른 비밀번호를 입력해주세요.";
			ra.addFlashAttribute("message", message);
			return "redirect:changePw";
		}

		if(!confirmPassword.equals(newPassword)) {
			message = "비밀번호 확인이 올바르지 않습니다.";
			ra.addFlashAttribute("message", message);
			return "redirect:changePw";
		}	
		
		if(result == 0) {
			message = "현재 비밀번호가 올바르지 않습니다.";
			ra.addFlashAttribute("message", message);
			return "redirect:changePw";
		}
		else {
			message = "비밀번호가 변경되었습니다.";
			ra.addFlashAttribute("message", message);
			return "redirect:myPage";
		}
	}
}
