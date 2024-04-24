package edu.kh.semi.member.model.service;

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
<<<<<<< HEAD



	
=======
	
	@Override
	public String findId(String memberTel) {
		
		return mapper.findId(memberTel);
	}
>>>>>>> b8a5929e1e6d9f6daca596f779bedcd540a9da3c
}
