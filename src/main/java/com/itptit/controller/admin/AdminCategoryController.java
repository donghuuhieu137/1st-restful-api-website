package com.itptit.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.itptit.controller.BaseController;
import com.itptit.entities.Category;
import com.itptit.entities.Product;
import com.itptit.model.AjaxResponse;
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
	@RequestMapping(value = { "/admin/category-delete" }, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponse> deleteWithAjax(@RequestBody Category category) {
		Category categoryInDB = categoryRepo.getOne(category.getId());
		categoryInDB.setStatus(true);
		categoryRepo.save(categoryInDB);
		return ResponseEntity.ok(new AjaxResponse(200, "Thành công"));
	}
	
	//////// EDIT
	@RequestMapping(value = "/admin/category-edit/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategoryById( @PathVariable("id") Integer id,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response)
		throws Exception{
		Category category = categoryRepo.getOne(id);
		return new ResponseEntity<>(category,HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/category-edit/{id}", method = RequestMethod.POST)
	public ResponseEntity<Category> getCategoryById( @RequestBody Category category )
			throws Exception{
		categoryService.save(category);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
