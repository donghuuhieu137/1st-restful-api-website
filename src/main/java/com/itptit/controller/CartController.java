package com.itptit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itptit.entities.Product;
import com.itptit.entities.ProductInCart;
import com.itptit.entities.SaleOrder;

import com.itptit.respositories.CartRepo;
import com.itptit.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.itptit.entities.Cart;
import com.itptit.model.respone.AjaxResponse;
import com.itptit.respositories.ProductRepo;
//import com.itptit.services.SaleOrderService;
import com.itptit.services.SaleOrderService;

@RestController 	// -> tạo ra 1 bean tên webConf và được spring-container quản lí.
			// -> báo module web mapping request cho controller này.
@RequestMapping("/cart")
public class CartController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired private CartRepo cartRepo;

	@Autowired private CartService cartService;

//	@RequestMapping(method = RequestMethod.POST)
//	ResponseEntity<?> add(@RequestBody Cart cart, UriComponentsBuilder ucBuilder){
//		ShoppingCart result = this.shoppingCartRepository.save(
//				new ShoppingCart(
//						ShoppingCart.PENDING,
//						input.userName,
//						input.products,
//						input.productQuantities,
//						input.orderDate,
//						input.lastModified,
//						input.totalPrice
//				));
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(ucBuilder.path("/carts/{id}").buildAndExpand(result.getId()).toUri());
//		return new ResponseEntity<ShoppingCart>(result, headers, HttpStatus.CREATED);
//	}

	@GetMapping (value = { "" })
	public ResponseEntity<List<Cart>> findAllCart() throws Exception {
		List<Cart> carts = cartRepo.findAll();
		return new ResponseEntity<>(carts, HttpStatus.OK);
	}
	
	@GetMapping(value = { "/user/{username}" })
	public ResponseEntity<Cart> findCartByUserName(@PathVariable("username") String username)
	throws Exception{
		Cart cart = cartService.findByUserName(username);
		if (cart == null){
			logger.error("Did not find any cart");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<Cart> findCartById(@PathVariable("id") Integer id) throws Exception {
		Cart cart = cartRepo.findById(id).get();
		if (cart == null){
			logger.error("Did not find any cart");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@PostMapping(value = "/{cartId}/product/{productId}")
	public ResponseEntity<?> addProductToCart(@PathVariable("cartId") Integer cartId, @RequestBody ProductInCart productInCart) throws Exception {
		cartService.addProductToCart(cartId,productInCart);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{cartId}/product/{productId}")
	public ResponseEntity<?> removeProductFromCart(@PathVariable("cartId") Integer cartId, @RequestBody ProductInCart productInCart) throws Exception {
		cartService.removeProductFromCart(cartId,productInCart);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/{cartId}/product/{productId}")
	public ResponseEntity<?> updateProductFromCart(@PathVariable("cartId") Integer cartId, @RequestBody ProductInCart productInCart) throws Exception {
		cartService.updateProductFromCart(cartId,productInCart);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = { "/checkout" }, method = RequestMethod.GET)
	public String checkOut(final ModelMap model, final HttpServletRequest request, final HttpServletResponse respone) 
	throws Exception {
		model.addAttribute("saleOrder", new SaleOrder());
		return "front-end/checkout";
	}

	@RequestMapping(value = { "/checkout" }, method = RequestMethod.POST)
	public String Order(@ModelAttribute SaleOrder saleOrder,
			final ModelMap model, final HttpServletRequest request)
	throws Exception {

		return "front-end/checkout?success=true";
	}

}
