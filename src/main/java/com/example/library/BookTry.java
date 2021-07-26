package com.example.library;

import org.springframework.jdbc.core.JdbcTemplate;

public class BookTry {
	
	public static void main(String[] args) {
		BookRepository bkRepo = new BookRepository();
		Book bkOne = new Book();
		Book bkTwo = new Book();
		bkOne.setTitle("Alexandros");
		bkOne.setAuthor("Manfredi");
		bkTwo.setTitle("Ramses");
		bkTwo.setAuthor("Jacq");
		
		bkRepo.save(bkOne);
		bkRepo.save(bkTwo);
		
		System.out.println("Load repo: ");
		System.out.println(bkRepo.load());
		
		//bkRepo.delete(bkOne);
		
		System.out.println("Find String: " + bkRepo.find("Manfredi"));
		System.out.println("Find int: " + bkRepo.find("1"));
		System.out.println("Find int: " + bkRepo.find("2"));
		
		//System.out.println("1. Find id: " + bkRepo.find(2));
		
		//System.out.println("2. Find txt: " + bkRepo.find("Jacq"));
		//System.out.println("3. Find txt: " + bkRepo.find("Ramses"));
		
		//Book bkThird = new Book(002, "Nefer", "Jacq");
		//BookRepository.save("Nefer", "Jacq");
		//System.out.println(bkRepo.load());
		//System.out.println("4. Find txt:\n" + bkRepo.find("Jacq"));
		//BookRepository.delete(2);
		//System.out.println(bkRepo.load());
		
	}

}
