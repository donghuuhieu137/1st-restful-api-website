package com.itptit.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itptit.entities.Category;
import com.itptit.respositories.CategoryRepo;
import com.itptit.services.CategoryService;

import java.util.List;

@RestController
public class AdminCategoryController{
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@RequestMapping(value = "/admin/category", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategory() throws Exception{
		return new ResponseEntity<>(categoryRepo.findAll(), HttpStatus.OK);
	}
	
	//////// ADD
	@RequestMapping(value = "/admin/category-add", method = RequestMethod.POST)
	public ResponseEntity<Category> addCategory(@RequestBody Category category)throws Exception{
		categoryService.save(category);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/////////////////// D E L E T E
	@RequestMapping(value = { "/admin/category-delete/{id}"}, method = RequestMethod.POST)
	public ResponseEntity<Category> deleteCategory(@PathVariable("id") Integer id) throws Exception{
		Category category = categoryRepo.getOne(id);
		if (category==null)
				return new ResponseEntity<>(HttpStatus.CONFLICT);
		categoryService.remove(category);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//////// EDIT
	@RequestMapping(value = "/admin/category/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategoryById( @PathVariable("id") Integer id)
		throws Exception{
		Category category = categoryRepo.findById(id).get();
		return new ResponseEntity<>(category,HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/category-edit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Category> editCategory(@PathVariable("id") Integer id, @RequestBody Category category)
			throws Exception{
		categoryService.save(category);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
