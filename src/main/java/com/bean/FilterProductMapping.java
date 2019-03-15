package com.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Product_Filter_Mapping")
public class FilterProductMapping {
	@Id
	@Column(name="id")
	int id;
	int filterid;
	int productid;
	
	public int getFilterid() {
		return filterid;
	}
	public void setFilterid(int filterid) {
		this.filterid = filterid;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
}
