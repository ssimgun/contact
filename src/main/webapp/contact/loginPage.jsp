<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>연락처</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">

<style>
/* 배경에 사진넣기 */
body, h1 {font-family: "Raleway", sans-serif}
body, html {height: 100%}
.bgimg {
	background-image: url('${pageContext.request.contextPath}/img/bg.jpg');
	min-height: 100%;
	background-position: center;
	background-size: cover;
}
</style>
</head>
<body>
	<div class="bgimg w3-display-container w3-animate-opacity w3-text-white">
		<!-- 로그인 페이지 -->
		<div id="loginPage">
			<div class="w3-display-topleft w3-padding-large w3-xlarge">Contact</div>
			<div class="w3-display-middle">
				<c:if test="${error != null}">
					<div class="alert alert-danger alert-dismissible fade show mt-3">
						에러 발생: ${error}
						<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
					</div>
				</c:if>
				<p class="w3-large w3-center">
					<form method="post" action="/contact/contactAPP?action=login">
						<div class="mb-3">
							<label for="inputID" class="form-label">ID</label>
							<input type="text" name="user_id" class="form-control" id="inputID" required autofocus>
						</div>
						<div class="mb-3">
						    <label for="inputPW" class="form-label">PassWord</label>
						    <input type="password" name="pw" class="form-control" id="inputPW" required>
						</div>
						<div class="w3-right">
							<button style="padding: 5px 20px;" type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Register</button>
						 	<button style="padding:5px 30px;" id="btnLogin" type="submit" onclick="login()" class="btn btn-primary">Login</button>
						</div>
					</form>
				</p>
			</div>
		</div>
	</div>
	
	<!-- 회원가입 모달 -->
	<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
		   <form method="post" action="/contact/contactAPP?action=register" onsubmit="return passwordCheck()">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="staticBackdropLabel">Register</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      <div class="modal-body">
		      <div class="form-floating mb-3">
				  <input type="text" name="name" class="form-control" id="name" placeholder="이름" required autofocus>
				  <label>Name</label>
				</div>
		        <div class="form-floating mb-3">
				  <input type="text" name="user_id" class="form-control" id="user_id" placeholder="아이디 입력" required>
				  <label>ID</label>
				</div>
				<div class="form-floating mb-3">
				  <input type="password" name="pw" class="form-control" id="pw" placeholder="비밀번호 입력" required>
				  <label>PassWord</label>
				</div> 
				<div class="form-floating">
				  <input type="password" name="confpw" class="form-control" id="confpw" placeholder="비밀번호 확인" required>
				  <label>Confirm PassWord</label>
				</div> 
		      </div>
		      <div class="modal-footer">
	        	<button type="submit" class="btn btn-primary">Register</button>
		      </div>
		    </div>
		   </form>
	  </div>
	</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/loginPage.js"></script> <!-- JavaScript 파일 로드 -->
</body>
</html>
