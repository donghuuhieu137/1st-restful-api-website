package com.itptit.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itptit.controller.BaseController;
import com.itptit.entities.Product;
import com.itptit.respositories.ProductRepo;
import com.itptit.services.ProductsService;

@RestController
public class AdminProductController extends BaseController{
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private ProductRepo productRepo;
	
	@RequestMapping(value = "/admin/product", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getAllProduct()throws Exception{
		List<Product> products = productRepo.findAll();
		if(products.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/product-add", method = RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@RequestParam("product_images") MultipartFile[] productImages, @RequestBody Product product)
		throws Exception{
		productsService.save(productImages,product);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}
	
	
	/////////////////// D E L E T E
	@RequestMapping(value = { "/admin/product-delete/{id}" }, method = RequestMethod.POST)
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Integer id) {
			productsService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//////// EDIT
	@RequestMapping(value = "/admin/dashboard-products-edit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Product> editAccount(@PathVariable("id") Integer id, @RequestParam("product_images") MultipartFile[] productImages, @RequestBody Product product)
		throws Exception{
		productsService.save(productImages,product);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
