<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그아웃</title>
</head>
<body>
	<script>
		alert("로그아웃 되었습니다.");
		document.location.href="<%=request.getContextPath()%>/index.jsp";
	</script>
</body>
</html>