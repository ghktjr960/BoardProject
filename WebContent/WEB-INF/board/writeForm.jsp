<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<%	String id = (String)session.getAttribute("id");
	if(id == null){ %>
	<script>
		alert("로그인 후 이용가능합니다.");
		history.go(-1);
	</script>
<%} else { %>
</head>
<body>
<h1>글쓰기</h1>
<form action="${pageContext.request.contextPath}/write.do" method="post" enctype="multipart/form-data">
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="ref" value="${ref}">
	<input type="hidden" name="step" value="${step}">
	<input type="hidden" name="depth" value="${depth}">	
	<input type="hidden" name="writer" value="${id}">
	
	<table>
		<tr>
			<td>제 목</td>
			<td>
				<c:if test="${num == 0}">
					<input type="text" name="subject" placeholder="제목을 입력하세요" required>
				</c:if>
				<c:if test="${num != 0}">
					<input type="text" name="subject" value="[답글]&nbsp;" placeholder="제목을 입력하세요" required> 
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<textarea name="content" rows="13" cols="40" placeholder="내용을 입력하세요" required></textarea>
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
				<input type="button" value="나가기" onclick="window.location='${pageContext.request.contextPath}/list.do'">
			</td>
		</tr>
	</table>
</form>
<%} %>
</body>
</html>