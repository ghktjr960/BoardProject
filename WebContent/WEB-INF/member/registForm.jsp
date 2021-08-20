<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registProc.do" method="post" name="registForm" onsubmit="return registCheck()">
	<b>아이디</b><br>
	<input type="text" id="id" name="id">
	<input type="button" id="idCheck" value="중복체크" onclick="idCheck()"><span id="idCheckResult"></span><br><br>
	<b>비밀번호</b><br>
	<input type="password" id="password" name="password"><br><br>
	<b>비밀번호 확인</b><br>
	<input type="password" id="repassword" name="repassword"><br><br>
	
	<b>이름</b><br>
	<input type="text" name="name"><br><br>
	<b>생년월일</b><br>
	<input type="text" name="year" placeholder="년(숫자)" size="3">
	<input type="text" name="month" placeholder="월(숫자)" size="3">	
	<input type="text" name="day" placeholder="일(숫자)" size="3"><br><br>
	<b>성별</b><br>
	<input type="radio" name="gender" value="man">남자
	<input type="radio" name="gender" value="woman">여자<br><br>
	<b>이메일(선택)</b><br>
	<input type="email" name="email"><br><br>
	
	<input type="submit" value="가입하기">
	<input type="button" value="나가기" onclick="window.location='${pageContext.request.contextPath}/'">
</form>
<script src="${pageContext.request.contextPath}/script/registScript.js"></script>
</body>
</html>