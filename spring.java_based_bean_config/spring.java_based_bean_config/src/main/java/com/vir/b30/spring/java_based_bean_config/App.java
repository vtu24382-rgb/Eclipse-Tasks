 package com.vir.b30.spring.java_based_bean_config;

 import java.util.LinkedList;
 import java.util.Scanner;

 public class App{
 	
 LinkedLists<Integer> l=new LinkedLists<>();
 	
 	void insert() {
 		l.add(100);
 		l.add(200);
 	}
 	
 	void show() {
 		System.out.println(l);
 	}
 	
 	void modify()
 	{
 		//l[0]=300; error
 		l.set(0, 300);
 	}
 	
 	void modifyParticular() {
 		System.out.println("Which item you want to modify give that index?");
 		int index=new Scanner(System.in).nextInt();
 		System.out.println("New Value?");
 		int value=new Scanner(System.in).nextInt();
 		l.set(index, value);
 	}
 	
 	void deleteItem() {
 		l.remove(new Scanner(System.in).nextInt());
 	}
 	
 	public static void main(String[] args) {
 		CollSAmple objAmple=new CollSAmple();
 		objAmple.insert();
 		objAmple.show();
 		objAmple.modifyParticular();
 		objAmple.show();
 		System.out.println("Enter the index of an item to be deleted");
 		objAmple.deleteItem();
 		objAmple.show();
 	}

 }
