
function passwordCheck() {
	var pw = document.getElementById("pw").value;
	var confpw = document.getElementById("confpw").value;
	      
	if (pw !== confpw) {
		alert("Passwords do not match.");
		return false;
	} else{
		return true;
	}
	    
}
	
	
	
