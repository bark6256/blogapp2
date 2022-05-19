<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Enter title">
		</div>
		<div class="form-group">
			<textarea id="summernote" class="form-control" rows="5"></textarea>
		</div>
		<button type="submit" class="btn btn-primary">글쓰기</button>
	</form>
</div>

<script>
	$('#summernote').summernote({
		height : 350
	});
</script>
<%@ include file="../layout/footer.jsp"%>



