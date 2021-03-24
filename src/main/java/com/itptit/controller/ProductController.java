package com.itptit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itptit.entities.Product;
import com.itptit.model.search.ProductSearch;
import com.itptit.services.ProductsService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends BaseController{
	
	@Autowired
	private ProductsService productsService;
	
	@RequestMapping(value = { "/product/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id)
		throws Exception {
		ProductSearch productSearch = new ProductSearch();
		productSearch.setId(id);
		return new ResponseEntity<>(productsService.search(productSearch).get(0), HttpStatus.OK);
	}
	
	
}
