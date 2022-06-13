package com.cos.blogapp2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.board.BoardRepository;
import com.cos.blogapp2.domain.comment.Comment;
import com.cos.blogapp2.domain.comment.CommentRepository;
import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp2.handler.ex.MyNotFoundException;
import com.cos.blogapp2.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	
	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public void 댓글삭제(int id, User principal) {
		Comment commentEntity = commentRepository.findById(id)
				.orElseThrow(() -> new MyAsyncNotFoundException("댓글을 찾지 못했습니다."));
		if(commentEntity.getUser().getId() != principal.getId())
			throw new MyAsyncNotFoundException("권한이 없습니다.");
		
		commentRepository.deleteById(id);
	}

	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public void 댓글쓰기(int boardId, CommentSaveReqDto dto, User principal) {
		Board boardEntity = boardRepository.findById(boardId)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
		Comment comment = dto.toEntity(principal, boardEntity);
		
		commentRepository.save(comment);
	}

}
