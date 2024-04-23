package edu.kh.semi.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
	private int memberNo;
	private String memberEmail;
	private String memberPw;
	private String memberTel;
	private String memberNickname;
	private String profileImg;
	private String enrollDate;
	private String memberDelFl;
	private String authority;
}
