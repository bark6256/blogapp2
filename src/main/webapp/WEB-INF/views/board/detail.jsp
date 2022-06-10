<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<c:if test="${boardEntity.user.username == sessionScope.principal.username}">
		<a href="/board/${boardEntity.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger" type="button" onclick="deleteById(${boardEntity.id})">삭제</button>
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
				<textarea name="content" id="content" class="form-control" rows="1" id="ta-content"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save" class="btn btn-primary" onclick="commentSave(${boardEntity.id})">등록</button>
			</div>
		</form>
		<!-- 댓글 끝 -->
	</div>
	<br />

<script>
	// 게시글 삭제
	async function deleteById(id){
	
		let response = await fetch("/board/"+id,{
			method: "delete"
		});
		
		let parseResponse = await response.json();
		
		if(parseResponse.code == 1){
			location.href = "/";
		} else {
			alert("업데이트 실패 - " + parseResponse.msg);
		}
	}
	
	// 댓글 작성
	async function commentSave(id){
		let commentSaveDto = {
				content : document.querySelector("#content").value
		};
		
		let response = await fetch("/board/"+id+"/comment",{
			method: "post",
			body: JSON.stringify(commentSaveDto),
			headers: {
				"Content-Type":"application/json; charset=utf-8"
			}
		});
		
		let parseResponse = await response.json();
		
		if(parseResponse.code == 1){
			location.href = "/board/" + id;
		} else {
			alert("업데이트 실패 - " + parseResponse.msg);
		}
	}
</script>

	<div class="card">
		<div class="card-header">
			<b>댓글 리스트</b>
		</div>
		<ul id="reply-box" class="list-group">
			<c:forEach var="comment" items="${boardEntity.comments}">
				<li id="reply-${comment.id}" class="list-group-item d-flex justify-content-between">
					<div>${comment.content}</div>
					<div class="d-flex">
						<div class="font-italic">작성자 : ${comment.user.username} &nbsp;</div>
						<button class="badge" id="reply" onclick="commentDelete(${comment.id})">삭제</button>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	
	<script>
	// 댓글 삭제
	async function commentDelete(commentId){
		
		let response = await fetch("/comment/" + commentId,{
			method: "delete"
		});
		
		let parseResponse = await response.json();
		
		if(parseResponse.code == 1){
			alert("댓글 삭제 성공");
			$("#reply-" + commentId).remove();
		} else {
			alert("업데이트 실패 - " + parseResponse.msg);
		}
	}
	</script>
</div>
	
<%@ include file="../layout/footer.jsp"%>