package com.rudrasiva86.jdbcdemo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.rudrasiva86.jdbcdemo.model.Product;
import com.rudrasiva86.jdbcdemo.model.ProductMapper;

@Repository
public class JdbcProductRepository implements ProductRepository {
	
	private final String SQL_FIND_BY_ID = "select * from product where id = ?";
	private final String SQL_FIND_ALL = "select * from product";
	private final String SQL_DELETE = "delete from product where id = ?";
	private final String SQL_UPDATE = "update product set name = ?, description = ? where id = ?";
	private final String SQL_CREATE = "insert into product(id, name, description) values(?,?,?)";

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	
	@Autowired
	public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Product findById(int id) {
		return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new ProductMapper(), id);
	}

	public List<Product> findAll() {
		return jdbcTemplate.query(SQL_FIND_ALL, BeanPropertyRowMapper.newInstance(Product.class));
	}

	public boolean delete(Product product) {
		return jdbcTemplate.update(SQL_DELETE, product.getId()) > 0;
	}

	public boolean update(Product product) {
		return jdbcTemplate.update(SQL_UPDATE, product.getName(), product.getDescription(), product.getId()) > 0;
	}
	
	@Override
	public boolean create(Product product) {
		return jdbcTemplate.update(SQL_CREATE, product.getId(), product.getName(), product.getDescription()) > 0;
	}

	@Override
	public boolean createWithNamedParameters(Product product) {
		NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(dataSource);
		Map<String, Object> params = new HashMap<>();
		params.put("id", product.getId());
		params.put("name", product.getName());
		params.put("desc", product.getDescription());
		return npjt.update("insert into product(id, name, description) values(:id,:name,:description)", params) > 0;
	}
	
	@Override
	public boolean createWithJdbcInsert(Product product) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("product");
		Map<String, Object> params = new HashMap<>();
		params.put("id", product.getId());
		params.put("name", product.getName());
		params.put("desc", product.getDescription());
		return jdbcInsert.execute(params) > 0;
	}

}
