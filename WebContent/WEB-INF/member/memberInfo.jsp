<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보</title>
</head>
<body>
	<h1>회원 정보</h1>
	<table>
		<tr>
			<td><b>아이디</b></td>
			<td>${memberInfo.id}</td>
		</tr>
		<tr>
			<td><b>이 름</b></td>
			<td>${memberInfo.name}</td>
		</tr>
		<tr>
			<td><b>생년월일</b></td>
			<td>${memberInfo.year}.${memberInfo.month}.${memberInfo.day}</td>
		</tr>
		<tr>
			<td><b>성 별</b></td>
			<c:if test="${memberInfo.gender eq 'man'}">
				<c:set var="gender" value="남자"/>
			</c:if>
			<c:if test="${memberInfo.gender eq 'woman'}">
				<c:set var="gender" value="여자"/>
			</c:if>
			<td>${gender}</td>
		</tr>
		<tr>
			<td><b>이메일</b></td>
			<c:if test="${memberInfo.email eq null}">
				<td>등록된 이메일이 없습니다.</td>
			</c:if>
			<td>${memberInfo.email}</td>
		</tr>
		<tr>
			<td colspan="3">
				<p>
					<button onclick="window.location='${pageContext.request.contextPath}/editForm.do'">수 정</button>
					<button onclick="window.location='${pageContext.request.contextPath}/removeForm.do'">탈 퇴</button>
					<button onclick="window.location='${pageContext.request.contextPath}/'">나가기</button>
				</p>
			</td>
		</tr>
	</table>
</body>
</html>