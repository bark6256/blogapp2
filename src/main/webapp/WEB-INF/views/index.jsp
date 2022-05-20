<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- 
	자신의 서버가 한다.
	<% response.sendRedirect("/board"); %>
 --%>

<!-- 상대의 PC가 일한다. 부담을 넘길 수 있다. 이걸 쓰는게 좋다 -->
<script>
	location.href="/board";
</script>