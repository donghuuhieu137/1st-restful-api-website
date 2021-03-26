package com.itptit.services;

import com.itptit.controller.CartController;
import com.itptit.entities.Cart;
import com.itptit.entities.ProductInCart;
import com.itptit.respositories.CartRepo;
import com.itptit.respositories.ProductInCartRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductInCartRepo productInCartRepo;

    public Cart findByUserName(String username){
        List<Cart> carts = cartRepo.findAll();
        for (Cart i: carts) {
            if(i.getUser().getUserName().equals(username))
                return i;
        }
        return null;
    }

    public void addProductToCart(Integer cartId ,ProductInCart productInCart){
        try {
            productInCart.setTotalPrice(productInCart.getPrice()*productInCart.getQuality());
            cartRepo.findById(cartId).get().getProductInCart().add(productInCart);
        }catch (Exception e){
            logger.error("Can not add product to cart");
        }
    }

    public void removeProductFromCart(Integer cartId ,ProductInCart productInCart){
        try {
            cartRepo.findById(cartId).get().getProductInCart().remove(productInCart);
        }catch (Exception e){
            logger.error("Can not remove product from cart");
        }

    }

    public void updateProductFromCart(Integer cartId ,ProductInCart productInCart){
        try {
            productInCartRepo.save(productInCart);
        }catch (Exception e){
            logger.error("Can not update product from cart");
        }

    }
}
