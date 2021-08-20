<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% pageContext.setAttribute("replaceChar", "\n"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글</title>
</head>
<body>
	<table>
		<tr>
			<th colspan="4">
				<h3>제목 : ${postVo.subject}</h3>
			</th>
		</tr>
		<tr>
			<td><b>작성자</b> : ${postVo.writer}&nbsp;&nbsp;</td>
			<td><b>조회수</b> : ${postVo.readcount}&nbsp;&nbsp;</td>
			<fmt:formatDate var="formatRegDate" value="${postVo.regdate}" pattern="yyyy.MM.dd"/>
			<td><b>작성일</b> : ${formatRegDate}</td>
		</tr>
		<tr>
			<td colspan="4">
				<p>
				${fn:replace(postVo.content, replaceChar, "<br/>") }
				</p>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				첨부파일
				<c:if test="${uploadVo.fileName != null }">
				<a href="${pageContext.request.contextPath}/download.do?fileName=${uploadVo.fileName}">${uploadVo.fileName}</a>
				</c:if>
				<c:if test="${uploadVo.fileName == null }">
				<i>&nbsp;&nbsp;저장된 파일이 없습니다</i> 
				</c:if>
			</td>
		</tr>		 
		<tr>
			<td colspan="4">
				<button onclick="window.location='${pageContext.request.contextPath}/update.do?num=${postVo.num}&pageNum=${pageNum}'">수 정</button>
				<button onclick="window.location='${pageContext.request.contextPath}/deleteForm.do?num=${postVo.num}&pageNum=${pageNum}&writer=${postVo.writer}'">삭 제</button>
				<button onclick="window.location='${pageContext.request.contextPath}/write.do?num=${postVo.num}&ref=${postVo.ref}&step=${postVo.step}&depth=${postVo.depth}'">답 글</button>
				<button onclick="window.location='${pageContext.request.contextPath}/list.do?pageNum=${pageNum}'">나가기</button>
			</td>
		</tr>
		 
	</table>
</body>
</html>