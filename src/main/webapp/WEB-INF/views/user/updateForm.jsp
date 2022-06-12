<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<input type="text" id="username" value="${sessionScope.principal.username}" class="form-control" placeholder="Enter username" maxlength="20" readonly>
		</div>
		<div class="form-group">
			<input type="password" id="password" class="form-control" placeholder="Enter password" maxlength="20">
		</div>
		<div class="form-group">
			<input type="password" id="newpassword" class="form-control" placeholder="Enter password" maxlength="20">
		</div>
		<div class="form-group">
			<input type="email" id="email" value="${sessionScope.principal.email}" name="email" class="form-control" placeholder="Enter email">
		</div>
		<button type="button" class="btn btn-primary" onclick="update(${sessionScope.principal.id})">수정</button>
	</form>
	
	<script>
	async function update(id){
		let userUpdateDto = {
				username : document.querySelector("#username").value,
				password : document.querySelector("#password").value,
				newpassword : document.querySelector("#newpassword").value,
				email : document.querySelector("#email").value
		};
		
		let response = await fetch("/api/user/"+id,{
			method: "put",
			body: JSON.stringify(userUpdateDto),
			headers: {
				"Content-Type":"application/json; charset=utf-8"
			}
		});
		
		let parseResponse = await response.json();
		
		if(parseResponse.code == 1){
			alert("업데이트 성공");
			location.href = "/";
		} else {
			alert("업데이트 실패 - " + parseResponse.msg);
		}
	}

	$('#content').summernote({
		height : 350
	});
	</script>
</div>

<%@ include file="../layout/footer.jsp"%>