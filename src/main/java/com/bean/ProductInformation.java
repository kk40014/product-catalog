package com.bean;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Product_Info")
public class ProductInformation {
	@Id
	@Column(name="productid")
	private int id;
	private String productname;
	private int price;
	private String imageurl;
	private String producturl;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getProducturl() {
		return producturl;
	}
	public void setProducturl(String producturl) {
		this.producturl = producturl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	// Lambda expressions 
	public static final Comparator<ProductInformation> SortPriceAsc = (ProductInformation p1, ProductInformation p2) ->p1.getPrice() - p2.getPrice();
	
	public static final Comparator<ProductInformation> SortPriceDesc = (ProductInformation p1, ProductInformation p2) ->p2.getPrice() - p1.getPrice();
	
	public static final Comparator<ProductInformation> SortAlbhabetical = (ProductInformation p1, ProductInformation p2) -> p1.getProductname().compareToIgnoreCase(p2.getProductname());
	
}

