<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<%
		if(session.getAttribute("id") != null){
	%>
		<script>
			location.href="<%=request.getContextPath()%>/";
		</script>
	<%
		} else {
	%>
		<script>
			alert("아이디와 비밀번호를 확인해주세요");
			document.location.href="<%=request.getContextPath()%>/loginForm.do";
		</script>
	<%
		}
	%>
</body>
</html>