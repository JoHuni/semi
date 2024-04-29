package edu.kh.semi.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.semi.member.model.dto.Member;

@Mapper

public interface MemberMapper {

	/** 입력한 회원 정보 조회
	 * @param memberEmail
	 * @return memberEmail
	 */
	Member login(String memberEmail);

	int signup(Member member);

	/** 사이드 메뉴에 나타나는 회원 수
	 * @return 
	 */
	int countMember();

	String getEncryptedPass(String memberEmail);

	int checkPassRedundancy(Member member);
	/** 아이디 찾기
	 * @param memberTel
	 * @return
	 */
	String findId(String memberTel);

	int nickNameRedundancy(String memberNickname);

	int emailRedundancy(String memberEmail);


}
