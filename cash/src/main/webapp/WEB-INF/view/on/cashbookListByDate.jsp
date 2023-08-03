<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/inc/head.jsp"></c:import>
</head>
<body>
	<div class="container">
		<h1>${targetYear}년 ${targetMonth + 1}월 ${targetDate}일</h1>
		<form method="post">
		<input type="hidden" name="targetYear" value="${targetYear}">
		<input type="hidden" name="targetMonth" value="${targetMonth}">
		<input type="hidden" name="targetDate" value="${targetDate}">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>수입/지출</th>
					<th>날짜</th>
					<th>내용</th>
					<th>금액</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${list}">
					<tr id="data-${c.cashbookNo}">
						<td class="category">${c.category}</td>
						<td>${c.cashbookDate}</td>
						<td>${c.memo}</td>
						<td>${c.price}</td>
						<td><a id="modifyBtn-${c.cashbookNo}" class="modifyBtn btn btn-sm btn-primary" href="#">수정</a></td>
						<td><a id="removeBtn" class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/on/removeCashbook?cashbookNo=${c.cashbookNo}&targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">삭제</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
	</div>
</body>
</html>