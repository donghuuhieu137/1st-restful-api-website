package com.itptit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "tbl_product_cart")
public class ProductInCart extends BaseEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "quality")
	private Integer quality;

	@Column(name = "price")
	private Integer price;

	@Column(name = "color")
	private String color;

	@Column(name = "size")
	private String size;

	@Column(name = "totalPrice")
	private Integer totalPrice;


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Cart cart;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public Integer getPrice() {
		return price;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
