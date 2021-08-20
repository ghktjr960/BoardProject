<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/loginProc.do" method="post">
		<input type="text" name="id" placeholder="아이디" required><br>
		<input type="password" name="password" placeholder="비밀번호" required><br>
		<input type="submit" value="로그인">
		<input type="button" value="나가기" onclick="window.location='${pageContext.request.contextPath}/'">
	</form>
</body>
</html>