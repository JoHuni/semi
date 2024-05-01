package edu.kh.semi.board.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {
	
	private int boardNo; 
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private String boardUpdateDate;
	private int readCount;
	private String boardDelFl;
	private String boardCheckNotice;
	private String boardCheckPublic;
	private int memberNo;
	
	private String memberNickname;
	
	private String boardType;
	
	private List<Image> imageList;
	
	private List<Comment> commentList;
	
	private int commentCount;

}
