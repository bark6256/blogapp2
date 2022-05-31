<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<c:forEach var="board" items="${boardsEntity.content}">
		<!-- 카드 글 시작 -->
		<div class="card">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
		<br>
		<!-- 카드 글 끝 -->
	</c:forEach>


	<fmt:parseNumber var="prev" value="${Math.floor(boardsEntity.number/10)*10}" integerOnly="true" />
	<fmt:parseNumber var="next" value="${Math.floor(boardsEntity.number/10+1)*10+1}" integerOnly="true" />
	<!-- 페이징 시작 -->
	<div class="d-flex justify-content-center">
		<ul class="pagination">
			<!-- 이전 페이지 -->
			<c:if test="${ !(1 > prev)}">
				<li class="page-item"><a class="page-link" href="/board?page=1">First</a></li>
				<li class="page-item"><a class="page-link" href="/board?page=${prev}">Prev</a></li>
			</c:if>
			
			<!-- 숫자 페이지 -->
			<c:forEach begin="${prev+1}" end="${next-1}" var="nPage">
				<c:if test="${!(nPage > boardsEntity.totalPages)}">
					<c:choose>
						<c:when test="${nPage == (boardsEntity.number+1)}">
							<li class="page-item active"><a class="page-link">${nPage}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="/board?page=${nPage}">${nPage}</a></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			
			<!-- Next 페이지 -->
			<c:if test="${!(boardsEntity.totalPages < next)}">
				<li class="page-item"><a class="page-link" href="/board?page=${next}">Next</a></li>
				<li class="page-item"><a class="page-link" href="/board?page=${boardsEntity.totalPages}">Last</a></li>
			</c:if>
		</ul>
	</div>
</div>

<%@ include file="../layout/footer.jsp"%>
