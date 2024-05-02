package edu.kh.semi.email.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.semi.email.model.mapper.EmailMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	//EmailConfig 설정이 적용된 객체 (메일 보내기 가능)
	private final JavaMailSender mailSender;
	
	//Mapper 의존성 주입
	private final EmailMapper mapper;
	
	//타임리프 (템플릿 엔진) 을 이용해서 html 코드 -> java로 변환
	private final SpringTemplateEngine templateEngine;
	
	
	
	
	@Override
	public String sendEmail(String htmlName, String email) {
		String authKey = createAuthKey();
		try {
			String subject = null;
			switch(htmlName) {
				case "signup": subject = "[boardProject] 회원가입 인증번호 입니다";break;
			}
			
			//인증 메일 보내기
			
			//MimeMessage : Java 에서 메일을 보내는  객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
			
			helper.setTo(email); // 받는 사람 이메일 지정
			helper.setSubject(subject); // 이메일 제목 지정
			helper.setText( loadHtml(authKey, htmlName),true );
			
			helper.addInline("logo",  
					new ClassPathResource("static/images/logo.jpg"));
			
			mailSender.send(mimeMessage);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Map <String, String> map = new HashMap<>();
		
		map.put("authKey", authKey);
		map.put("email",email);
		
		int result = mapper.updateAuthKey(map);
		
		if(result == 0) {
			result = mapper.insertAuthKey(map);
		}
		if(result == 0) return null;
		
		return authKey;
	
	}
	
	public String loadHtml(String authKey, String htmlName) {
		Context context = new Context();
		context.setVariable("authKey",authKey);
		return templateEngine.process("email/" + htmlName, context );
	}
	
	public String createAuthKey() {
		String key = "";
		for(int i = 0; i < 6; i ++) {
			int sel1 = (int)(Math.random() * 3);
			if(sel1 == 0) {
				int num = (int)(Math.random() * 10);
				key += num;
			}else {
				char ch = (char)(Math.random() * 26 + 65);
				int sel2 = (int)(Math.random() * 2);
				if(sel2 ==0) {
					ch = (char)(ch + ('a' - 'A'));
				}
				
				key += ch;
			}
		}
		return key;
	}
	
	@Override
	public int checkAuthKey(Map<String, Object> map) {
		return mapper.checkAuthKey(map);
	}

	

}
