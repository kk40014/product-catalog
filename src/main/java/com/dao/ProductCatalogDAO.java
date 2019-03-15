package com.dao;

import java.util.List;

import com.bean.ProductCategory;
import com.bean.ProductFilter;
import com.bean.ProductInformation;

public interface ProductCatalogDAO{
	 public List<ProductFilter> getAllFilters();
	 public List<ProductFilter> saveSettings();
	List<ProductCategory> getFilterCategory();
	List<ProductInformation> getAllProducts(String[] filter);
}
