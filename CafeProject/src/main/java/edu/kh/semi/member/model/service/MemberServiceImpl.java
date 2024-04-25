package edu.kh.semi.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.semi.member.model.dto.Member;
import edu.kh.semi.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	
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

}
