package com.example.library;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Logger;

public class BookRepository {
	//private static Logger log = Logger.getLogger(BookRepository.class.getName());
	static private List<Book> bookList = new ArrayList<Book> ();
	
	public void save(Book bk) {
		bk.setId(bookList.size() + 1);
		insert(bk);
	}
	
	private void insert(Book bk) {
		bookList.add(bk);
	}
	
	public List<Book> load() {
		return bookList;
	}
	
	public Book loadLast() {
		Book result = new Book();
		for(Book bk : bookList) {
			result = bk;
		}
		return result;
	}
	
	public void delete(int id) {

		List<Book> copyList = new ArrayList<Book> ();
		for(Book bk : bookList) {
			if(bk.getId() == id) {
				
			}
			else {
				Book bkn = new Book(copyList.size() + 1, bk.getTitle(), bk.getAuthor());
				copyList.add(bkn);
			}
		}
		bookList.clear();
		bookList.addAll(copyList);
	}
	
	public String loadBook(List<Book> list) {
		return listToString(list);
	}
	
	/*public String find(int id) {
		return listToString(findIntList(id));
	}*/
	
	public List<Book> find(String txt) {
		if(!isNumeric(txt)) {
			System.out.println("Non numerico: " + txt);
			return findStringList(txt);
		}
		else if(isNumeric(txt)) {
			System.out.println("Numerico: " + txt);
			double d = Double.parseDouble(txt);
			return findIntList( (int) d );
		}
		else return new ArrayList<Book> ();
	}
		
	public static boolean isNumeric(String value) {
		if (value == null) {
	        return false;
	    }
	    try {
	        //double d = Double.parseDouble(value);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
		return true;
	}
	
	static public List<Book> findStringList(String txt) {
		System.out.println("findStringList " + txt);
		List<Book> bkList = new ArrayList<Book> ();
		for(Book bk : bookList) {
			System.out.println("bk: " + bk);
			if( bk.getTitle().equals(txt) || bk.getAuthor().equals(txt) )
				bkList.add(bk);
		}
		return bkList;
	}
	
	static public List<Book> findIntList(int id) {
		List<Book> bkList = new ArrayList<Book> ();
		for(Book bk : bookList) {
			if( bk.getId()==id )
				bkList.add(bk);
		}
		return bkList;
	}
	
	static public String listToString(List<Book> list) {
		String result = "";
		for(Book bk : list) {
			result += bk.toString();
		}
		return result;
	}
	
	public Boolean findDuplicate(int id) {
		
		for(Book bk : bookList) {
			if( bk.getId() == id )
				return false;
		}
		return true;
	}
	
	public void clear() {
		bookList.clear();
	}
}
