function registCheck(){
	if(document.registForm.id.value == ""){
		alert("아이디를 입력하세요.");
		document.registForm.id.focus();
		return false;
	}
	if(document.registForm.password.value == ""){
		alert("비밀번호를 입력하세요.");
		document.registForm.password.focus();
		return false;
	}
	if(document.registForm.name.value == ""){
		alert("이름을 입력하세요.");
		document.registForm.name.focus();
		return false;
	}
	if(document.registForm.year.value == ""){
		alert("년도를 입력하세요.");
		document.registForm.year.focus();
		return false;
	}
	if(document.registForm.gender.value == ""){
		alert("성별을 선택하세요.");
		document.registForm.gender.focus();
		return false;
	}
	
	if(document.registForm.password.value != document.registForm.repassword.value){
		alert("비밀번호가 일치하지 않습니다.");
		document.registForm.repassword.focus();
		return false;
	}
	if(!(document.registForm.month.value <= 12 && document.registForm.month.value >= 1)){
		alert("1월 ~ 12월로 입력하세요");
		document.registForm.month.focus();
		return false;
	}
	if(!(document.registForm.day.value <= 31 && document.registForm.day.value >= 1)){
		alert("1일 ~ 31일로 입력하세요.");
		document.registForm.day.focus();
		return false;
	}
}

(function() {
	  var httpRequest;
	  document.getElementById("idCheck").addEventListener('click', makeRequest);
	  function makeRequest() {
	    httpRequest = new XMLHttpRequest();

	    if(!httpRequest) {
	      alert('XMLHTTP 오류');
	      return false;
	    }
	    httpRequest.onreadystatechange = alertContents;
	    
	    //POST로 요청
	    httpRequest.open('POST', "idCheck");
	    httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	    httpRequest.send('id=' + encodeURIComponent(document.getElementById("id").value));
	  }
	  
	  function alertContents() {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
	      if (httpRequest.status === 200) {
	    	document.getElementById("idCheckResult").innerHTML = httpRequest.responseText;
	      } else {
	        alert('request 오류');
	      }
	    }
	  }
	})();