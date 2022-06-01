<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<c:if test="${boardEntity.user.username == sessionScope.username}">
		<a href="#" class="btn btn-warning">수정</a>
		<button class="btn btn-danger">삭제</button>
	</c:if>
	<br /><br />
	<div>
		<span>글 번호 : ${boardEntity.id}</span> 작성자 : <span><i>${boardEntity.user.username}</i></span>
	</div>
	<br />
	<div>
		<h3>${boardEntity.title}</h3>
	</div>
	<hr />
	<div>
		<div>${boardEntity.content}</div>
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