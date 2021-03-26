package com.itptit.respositories;

import com.itptit.entities.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInCartRepo extends JpaRepository<ProductInCart, Integer> {

}
