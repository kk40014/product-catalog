$( document ).ready(function() {
	var userName;
	var allVals = [];
	validUserSession();
	loadMenu();
	loadProducts(allVals);
	loadFilters();
	var pagePath ;
	var selectedVal = "relevance";
	$("#logOut").click(function(){
		logoutUser();
	});
	
	$("#search").click(function(){
		var keyword = $("#searchBox").val();
		searchProduct(keyword,selectedVal);
	});
	
	$("#sort_by").change(function() {
		 selectedVal = $(this).find(':selected').val();
		sortProduct(selectedVal);
	});

});

function loadMenu(){
	var menuList  = sessionStorage.getItem("userMenu");
	pagePath = window.location.pathname;
	$("#userName").text(userName)
	var menuHtml ="";
	$.each(JSON.parse(menuList), function(i,menu) {
		if(pagePath.indexOf(menu.menuurl) >= 0){
			menuHtml +=  "<li class = 'menuList current'>";
		}else{
			menuHtml +=  "<li class = 'menuList'>";
		}
		menuHtml +=  "<a href = '"+menu.menuurl+"'class = 'menuItem'>"+menu.menuname+"</a></li>";
	});
	
	$("#menu").append(menuHtml);
}

function loadProducts(allVals){
	console.log(allVals);
	$.ajax({
	    type: "GET",
	    url: "/product-catalog/getProductList",
	    // The key needs to match your method's input parameter (case-sensitive).,
	    data : {selectedFilter : allVals},
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data){
	    	renderProduct(data);
	    },
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
}

function getSelectedFilters(){
	  var allVals = [];
	     $('#filterContainner :checked').each(function() {
	       allVals.push($(this).val());
	     });
	     loadProducts(allVals);
}

function loadFilters(){
	$.ajax({
	    type: "GET",
	    url: "/product-catalog/getFilterCategory",
	    // The key needs to match your method's input parameter (case-sensitive).,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data){
	    	var categoryList = data;
	    	var filterHtml = "";
	    	$.each(categoryList, function(i,category){
	    		filterHtml += "<button class='accordion' id = '"+category.categoryid+"'>"+category.categoryname+"</button>"
	    					+ "<div class='panel'>"
	    					+ "<ul class = 'filterList'>";
	    		$.each(category.filters, function(i,filter){
	    			filterHtml +=  "<li><input type='checkbox' onclick = 'getSelectedFilters()' value = '"+filter.id+"'>"+filter.filtername+"</li>"
	    		});
	    		filterHtml += "</ul> </div>";
	    	});
	    	
	    	$("#filterContainner").append(filterHtml);
	    	 accordionEvent();
	    },
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
}

function renderProduct(data){
	var productList = data;
	var productHtml = "";
	$("#productList").empty();
	$.each(productList, function(i,product) {
		if(product.imageurl == " ")
			product.imageurl = "images/default-image.jpg";
		productHtml += "<li  class = 'productList'>"
			+"<div class = 'media'>"
			+"<img src='"+product.imageurl+"' style='width: 89%;height: 137%;'>" 
			+"</div>"
			+"<a class='productTitle' href ='"+product.producturl+"'>"+product.productname+"</a>"
			+"<div class = 'content'>Starting at:USD($) "+product.price+"</div>";
	});
	
	$('#productList').append(productHtml);
}

function accordionEvent(){
	var acc = document.getElementsByClassName("accordion");
	var i;
	for (i = 0; i < acc.length; i++) {
	  acc[i].addEventListener("click", function() {
	    this.classList.toggle("active");
	    var panel = this.nextElementSibling;
	    if (panel.style.maxHeight){
	      panel.style.maxHeight = null;
	    } else {
	      panel.style.maxHeight = panel.scrollHeight + "px";
	    } 
	  });
	}
}

function validUserSession(){
	userName = sessionStorage.getItem("user");
	if(userName == null || userName == "")
		validateUser();
}

function validateUser(){
	$.ajax({
	    type: "GET",
	    url: "/product-catalog/validateUser",
	    // The key needs to match your method's input parameter (case-sensitive).,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data){
	    	var productList = data;
	    	if(data.statusCode == 200 ){
	    		sessionStorage.setItem("user",data.userinfo.username);
	    		sessionStorage.setItem("pwd",data.userinfo.pwd);
	    		sessionStorage.setItem("userMenu",JSON.stringify(data.userinfo.menu));
	    	}
	    	if(data.statusCode == 404 ){
	    		sessionStorage.clear();
	    		location.replace("login.html")
	    	}
	    },
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
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

function sortProduct(selectedVal){
	var allVals = [];
    $('#filterContainner :checked').each(function() {
      allVals.push($(this).val());
    });
    
	$.ajax({
	    type: "GET",
	    url: "/product-catalog/getSortByProduct",
	    data: { sortBy : selectedVal, selectedFilter : allVals },
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data){
	    	renderProduct(data);
	    },
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
}

function searchProduct(keyword,selectedVal){
	var allVals = [];
    $('#filterContainner :checked').each(function() {
      allVals.push($(this).val());
    });
	$.ajax({
	    type: "GET",
	    url: "/product-catalog/getSearchResult",
	    data: { sortBy : selectedVal, selectedFilter : allVals, keyword : keyword },
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data){
	    	renderProduct(data);
	    },
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
}

