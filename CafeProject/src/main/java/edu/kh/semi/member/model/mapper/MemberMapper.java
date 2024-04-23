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

}
