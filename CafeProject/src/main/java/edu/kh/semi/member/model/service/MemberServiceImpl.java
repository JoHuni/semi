package edu.kh.semi.member.model.service;


import java.util.HashMap;
import java.util.Map;


import java.io.File;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.semi.common.util.Utility;
import edu.kh.semi.member.model.dto.Member;
import edu.kh.semi.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")

public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath;
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	
	@Override
	public Member login(Member inputMember) {
		
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		if(loginMember == null) {
			return null;
		}
		
		if(!bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		
		loginMember.setMemberPw(null);

		return loginMember;
	}



	@Override
	public int signup(Member member) {
		String encPw = bcrypt.encode(member.getMemberPw());
		member.setMemberEmail(member.getMemberEmail());
		member.setMemberNickname(member.getMemberNickname());
		member.setMemberPw(encPw);
		member.setMemberTel(member.getMemberTel());
		
		return mapper.signup(member);
	}

	
	@Override
	public int countMember() {
		return mapper.countMember();

	}

	
	@Override
	public String findId(String memberTel) {
		
		return mapper.findId(memberTel);
	}
	

	@Override
	public int emailRedundancy(String memberEmail) {
		return mapper.emailRedundancy(memberEmail);
		
		
		
		
	}
	
	@Override
	public int nickNameRedundancy(String memberNickname) {
		return mapper.nickNameRedundancy(memberNickname);
	}


	// 프로필 이미지 변경
	@Override
	public int profile(
			Member loginMember, MultipartFile profileImg, String memberNickanme) throws IllegalStateException, IOException {
		String updatePath = null;
		
		String rename = null;
		if(!profileImg.isEmpty()) {
			// updatePath
			
			rename = Utility.fileRename(profileImg.getOriginalFilename());
			
			// /myPage/profile/변경된파일명.jpg
			updatePath = profileWebPath + rename;
			
		}
		// 수정된 프로필 이미지 경로 + 회원 번호를 저장할 DTO 객체
		Member mem = Member.builder()
				.memberNo(loginMember.getMemberNo())
				.memberNickname(memberNickanme)
				.profileImg(updatePath)
				.build();
		
		int result = mapper.profile(mem);
		
		if(result > 0) { 
			
			// 프로필 이미지를 없앤 경우(NULL로 수정한 경우)를 제외
			if(!profileImg.isEmpty()) {				
				// 파일을 서버 지정된 폴더에 저장
				profileImg.transferTo(new File(profileFolderPath + rename));
			}
			
			loginMember.setProfileImg(updatePath);
		}
		
		
		return result;
	}

	@Override
	public int changePw(String currentPassword, String newPassword, Member loginMember) {
		Map<String, Object> map = new HashMap<>();
		map.put("currentPassword", currentPassword);
		map.put("memberNo", loginMember.getMemberNo());
		
		String encPw = mapper.checkPw(map);
		
		
		if(!bcrypt.matches(currentPassword, encPw)) {
			return 0;
		}
		else {
			String newPw = bcrypt.encode(newPassword);
			map.put("newPw", newPw);
			
			return mapper.changePw(map);
		}
	}
	
	@Override
	public int withdrawalMember(String currentPassword, int memberNo) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("currentPassword", currentPassword);
		map.put("memberNo", memberNo);
		
		String encPw = mapper.checkPw(map);
		
		
		if(!bcrypt.matches(currentPassword, encPw)) {
			return 0;
		}
		else {
			return mapper.withdrawal(memberNo);
		}
	}
	
	@Override
	public int findPw(String memberEmail) {
		return mapper.findPw(memberEmail);
	}
	
	@Override
	public int updatePw(String memberPw, String memberEmail) {
		String encPw = bcrypt.encode(memberPw);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("encPw", encPw);
		map.put("memberEmail", memberEmail);
		
		
		return mapper.updatePw(map);
	
	}
}
