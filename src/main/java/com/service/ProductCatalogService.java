package com.service;

import java.util.List;

import com.bean.ProductFilter;

public interface ProductCatalogService {
	 public List<ProductFilter> getAllFilters();
	 public List<ProductFilter> getAllProducts(String filter);
	 public List<ProductFilter> saveSettings();
}
