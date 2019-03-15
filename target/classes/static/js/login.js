$( document ).ready(function() {
	validateUserRequest();
	$("#login").click(function(){
		
		var name = $("#loginUser").val();
		var password = $("#loginPwd").val();
		var count = formcheck("loginForm");
		if(count == false){
			$("#alert").text("Please enter highlighted fields");
    		$("#alert").prop("hidden",false);
			alert("Please enter highlighted fields")
			return false;
		}
		$("#alert").prop("hidden",true);
		$.ajax({
		    type: "GET",
		    url: "/product-catalog/login",
		    // The key needs to match your method's input parameter (case-sensitive).
		    data: { userName: name, pwd: password},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){
		    	console.log(data)
		    	if(data.statusCode == 200 ){
		    		sessionStorage.setItem("user",name);
		    		sessionStorage.setItem("pwd",password);
		    		sessionStorage.setItem("userMenu",JSON.stringify(data.userinfo.menu));
		    		location.replace("home.html")
		    	}else{
		    		$("#alert").text(data.statusMessage)
		    		$("#alert").prop("hidden",false)
		    	}
		    },
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
	
		/*$.get( "/product-catalog/login", ,function() {
			  alert( "success" );
			});*/
	});
	
	$("#register").click(function(){
		
		var name = $("#regUser").val();
		var password = $("#regPwd").val();
		var rpassword = $("#re-password").val();
		
		var count = formcheck("registerForm");
		if(count == false){
			alert("Please enter highlighted fields")
			return false;
		}
		
		if(password != rpassword){
			alert("Re-entered password doesnot matched. Please re-enter again");
			$("#re-password").val("");
			return false;
		}
		$.ajax({
		    type: "GET",
		    url: "/product-catalog/register",
		    // The key needs to match your method's input parameter (case-sensitive).
		    data: { userName: name, pwd: password},
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){
		    	console.log(data)
		    	if(data.statusCode == 200 ){
		    		sessionStorage.setItem("user",name);
		    		sessionStorage.setItem("pwd",password);
		    		sessionStorage.setItem("userMenu",JSON.stringify(data.userinfo.menu));
		    		location.replace("home.html")
		    	}else{
		    		$("#alert").text(data.statusMessage)
		    		$("#alert").prop("hidden",false)
		    	}
		    },
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
	
		/*$.get( "/product-catalog/login", ,function() {
			  alert( "success" );
			});*/
	});
	
	function formcheck(formName) {
		  var fields = $("#"+formName+" .item-required");
		  
		  var count = 0;
		  $.each(fields, function(i, field) {
		    if (field.value == ""){
		    	count++;
		    	$("#"+field.id).addClass("required-css");
		    }else{
		    	$("#"+field.id).removeClass("required-css");
		    }
		   }); 
		  return count == 0 ? true : false;
		}
});

function validateUserRequest(){
	userName = sessionStorage.getItem("user");
	if(userName != null && userName != ""){
		alert("You will be logged out");
		logoutUser();
	}
}

function logoutUser(){
	$.ajax({
	    type: "GET",
	    url: "/product-catalog/logout",
	    // The key needs to match your method's input parameter (case-sensitive).,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data){
	    	if(data.statusCode == 200 ){
	    		sessionStorage.clear();
	    		location.replace("login.html");
	    	}
	    },
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
}