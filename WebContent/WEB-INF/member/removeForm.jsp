<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
</head>
<body>
	<script>
		var result = confirm("탈퇴하시겠습니까?");
		if(result){
			location.href="<%=request.getContextPath()%>/removeProc.do"
		} else {
			history.go(-1);
		}
	</script>
</body>
</html>