package com.example.library;

public class Book {
	 private int id;
	 private String title;
	 private String author;
	 private int isbn;
	 private String genre;
	 private int year;
	 private int pages;
	 private double price;
	 
	 public Book() {}
	 
	 public Book(int id, String title, String author) {
		 this.id = id;
		 this.title = title;
		 this.author = author;
	 }
	 
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	 
	@Override
	public String toString() {
	
		return "[" + id + ", " + title + ", " + author + "]";
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
