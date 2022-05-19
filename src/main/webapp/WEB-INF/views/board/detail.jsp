<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<a href="#" class="btn btn-warning">수정</a>
	<button class="btn btn-danger">삭제</button>
	<br /><br />
	<div>
		<span>글 번호 : 1</span> 작성자 : <span><i>홍길동</i></span>
	</div>
	<br />
	<div>
		<h3>글 제목</h3>
	</div>
	<hr />
	<div>
		<div>글 내용</div>
	</div>
	<hr />

	<div class="card">
		<!-- 댓글 시작 -->
		<form>
			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="1" id="ta-content"></textarea>
			</div>
			<div class="card-footer">
				<button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
			</div>
		</form>
		<!-- 댓글 끝 -->
	</div>
	<br />
</div>

<%@ include file="../layout/footer.jsp"%>