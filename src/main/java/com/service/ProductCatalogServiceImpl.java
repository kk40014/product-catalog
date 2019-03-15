package com.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.ProductCategory;
import com.bean.ProductInformation;
import com.dao.ProductCatalogDAOImpl;

@Service
public class ProductCatalogServiceImpl {

	@Autowired
	private ProductCatalogDAOImpl productDAO;
	
	@Transactional
    public List<ProductInformation> getAllProducts(String[] filterIds) {
		return productDAO.getAllProducts(filterIds);
	}
	
	@Transactional
    public List<ProductCategory> getFilterCategory() {
		return productDAO.getFilterCategory();
	}
	
	@Transactional
    public List<ProductInformation> getSortByProduct(String sortBy, String[] selectedFilter) {
		List<ProductInformation> productList = productDAO.getAllProducts(selectedFilter); 			
		if(sortBy.equals("lower_price")) {
			Collections.sort(productList, ProductInformation.SortPriceAsc);
		}
		else if(sortBy.equals("higher_price")) {
			Collections.sort(productList, ProductInformation.SortPriceDesc);
		}
		else if(sortBy.equals("alpha")) {
			Collections.sort(productList, ProductInformation.SortAlbhabetical);
		}else {
			return productList;
		}
			
		return productList;
	}
	
	@Transactional
    public List<ProductInformation> getSearchResult(String sortBy, String[] selectedFilter, String keyword) {
		List<ProductInformation> sortedList = getSortByProduct(sortBy,selectedFilter);
		if(keyword.equals(""))
			return sortedList;
		
		List<ProductInformation> productList = sortedList.stream().
												filter(p -> p.getProductname().contains(keyword)).collect(Collectors.toList());;
			
		return productList;
	}
	
}

