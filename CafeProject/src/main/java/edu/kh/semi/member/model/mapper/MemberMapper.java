package edu.kh.semi.member.model.mapper;

import java.util.Map;

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

	/** 아이디 찾기
	 * @param memberTel
	 * @return
	 */
	String findId(String memberTel);

	/** 프로필 이미지 변경
	 * @param mem
	 * @return
	 */
	int profile(Member mem);


	String getEncryptedPass(String memberEmail);

	int checkPassRedundancy(Member member);

	int emailRedundancy(String memberEmail);

	int nickNameRedundancy(String memberNickname);

	String checkPw(Map<String, Object> map);

	int changePw(Map<String, Object> map);

	int withdrawal(int memberNo);

	int findPw(String memberEmail);

	int updatePw(String memberEmail);

	int updatePw(Map<String, Object> map);


}
