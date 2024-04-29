package edu.kh.semi.board.model.service;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Map;


import org.apache.ibatis.session.RowBounds;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.semi.board.model.dto.Board;
import edu.kh.semi.board.model.dto.Image;
import edu.kh.semi.board.model.dto.Pagination;

import edu.kh.semi.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper mapper;
	
	//config.properties 값을 얻어와 필드에 저장
		@Value("${my.board.web-path}")
		private String webPath;
		
		@Value("${my.board.folder-path}")
		private String folderPath;
	

	//글작성
	@Override
	public int insertBoard(String boardTitle, String boardContent, int memberNo, String boardCheckPublic,
			String boardCheckNotice, List<MultipartFile> images) throws IllegalStateException, IOException {
	
		Board board = new Board();
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setMemberNo(memberNo);
		


		
		if(boardCheckNotice==null) {
			board.setBoardCheckNotice("N");
		}
		else {
			board.setBoardCheckNotice(boardCheckNotice);
		}
		
		
		if(boardCheckPublic==null) {
			board.setBoardCheckPublic("N");
		}else {
			board.setBoardCheckPublic(boardCheckPublic);
		}

		
		int result = mapper.insertBoard(board);
		log.debug("result : " + result );
		
		if(result==0) return 0;
		
		
		int boardNo = board.getBoardNo();
		
		List<Image> uploadList = new ArrayList<>();
		
		for(int i=0; i<images.size(); i++) {
			if(!images.get(i).isEmpty()) {
				String imgName=images.get(i).getOriginalFilename();
				
				Image img = Image.builder()
							.imgName(imgName)
							.imgOrder(i)
							.imgPath(webPath)
							.boardNo(boardNo)
							.uploadFile(images.get(i))
							.build();
				uploadList.add(img);
			}
		}
		
		if(uploadList.isEmpty()) {
			return boardNo;
		}

		
		result=mapper.insertUploadList(uploadList);
		
		if(result==uploadList.size()) {
			
			for(Image img : uploadList) {
				img.getUploadFile()
					.transferTo(new File(folderPath + img.getImgName()));
			}
		}else {
			
			throw new RuntimeException("이미지가 정상 삽입되지 않음!!");
			
		}
	
		return boardNo;
	}
	
	
	
	
	
	//글 상세조회
	@Override
	public Board selectOne(Map<String, Object> map) {
		return mapper.selectOne(map);
	}
	
	
	
	//게시글 리스트 조회
	@Override
	public Map<String, Object> selectBoardList(String boardType, int cp) {

		
		//페이지네이션
		/*
		 * int listCount = mapper.getListCount(boardType); Pagination pagination = new
		 * Pagination();
		 */
		
		int listCount = mapper.getListCount(boardType);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		
		int offset = (cp-1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.selectBoard(boardType, bounds);

		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		return map;
	}








}


