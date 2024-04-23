package edu.kh.semi.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.semi.member.dto.Member;
import edu.kh.semi.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	
	@Override
	public Member login(Member inputMember) {
		
		String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
		
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		return null;
	}


	@Override
	public int signup(String memberEmail, String memberNickname, String memberPw, String memberTel) {
		Member member = new Member();
		member.setMemberEmail(memberEmail);
		member.setMemberNickname(memberNickname);
		member.setMemberPw(memberPw);
		return mapper.signup(member);
	}
}
