package edu.kh.semi.member.model.service;

import edu.kh.semi.member.model.dto.Member;

public interface MemberService {

	/** 로그인 기능
	 * @param inputMember : 로그인 시 입력한 이메일, 비밀번호
	 * @return
	 */
	Member login(Member inputMember);

	int signup(String memberEmail, String memberNickname, String memberPw, String memberTel);

}
