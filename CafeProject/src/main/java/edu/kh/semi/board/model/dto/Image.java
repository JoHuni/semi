package edu.kh.semi.board.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Image {
	

	private int imgNo;
	private String imgPath;
	private String imgName;
	private int imgOrder;
	
	private int boardNo;
	
	
	private MultipartFile uploadFile;
}
