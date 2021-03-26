package com.itptit.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.itptit.entities.Product;
import com.itptit.model.search.ProductSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itptit.entities.Category;
import com.itptit.model.search.CategorySearch;
import com.itptit.respositories.CategoryRepo;

@Service
public class CategoryService {

	@PersistenceContext protected EntityManager entityManager;
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ProductsService productsService;

	public void save(Category categories) throws IllegalAccessException, IOException{
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = (User) auth.getPrincipal();
//		Integer userId = user.getId();
		if(categories.getId() == null)
		{
			LocalDateTime createdDate = LocalDateTime.now();
			categories.setCreatedDate(createdDate);
//			categories.setCreatedBy(userId);
		}
		else {
			LocalDateTime createdDate = categoryRepo.getOne(categories.getId()).getCreatedDate();
			categories.setCreatedDate(createdDate);
			LocalDateTime updatedDate = LocalDateTime.now();
			categories.setUpdatedDate(updatedDate);
//			categories.setUpdatedBy(userId);
		}
		
		categoryRepo.save(categories);
		
	}

	private void removeProductByCategory(Category category){
		ProductSearch productSearch = new ProductSearch();
		productSearch.setCategoryId(category.getId());
		List<Product> products = productsService.search(productSearch);
		for (Product p: products) {
			p.setStatus(false);
		}
	}

	public void delete(Integer id) throws Exception{
		Category category = categoryRepo.findById(id).get();
		categoryRepo.delete(category);
	}

	public List<Category> search(final CategorySearch categorySearch) {
//		String jpql = "Select caijcungduoc from Product caijcungduoc";
//		Query query = entityManager.createQuery(jpql, Product.class);

		String sql = "select * from donghuuhieu.tbl_category where 1=1";

		// tìm kiếm theo category ID.
		if(categorySearch != null && categorySearch.getId() != null) {
			sql = sql + " and id=" + categorySearch.getId();
		} 

		// tìm kiếm theo ID của sản phẩm.
		if(categorySearch != null && categorySearch.getParentId() != null) {
			sql = sql + " and parent_id=" + categorySearch.getParentId();
		}
		
		Query query = entityManager.createNativeQuery(sql, Category.class);
		
//		if(categorySearch.getCurrentPage() != null && categorySearch.getCurrentPage() > 0) {
//			query.setFirstResult((categorySearch.getCurrentPage()-1) * ProductSearch.SIZE_ITEMS_ON_PAGE);
//			query.setMaxResults(ProductSearch.SIZE_ITEMS_ON_PAGE);
//		}
		
		return query.getResultList();
	}
}

