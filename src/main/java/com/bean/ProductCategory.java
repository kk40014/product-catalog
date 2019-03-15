package com.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Product_Category_Info")
public class ProductCategory {
	@Id
	@Column(name="categoryid")
	int categoryid;
	String categoryname;
	@Transient
	List<ProductFilter> filters;
	
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public List<ProductFilter> getFilters() {
		return filters;
	}
	public void setFilters(List<ProductFilter> filters) {
		this.filters = filters;
	}
}
