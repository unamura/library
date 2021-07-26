package com.example.library;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

class BookTest {
	private static Logger log = Logger.getLogger(BookRepository.class.getName());

	BookRepository brn = new BookRepository();

	@BeforeEach
	void setup() {
		brn.clear();
	}
	
	@Test
	void testInsertBook() {	
		
		Book bkOne = new Book();
		Book bkTwo = new Book();
		
		bkOne.setTitle("1q84");
		bkOne.setAuthor("Murakami");
		
		bkTwo.setTitle("Macbeth");
		bkTwo.setAuthor("Shakespeare");
		
		brn.save(bkOne);
		brn.save(bkTwo);
		
		log.info("First load:\n" + brn.load());
		
		//String expOne = "[1, 1q84, Murakami]";
		//assertEquals(expOne, brn.find(1));
		
		//String expTwo = "[2, Macbeth, Shakespeare]";
		//assertEquals(expTwo, brn.find(2));
		
		Book bkThree = new Book();
		bkThree.setTitle("Ramses");
		bkThree.setAuthor("Jacq");
		brn.save(bkThree);
		
		String expLoad = "[[1, 1q84, Murakami], [2, Macbeth, Shakespeare], [3, Ramses, Jacq]]";
		String result = brn.load().toString();

		assertEquals(expLoad, result);
		brn.clear();
		
	}
	
	@Test
	void testSecondInsert() {	
		BookRepository br = new BookRepository();
		
		Book bkFour = new Book();
		bkFour.setTitle("Nefertiti");
		bkFour.setAuthor("Jacq");
		br.save(bkFour);
		
		log.info("Third load:\n" + br.load());
		
		br.find("Jacq");
		//log.info(br.find("Jacq"));
		
		String expDel = "[[1, Nefertiti, Jacq]]";
		br.delete(3);
		assertEquals(expDel, br.load().toString());
		log.info("Firth load:\n" + br.load());
		br.clear();
	}
	
	@Test
	void testOtherInsert() {
		BookRepository br = new BookRepository();
		
		List<Book> outPut = br.load();
		log.info(""+outPut);
	}

}
