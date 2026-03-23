package com.vir.b30.spring.java_based_bean_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component

public class ThrillerBook implements Publish {

	@Autowired
	Book book3;
	
	
	public void show() {
		System.out.println("Thriller Book");
		System.out.println("....."+book3.hashCode());

	}

}
