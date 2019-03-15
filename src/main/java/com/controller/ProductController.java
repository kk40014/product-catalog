package com.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ProductCategory;
import com.bean.ProductInformation;
import com.service.ProductCatalogServiceImpl;

@RestController 
public class ProductController {
	 @Autowired
	 private ProductCatalogServiceImpl productService;
	 
	 private static Map<String,String> systemMenuMessage = UserLoginController.systemMessage;
	 
	 @RequestMapping(value = "/getProductList" , method = { RequestMethod.GET})  
	 @ResponseBody
	 //, @RequestParam("productIDs") String productIDs	
     public  List<ProductInformation> getProductList(Model model, @RequestParam(value="selectedFilter[]", defaultValue="") String[] selectedFilter){
		 List<ProductInformation> productList = productService.getAllProducts(selectedFilter);
		 return productList;  
     }
	 
	 @RequestMapping(value = "/getFilterCategory" , method = { RequestMethod.GET})  
	 @ResponseBody
	 //, @RequestParam("productIDs") String productIDs	
     public  List<ProductCategory> getFilterCategory(Model model){
		 List<ProductCategory> filterCategoryList = productService.getFilterCategory();
		 return filterCategoryList;  
     }
	 
	 @RequestMapping(value = "/getSortByProduct" , method = { RequestMethod.GET})  
	 @ResponseBody
	 //, @RequestParam("productIDs") String productIDs	
     public  List<ProductInformation> getSortByProduct(Model model, @RequestParam("sortBy") String sortBy, @RequestParam(value="selectedFilter[]", defaultValue="") String[] selectedFilter){
		 List<ProductInformation> productList = productService.getSortByProduct(sortBy ,selectedFilter);
		 return productList;  
     }
	 
	 @RequestMapping(value = "/getSearchResult" , method = { RequestMethod.GET})  
	 @ResponseBody
	 //, @RequestParam("productIDs") String productIDs	
     public  List<ProductInformation> getSearchResult(Model model, @RequestParam("sortBy") String sortBy, @RequestParam(value="selectedFilter[]", defaultValue="") String[] selectedFilter,
    		 @RequestParam(value = "keyword") String keyword){
		 System.out.println(selectedFilter);
		 List<ProductInformation> productList = productService.getSearchResult(sortBy ,selectedFilter, keyword);
		 return productList;  
     }
}
