package com.rudrasiva86.jdbcdemo.dao;

import java.util.List;

import com.rudrasiva86.jdbcdemo.model.Product;

public interface ProductRepository {
	Product findById(int i);

	List<Product> findAll();

	boolean delete(Product Product);

	boolean update(Product Product);

	boolean create(Product Product);

	boolean createWithJdbcInsert(Product product);

	boolean createWithNamedParameters(Product product);
}
