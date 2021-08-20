<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
<h1>게시판</h1>
<p>
<button onclick="window.location='${pageContext.request.contextPath}/write.do'">글쓰기</button>
<button onclick="window.location='${pageContext.request.contextPath}/'">메인</button>
</p>
	<c:if test="${count == 0}">
		<table>
			<tr>
				<td>
					게시판에 저장된 글이 없습니다.
				</td>
			</tr>	
		</table>
	</c:if>	
	
	<c:if test="${count > 0}">
		<table>
			<tr>
				<th>번 호</th>
				<th>제 목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
			<c:forEach var="postList" items="${postList}">
			<tr>
				<td>
					<c:out value="${number}"></c:out>
					<c:set var="number" value="${number - 1}"></c:set>
				</td>
				<td>
					<c:if test="${postList.depth > 0}">
						<c:forEach begin="1" end="${postList.depth}">
							&nbsp;&nbsp;
						</c:forEach>
					</c:if>
					<a href="${pageContext.request.contextPath}/content.do?num=${postList.num}&pageNum=${currentPage}">
						${postList.subject}</a>
				</td>
				<td>${postList.writer}</td>
				<fmt:formatDate var="formatRegDate" value="${postList.regdate}" pattern="yyyy.MM.dd"/>
				<td>${formatRegDate}</td>
				<td>${postList.readcount}</td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${count > 0}">
		<c:set var="imsi" value="${count % pageSize == 0? 0 : 1}" />
		<c:set var="pageCount" value="${count / pageSize + imsi}" />
		<fmt:parseNumber var="pageCount" value="${pageCount}" integerOnly="true"/>	
		
		<c:set var="pageBlock" value="${5}"/>
		
		<fmt:parseNumber var="result" value="${(currentPage - 1) / pageBlock }" integerOnly="true" />
		
		<c:set var="startPage" value="${result * pageBlock + 1}"/>
		<c:set var="endPage" value="${startPage + pageBlock - 1}"/>
		
		<c:if test="${endPage > pageCount}">
			<c:set var="endPage" value="${pageCount}"/>
		</c:if>
			<c:if test="${startPage > pageBlock}">
			<a href="${pageContext.request.contextPath}/list.do?pageNum=${startPage - pageBlock}">이전</a>
		</c:if>
		
		<c:forEach var="i" begin="${startPage}" end="${endPage}">
			<a href="${pageContext.request.contextPath}/list.do?pageNum=${i}">[${i}]</a>
		</c:forEach>
		
		<c:if test="${endPage < pageCount}">
			<a href="${pageContext.request.contextPath}/list.do?pageNum=${startPage + pageBlock}">다음</a>
		</c:if>
	</c:if>
</body>
</html>