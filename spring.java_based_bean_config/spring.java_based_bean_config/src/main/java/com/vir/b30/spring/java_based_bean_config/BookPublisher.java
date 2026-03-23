package com.vir.b30.spring.java_based_bean_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BookPublisher {
	
	
Book book;


//DI via Constructor
@Autowired
public BookPublisher(Book book) {
	super();
	this.book = book;
}

@Autowired
ComicsBook comicsBook;

@Autowired
@Qualifier("thrillerBook")
Publish publish;




//DI via Method Injection
@Autowired
public void check(Book book) {
	
	comicsBook.show();
	
	publish.show();
	
	System.out.println(book.hashCode());
	
}




public Book getBook() {
	return book;
}



//DI via property injection
@Autowired
public void setBook(Book book) {
	this.book = book;
}


}