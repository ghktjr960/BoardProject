<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<%	String id = (String)session.getAttribute("id");
	if(id == null){ %>
	<script>
		alert("로그인 후 이용가능합니다.");
		history.go(-1);
	</script>
<%} else if(id != null && request.getAttribute("writer") != null){ 
		if(!(id.equals((String)request.getAttribute("writer")))){ %>
		<script>
			alert("작성자만 수정이 가능합니다.");
			history.go(-1);
		</script>
<% 	} else{ %>
</head>
<body>

	<h1>글수정</h1>
		<form action="${pageContext.request.contextPath}//update.do?pageNum=${pageNum}" method="post" enctype="multipart/form-data">
			<input type="hidden" name="num" value="${postVo.num}">
			<table>
		<tr>
			<td>제 목</td>
			<td>
				<input type="text" name="subject" placeholder="제목을 입력하세요" value="${postVo.subject}" required>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<textarea name="content" rows="13" cols="40" placeholder="내용을 입력하세요" required>${postVo.content}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			파일선택 &nbsp;<input name="fileName" type="file">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="작성완료">
				<input type="reset" value="다시작성">
				<input type="button" value="나가기" onclick="window.location='${pageContext.request.contextPath}/list.do?pageNum=${pageNum}'">
			</td>
		</tr>
	</table>
		</form>
</body>
<%			
		}
	} 
%>

</html>