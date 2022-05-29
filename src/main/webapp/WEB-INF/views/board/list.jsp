<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<c:forEach var="board" items="${boardsEntity}">
		<!-- 카드 글 시작 -->
		<div class="card">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary"></a>
			</div>
		</div>
		<br>
		<!-- 카드 글 끝 -->
	</c:forEach>

</div>

<%@ include file="../layout/footer.jsp"%>
