<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendar</title>
<!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container pt-5">
	<div class="text-center">
		<h2>
			<a href="/cash/calendar?targetYear=${targetYear}&targetMonth=${targetMonth-1}">이전달</a>
			${targetYear}년 ${targetMonth+1}월
			<a href="/cash/calendar?targetYear=${targetYear}&targetMonth=${targetMonth+1}">다음달</a>
		</h2>
	</div>
	<div><!-- 달력 -->
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>SUN</th>
					<th>MON</th>
					<th>TUE</th>
					<th>WED</th>
					<th>THU</th>
					<th>FRI</th>
					<th>SAT</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<!-- td반복 -->
					<c:forEach var="i" begin="0" end="${totalTd -1}" step="1">
						<!-- 행바꿈 -->
						<c:if test="${i % 7 == 0 && i != 0}">
							</tr><tr>
						</c:if>
						
						<!-- 날짜 출력 -->
						<c:if test="${i >= beginBlank && i < beginBlank+lastDate}">
							<td class="td">
								${i - beginBlank + 1}
								
								<!-- 가계부 출력 -->
								<c:forEach var="m" items="${list}">
									<c:if test="${(i - beginBlank + 1) == fn:substring(m.cashbookDate, 8, 10)}">
										<div>
											<c:if test="${m.category == '지출' }">
												<span style="color:red">-${m.price}</span>
											</c:if>
											<c:if test="${m.category == '수입' }">
												<span style="color:green">+${m.price}</span>
											</c:if>
										</div>
									</c:if>
								</c:forEach>
							</td>
						</c:if>
						
						<!-- 날짜가 없는 칸 -->
						<c:if test="${!(i >= beginBlank && i < beginBlank+lastDate)}">
							<td>&nbsp;</td>
						</c:if>			
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>