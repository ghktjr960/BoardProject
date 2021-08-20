<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
<% if(request.getAttribute("resultCheck") != null && request.getAttribute("resultCheck").equals("false")){ %>
	<script>
		alerf("회원 정보 수정에 실패하였습니다.")
		location.href="<%=request.getContextPath()%>/member.do";
	</script>
<% } else { %>
</head>
<body>
<h1>회원 정보 수정</h1>
<form action="${pageContext.request.contextPath}/editProc.do" method="post" onsubmit="return registCheck()">
	<b>아이디</b><br>
	<input type="text" id="id" name="id" value="${memberInfo.id}" readonly><br><br>
	<b>비밀번호</b><br>
	<input type="password" id="password" name="password"><br><br>
	<b>비밀번호 확인</b><br>
	<input type="password" id="repassword" name="repassword"><br><br>
	
	<b>이름</b><br>
	<input type="text" name="name" value="${memberInfo.name }"><br><br>
	<b>생년월일</b><br>
	<input type="text" name="year" placeholder="년(숫자)" size="3" value="${memberInfo.year }">
	<input type="text" name="month" placeholder="월(숫자)" size="3" value="${memberInfo.month }">	
	<input type="text" name="day" placeholder="일(숫자)" size="3" value="${memberInfo.day }"><br><br>
	<b>성별</b><br>
	<c:if test="${memberInfo.gender eq 'man'}">
		<input type="radio" name="gender" value="man" checked="checked">남자
		<input type="radio" name="gender" value="woman">여자<br><br>
	</c:if>
	<c:if test="${memberInfo.gender eq 'woman'}">
		<input type="radio" name="gender" value="man">남자
		<input type="radio" name="gender" value="woman" checked="checked">여자<br><br>
	</c:if>
	
	<b>이메일(선택)</b><br>
	<input type="email" name="email" value="${memberInfo.email}"><br><br>
	
	<input type="submit" value="수정완료">
	<input type="button" value="나가기" onclick="window.location='${pageContext.request.contextPath}/member.do'">
</form>
<script src="${pageContext.request.contextPath}/script/registScript.js"></script>
<%} %>
</body>
</html>