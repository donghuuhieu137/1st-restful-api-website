package com.itptit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_cart")
public class Cart extends BaseEntity{

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "cart")
	private List<ProductInCart> productInCart = new ArrayList<ProductInCart>();

	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;

	@Column(name = "totalPrice")
	private Long totalPrice;

	public List<ProductInCart> getProductInCart() {
		return productInCart;
	}

	public void setProductInCart(List<ProductInCart> productInCart) {
		this.productInCart = productInCart;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
}
