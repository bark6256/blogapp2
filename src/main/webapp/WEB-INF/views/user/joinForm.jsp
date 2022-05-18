<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="./layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Enter username" maxlength="20">
		</div>
		<div class="form-group">
			<input type="password" class="form-control" placeholder="Enter password" maxlength="20">
		</div>
		<div class="form-group">
			<input type="email" class="form-control" placeholder="Enter email">
		</div>
		<button type="submit" class="btn btn-primary">회원가입</button>
	</form>
</div>

<%@ include file="./layout/footer.jsp"%>