package com.vir.b30.spring.java_based_bean_config;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class Book {

	String bookName;
	int price;
	//List booksList;
	public Book(String bookName, int price) {
		super();
		this.bookName = bookName;
		this.price = price;
	}
	
	
	
	
}
