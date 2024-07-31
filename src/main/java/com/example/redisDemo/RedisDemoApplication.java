package com.example.redisDemo;

import com.example.redisDemo.entity.Product;
import com.example.redisDemo.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class RedisDemoApplication {

	@Autowired
	private ProductDao dao;

	@PostMapping("/add")
	public Product save(@RequestBody Product product){
		return dao.save(product);
	}

	@GetMapping("/get")
	public List<Product> getAllProducts(){
		return dao.findAll();
	}

	@GetMapping("/get/{id}")
	@Cacheable(key="#id", value="Product", unless = "#result.price > 1000")
	public Product getProduct(@PathVariable int id){
		return dao.findById(id);
	}

	@DeleteMapping("/delete/{id}")
	public String removeProduct(@PathVariable int id){
		return dao.deleteProduct(id);
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

}
