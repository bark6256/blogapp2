<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<input type="text" id="title" class="form-control" placeholder="Enter title" value="${boardEntity.title}">
		</div>
		<div class="form-group">
			<textarea id="content" class="form-control" rows="5">${boardEntity.content}</textarea>
		</div>
		<button type="button" class="btn btn-primary" onclick="update(${boardEntity.id})">수정하기</button>
	</form>
</div>

<script>
	async function update(id){
		let boardUpdateDto = {
				title : document.querySelector("#title").value,
				content : document.querySelector("#content").value
		};
		
		let response = await fetch("/api/board/"+id,{
			method: "put",
			body: JSON.stringify(boardUpdateDto),
			headers: {
				"Content-Type":"application/json; charset=utf-8"
			}
		});
		
		let parseResponse = await response.json();
		
		if(parseResponse.code == 1){
			alert("업데이트 성공");
			location.href = "/board/" + id;
		} else {
			alert("업데이트 실패 - " + parseResponse.msg);
		}
	}

	$('#content').summernote({
		height : 350
	});
</script>



<%@ include file="../layout/footer.jsp"%>



