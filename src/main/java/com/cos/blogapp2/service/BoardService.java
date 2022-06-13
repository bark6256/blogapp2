package com.cos.blogapp2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.board.BoardRepository;
import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp2.handler.ex.MyNotFoundException;
import com.cos.blogapp2.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public void 게시글삭제(int id, User principal) {
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
		
		if(boardEntity.getUser().getId() != principal.getId())
			throw new MyAsyncNotFoundException("권한이 없습니다.");
		
		boardRepository.deleteById(boardEntity.getId());
	}

	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public void 게시글수정(int id, BoardSaveReqDto dto, User principal) {
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
		
		if(boardEntity.getUser().getId() != principal.getId())
			throw new MyAsyncNotFoundException("권한이 없습니다.");
		
		boardEntity.setTitle(dto.getTitle());
		boardEntity.setContent(dto.getContent());
	}

	@Transactional(rollbackFor = MyNotFoundException.class)
	public Board 게시글수정페이지이동(int id) {
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
		return boardEntity;
	}

	@Transactional(rollbackFor = MyNotFoundException.class)
	public void 게시글생성(BoardSaveReqDto dto, User principal) {
		Board board = dto.toEntity(principal);
		boardRepository.save(board);
	}

	@Transactional(rollbackFor = MyNotFoundException.class)
	public Page<Board> 게시글목록(PageRequest pageRequest) {
		Page<Board> boardsEntity = boardRepository.findAll(pageRequest);
		return boardsEntity;
	}

	@Transactional(rollbackFor = MyNotFoundException.class)
	public Board 게시글상세보기(int id) {
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
		return boardEntity;
	}

}
