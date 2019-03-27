package com.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.ProductCategory;
import com.bean.ProductFilter;
import com.bean.ProductInformation;
import com.dao.ProductCatalogDAOImpl;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

	@Autowired
	private ProductCatalogDAOImpl productDAO;
	
	@Override
	@Transactional
    public List<ProductInformation> getAllProducts(String[] filterIds) {
		return productDAO.getAllProducts(filterIds);
	}
	
	
	@Override
	@Transactional
    public List<ProductCategory> getFilterCategory() {
		return productDAO.getFilterCategory();
	}
	
	@Override
	@Transactional
    public List<ProductInformation> getSortByProduct(String sortBy, String[] selectedFilter) {
		List<ProductInformation> productList = productDAO.getAllProducts(selectedFilter); 			
		if(sortBy.equals("lower_price")) {
			//Collections.sort(productList, ProductInformation.SortPriceAsc);
			productList.sort((ProductInformation p1, ProductInformation p2) ->p1.getPrice() - p2.getPrice());
		}
		else if(sortBy.equals("higher_price")) {
			//Collections.sort(productList, ProductInformation.SortPriceDesc);
			productList.sort((ProductInformation p1, ProductInformation p2) -> p2.getPrice() - p1.getPrice());
		}
		else if(sortBy.equals("alpha")) {
			//Collections.sort(productList, ProductInformation.SortAlbhabetical);
			productList.sort((ProductInformation p1, ProductInformation p2) -> p1.getProductname().compareToIgnoreCase(p2.getProductname()));
			
		}else {
			return productList;
		}
			
		return productList;
	}
	
	@Override
	@Transactional
    public List<ProductInformation> getSearchResult(String sortBy, String[] selectedFilter, String keyword) {
		List<ProductInformation> sortedList = getSortByProduct(sortBy,selectedFilter);
		if(keyword.equals(""))
			return sortedList;
		
		List<ProductInformation> productList = sortedList.stream().
												filter(p -> p.getProductname().contains(keyword)).collect(Collectors.toList());;
			
		return productList;
	}

	@Override
	public List<ProductFilter> saveSettings() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

