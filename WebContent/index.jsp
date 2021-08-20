<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
</head>
<body>
	<%if(session.getAttribute("id") != null){ %>
		<a href="${pageContext.request.contextPath }/member.do"><%=session.getAttribute("id") %></a>
		<a href="${pageContext.request.contextPath }/logout.do">로그아웃</a>
	<%} else { %>
		<a href="${pageContext.request.contextPath }/loginForm.do">로그인</a>
		<a href="${pageContext.request.contextPath }/registForm.do">회원가입</a>
	<%} %>
	<br>게시판 : <button onclick="window.location='${pageContext.request.contextPath}/list.do'">게시판 바로가기</button>
</body>
</html>