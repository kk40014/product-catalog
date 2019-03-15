package com.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_filter_info")
public class ProductFilter{
	@Id
	@Column(name="filterid")
	private int id;
	private String filtername;
	private int category_id;
	
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="filtername")
	public String getFiltername() {
		return filtername;
	}
	public void setFiltername(String filterName) {
		this.filtername = filterName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getFiltername();
	}
}
