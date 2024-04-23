package edu.kh.semi.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.semi.member.dto.Member;
import lombok.RequiredArgsConstructor;

@Mapper

public interface MemberMapper {

	/** 입력한 회원 정보 조회
	 * @param memberEmail
	 * @return memberEmail
	 */
	Member login(String memberEmail);

}
