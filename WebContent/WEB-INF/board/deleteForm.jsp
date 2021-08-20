<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제 확인</title>
</head>
<body>
	<% if(session.getAttribute("id") == null){	%>
		<script>
			alert("로그인 후 이용가능합니다.");
			history.go(-1);
		</script>
	<%	} else if(!(session.getAttribute("id").equals(request.getAttribute("writer")))){%>
		<script>
			alert("작성자만 삭제가 가능합니다.");
			history.go(-1);
		</script>
	<%	} else {%>
	<script>
		var result = confirm("삭제하시겠습니까?");
		if(result){
			location.href="<%=request.getContextPath()%>/deleteProc.do?num=<%=request.getAttribute("postNum")%>&pageNum=<%=request.getAttribute("pageNum")%>"
		} else {
			history.go(-1);
		}
	</script>
	<% } %>
</body>
</html>