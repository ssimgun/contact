

function getList(e){
	var closeDiv = e.closest("div");
	
	var id = closeDiv.getAttribute("data-id");
	var profile_val = closeDiv.getAttribute("data-profile_val");
	var name = closeDiv.getAttribute("data-name");
	var phone_num = closeDiv.getAttribute("data-phone_num");
	var address = closeDiv.getAttribute("data-address");
	var gubun_name = closeDiv.getAttribute("data-gubun_name");
	var memo = closeDiv.getAttribute("data-memo");
	
	document.getElementById("ListID").value = id;
	document.getElementById("ListName").value = name;
	document.getElementById("ListPhone_num").value = phone_num;
	document.getElementById("ListAddress").value = address;
	document.getElementById("ListGubun_name").value = gubun_name;
	document.getElementById("ListMemo").value = memo;
	document.getElementById("ListProfile_val").src = profile_val;
}

function update(){
	document.getElementById("saveBtn").style.display = "block";
	document.getElementById("deleteBtn").style.display = "none";
	document.getElementById("updateBtn").style.display = "none";
	
	document.getElementById("ListName").removeAttribute("readonly");
	document.getElementById("ListPhone_num").removeAttribute("readonly");
	document.getElementById("ListAddress").removeAttribute("readonly");
	document.getElementById("ListMemo").removeAttribute("readonly");

}

function saveUpdate(){
	document.getElementById("saveBtn").style.display = "none";
	document.getElementById("deleteBtn").style.display = "block";
	document.getElementById("updateBtn").style.display = "block";
	
	document.getElementById("delete-update").value = 'update';
	
	document.getElementById("ListName").setAttribute("readonly","readonly");
	document.getElementById("ListPhone_num").setAttribute("readonly","readonly");
	document.getElementById("ListAddress").setAttribute("readonly","readonly");
	document.getElementById("ListMemo").setAttribute("readonly","readonly");

}

function deleteMemb(){
	document.getElementById("delete-update").value = 'delete';
}

