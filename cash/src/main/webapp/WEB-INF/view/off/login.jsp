<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JJACK JJACK : CASHBOOK</title>
<c:import url="/inc/head.jsp"></c:import>
</head>
<body>
	<header>
		<c:import url="/inc/header.jsp"></c:import>
	</header>
	<div class="container">
		<form action="${pageContext.request.contextPath}/off/login" method="post">
		<div>
			<div>
				<label for="memberId">아이디</label>
			</div>
			<div>
				<input type="text" name="memberId" id="memberId" value="${loginId}" placeholder="아이디" required>
			</div>
			<div>
				<label for="pw">비밀번호</label>
			</div>
			<div>
				<input type="password" name="memberPw" id="memberPw" placeholder="비밀번호" required>
			</div>
		</div>
		<div>
			<label for="idCookie">아이디저장</label>
			<input type="radio" id="idCookie" class="form" name="idCookie" value="y">
		</div>
		<div>
			<button class="btn btn-primary" type="submit">로그인</button>
		</div>
		</form>
	</div>
	<footer>
	</footer>
</body>
</html>