package com.rudra.jdbcdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.rudra.jdbcdemo.config.AppConfig;
import com.rudra.jdbcdemo.dao.JdbcProductRepository;
import com.rudra.jdbcdemo.dao.ProductRepository;
import com.rudra.jdbcdemo.model.Product;

public class Application {
	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		ProductRepository productDao = ctx.getBean(JdbcProductRepository.class);
		
		System.out.println("List of products... ");
		productDao.findAll().forEach(System.out::println);
		
		
		System.out.println("\nGet Product with ID = 2... ");
		System.out.println(productDao.findById(2));
		
		System.out.println("\nCreate new product...");
		Product product4 = new Product(4, "P4", "Product Four");
		productDao.create(product4);
//		productDao.createWithNamedParameters(product4);
//		productDao.createWithJdbcInsert(product4);
		
		System.out.println("\nList of products, After adding new product... ");
		productDao.findAll().forEach(System.out::println);
		
		System.out.println("\nUpdate Product4... ");
		product4.setName("P4 Updated");
		product4.setDescription("Product Four Updated");
		productDao.update(product4);
		
		System.out.println("\nList of products, After updating product4... ");
		productDao.findAll().forEach(System.out::println);
		
		System.out.println("\nDeleting Product 4");
		productDao.delete(product4);
		
		System.out.println("\nFinal List of products... ");
		productDao.findAll().forEach(System.out::println);
		ctx.close();
	}
}
