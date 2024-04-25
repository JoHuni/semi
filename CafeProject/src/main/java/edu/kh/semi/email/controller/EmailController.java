package edu.kh.semi.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.semi.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"authKey"})
@Controller
@RequestMapping("email")
@RequiredArgsConstructor 
public class EmailController {
	private final EmailService service;
	
	
	@ResponseBody
	@PostMapping("signup")
	public int signup(@RequestBody String email) {
		String authKey = service.sendEmail("signup", email);
		if(authKey != null) {
			return 1;
		}
		return 0;
	}
	
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(
			@RequestBody Map<String, Object> map) {
		
		return service.checkAuthKey(map);
		
	}
}
