<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>연락처</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Raleway">

<style>
/* 배경에 사진넣기 */
body, h1 {
	font-family: "Raleway", sans-serif
}

body, html {
	height: 100%
}

.bgimg {
	background-image: url('${pageContext.request.contextPath}/img/bg.jpg');
	min-height: 100%;
	background-position: center;
	background-size: cover;
}
</style>
</head>
<body>
	<div
		class="bgimg w3-display-container w3-animate-opacity w3-text-white">
		<div id="mainPage">
			<!--네비게이션 영역-->
			<nav class="navbar fixed-top bg-body-tertiary">
				<div class="container-fluid">
					<a class="navbar-brand ms-3">Contact List</a>
					<form method="post" class="d-flex" role="search"
						action="/contact/contactAPP?action=search">
						<button type="button"
							class="btn btn-outline-primary bi bi-person-plus-fill me-2"
							data-bs-toggle="modal" data-bs-target="#addMemberImgModal">
						</button>
						<input class="form-control me-2" type="search" name="name"
							placeholder="Search" aria-label="Search">
						<button class="btn btn-outline-success" type="submit">Search</button>
					</form>
					<div>
						<span class="navbar-brand fs-5">${userName}님 반갑습니다!</span> <a
							type="button" class="btn btn-outline-danger me-3"
							href="http://localhost:9091/contact/contactAPP?action=logout">Logout</a>
					</div>
				</div>
			</nav>
			<!--연락처 목록 영역-->
			<div class="w3-container w3-padding-64 w3-center" id="member"
				style="border: 1;">
				<div id="contactList" class="w3-row">
					<br>
				 	<c:if test="${error != null}">
						<div class="alert alert-danger alert-dismissible fade show mt-3">
							에러 발생: ${error}
							<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
						</div>
					</c:if>
					<c:if test="${not empty sessionScope.complete}">
						<div class="alert alert-primary alert-dismissible fade show mt-3">
								${sessionScope.complete}
							<c:remove var="complete" scope="session" />
							<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
						</div>
					</c:if>
					<c:forEach var="c" items="${contactList}">
						<div class="w3-quarter w3-padding-small" id="contactList"
							data-id="${c.id}" data-profile_val="${c.profile_val}"
							data-name="${c.name}" data-phone_num="${c.phone_num}"
							data-address="${c.address}" data-gubun_name="${c.gubun_name}"
							data-memo="${c.memo}">
							<input type="hidden" name="${c.id}"> <img
								src="${c.profile_val}" alt="${c.name}" style="width: 50%;"
								class="w3-circle w3-hover-opacity" data-bs-toggle="modal"
								data-bs-target="#select" onclick="getList(this)">
							<h3>${c.name}</h3>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<!-- 회원추가 모달 -->
	<form method="post" action="/contact/contactAPP?action=insert">
		<div class="modal fade" id="addMemberImgModal"
			data-bs-backdrop="static" data-bs-keyboard="false" aria-hidden="true"
			aria-labelledby="exampleModalToggleLabel" tabindex="-1">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title fs-5">Choose Profile</h1>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div
							class="list-group mx-auto p-2 d-flex flex-column align-items-center">
							<img class="defaultImg img-thumbnail rounded-circle"
								src="${pageContext.request.contextPath}/img/dull.png"
								style="width: 40%;"> <select name="profile_val"
								class="selectImg orm-select form-select-lg text-center rounded-pill"
								style="width: 40%;">
								<option value="${pageContext.request.contextPath}/img/dull.png">둘리</option>
								<option value="${pageContext.request.contextPath}/img/baby.JPG">희동이</option>
								<option value="${pageContext.request.contextPath}/img/ddoc.png">또치</option>
								<option value="${pageContext.request.contextPath}/img/doun.png">도우너</option>
								<option
									value="${pageContext.request.contextPath}/img/gogil.jpeg">고길동</option>
								<option value="${pageContext.request.contextPath}/img/mich.png">마이콜</option>
								<option value="${pageContext.request.contextPath}/img/mong.jpeg">몽실이</option>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-primary" data-bs-target="#addMemberModal"
							data-bs-toggle="modal">Next</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="addMemberModal" data-bs-backdrop="static"
			data-bs-keyboard="false" tabindex="-1"
			aria-labelledby="addMemberModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title fs-5" id="addMemberModalLabel">AddMember</h1>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class="form-floating mb-3">
							<input type="text" name="name" class="form-control"
								id="InputName" placeholder="이름 입력" required> <label
								for="InputName">Name</label>
						</div>
						<div class="form-floating mb-3">
							<input type="text" name="phone_num" class="form-control"
								id="InputPhone" placeholder="전화번호 입력" pattern="^[0-9]+$"
								title="input only number" required> <label
								for="InputPhone">Phone Number</label>
						</div>
						<div class="form-floating mb-3">
							<input type="text" name="address" class="form-control"
								id="InputAddress" placeholder="주소 입력" required> <label
								for="InputAddress">Address</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text" id="basic-addon1">SelectType</span>
							<select class="form-select form-select-lg" name="gubun_name"
								id="SelectType">
								<option value="Family">Family</option>
								<option value="Friend">Friend</option>
								<option value="Company">Company</option>
								<option value="Others">Others</option>
							</select>
						</div>
						<div class="form-floating mb-3">
							<textarea rows="5" class="form-control" id="InputMemo"
								name="memo" placeholder="메모 입력"></textarea>
							<label for="InputMemo">Memo</label>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-secondary"
							data-bs-target="#addMemberImgModal" data-bs-toggle="modal">Back</button>
						<button id="doneBtn" type="submit" class="btn btn-primary">Done</button>
					</div>
				</div>
			</div>
		</div>
	</form>


	<!-- 상세보기 모달 -->
	<div class="modal fade" id="select" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="selectName">Member Info</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<form method="post"
					action="${pageContext.request.contextPath}/contactAPP">
					<input type="hidden" name="action" id="delete-update">
					<div class="modal-body" id="getContact">
						<div class="d-flex flex-column align-items-center form-control">
							<input type="hidden" id="ListID" name="id"> <img
								id="ListProfile_val"
								class="defaultImg img-thumbnail rounded-circle" src=""
								style="width: 40%;"> <select
								class="selectImg form-select form-select-lg text-center rounded-pill"
								name="profile_val" style="width: 40%;"
								onchange="chooseImg(this)">
								<option value="${pageContext.request.contextPath}/img/dull.png">둘리</option>
								<option value="${pageContext.request.contextPath}/img/baby.JPG">희동이</option>
								<option value="${pageContext.request.contextPath}/img/ddoc.png">또치</option>
								<option value="${pageContext.request.contextPath}/img/doun.png">도우너</option>
								<option
									value="${pageContext.request.contextPath}/img/gogil.jpeg">고길동</option>
								<option value="${pageContext.request.contextPath}/img/mich.png">마이콜</option>
								<option value="${pageContext.request.contextPath}/img/mong.jpeg">몽실이</option>
							</select>
							<div class="input-group mb-3">
								<span class="input-group-text w-25">Name</span> <input
									class="input-group-text w-75" id="ListName" name="name"
									readonly>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text w-25">Number</span> <input
									class="input-group-text w-75" id="ListPhone_num"
									name="phone_num" pattern="^[0-9]+$" title="input only number"
									readonly>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text w-25">Address</span> <input
									class="input-group-text w-75" id="ListAddress" name="address"
									readonly>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text w-25">Type</span> <select
									class="input-group-text w-75" id="ListGubun_name"
									name="gubun_name">
									<option value="Family">Family</option>
									<option value="Friend">Friend</option>
									<option value="Company">Company</option>
									<option value="Others">Others</option>
								</select>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text w-25">Memo</span> <input
									class="input-group-text w-75" id="ListMemo" name="memo"
									readonly>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button id="saveBtn" type="submit" class="btn btn-secondary"
							onclick="saveUpdate()" style="display: none;">Save</button>
						<button id="updateBtn" type="button" class="btn btn-secondary"
							onclick="update()">Update</button>
						<button id="deleteBtn" type="submit" class="btn btn-danger"
							onclick="deleteMemb()">Delete</button>
						<button type="button" class="btn btn-primary"
							data-bs-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
// 이미지 변경
function chooseImg(selectElement) {
    let selectedOption = selectElement.options[selectElement.selectedIndex].value;
    let imgElement = selectElement.closest('.modal-body').querySelector('.defaultImg');
    imgElement.src = selectedOption;
}

// 초기 선택된 이미지 설정
document.querySelectorAll('.selectImg').forEach(select => {
    chooseImg(select);
    select.addEventListener('change', function() {
        chooseImg(this);
    });
});
	
</script>	
<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
		crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/mainPage.js"></script> <!-- JavaScript 파일 로드 -->
</body>
</html>
