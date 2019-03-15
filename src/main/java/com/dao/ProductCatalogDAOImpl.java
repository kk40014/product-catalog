package com.dao;

import java.util.* ;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bean.ProductCategory;
import com.bean.ProductFilter;
import com.bean.ProductInformation;
@Transactional
@Repository

public class ProductCatalogDAOImpl implements ProductCatalogDAO{
	@PersistenceContext	
	private EntityManager entityManager; 
	
	 @SuppressWarnings("unchecked")
	 public List<ProductFilter> getAllFilters() {
		 List<ProductFilter> filterList = entityManager.createQuery("from ProductFilter")
					.getResultList();
		 return filterList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInformation> getAllProducts(String[] filterArr) {
		
		StringBuilder query =  new StringBuilder("from ProductInformation where id in("
				+ "select distinct p.id from ProductInformation p , FilterProductMapping f where f.productid = p.id ");
		List<ProductInformation> productList;
		List<Integer> filterId = new ArrayList<Integer>() ;
		
		if(filterArr.length > 0) {
			for(String s : filterArr) {
				filterId.add(Integer.parseInt(s));
			}
			query.append("and f.filterid IN :ids)");
			productList = entityManager.createQuery(query.toString())
					.setParameter("ids", filterId)
					.getResultList();
		}else {
			query.append(")");
			productList = entityManager.createQuery(query.toString())
					.getResultList();
		}
		return productList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> getFilterCategory() {
		
	    List<ProductCategory> categoryList = entityManager.createQuery("from ProductCategory where status = :status")
	    		.setParameter("status","active")
				.getResultList();
	    for(ProductCategory pc: categoryList) {
	    	List<ProductFilter> filterList = entityManager.createQuery("from ProductFilter where status = :status and category_id = :category_id")
		    		.setParameter("status","active")
		    		.setParameter("category_id",pc.getCategoryid())
					.getResultList();
	    	pc.setFilters(filterList);
	    	
	    }
	    return categoryList;
	}
	
	
	@Override
	public List<ProductFilter> saveSettings() {
		// TODO Auto-generated method stub
		return null;
	}
}
