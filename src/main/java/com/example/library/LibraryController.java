package com.example.library;

import java.util.Map;

//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LibraryController {
	private BookRepository  bkRepo = new BookRepository();
	// New JDBC method
	private TestService testService;
	
	public LibraryController(TestService testService) {
        this.testService = testService;
    }
	
	/*
	 * Start new JDBC methods
	 * */
	@RequestMapping("/try")
	@ResponseBody
	public String getBooks() {
		String result = "";
		for(BookDto bdto : testService.queryFromDatabase()) {
			result += bdto.getTitle();
		}
		return result;
	}
	
	@RequestMapping("/try/insert")
	@ResponseBody
	public String insertBooks(String title, String author) {
		
		String result = "";
		testService.insertIntoDatabase(title, author);

		for(BookDto bdto : testService.queryFromDatabase()) {
			result += bdto.getTitle();
		}
		return result;
	}
	
	@RequestMapping("/try/querymap")
	@ResponseBody
	public String queryListMap() {
		String result = "";
		for(Map<String, Object> mbk : testService.queryMap() ) {
			result += "\n" + mbk.toString();
			System.out.println("Mbk: " + mbk.toString());
		}
		return result;
	}
	
	@RequestMapping("/try/update")
	@ResponseBody
	public String updateQuery(String title, int id) {
		String result = "";
		
		testService.updateDataBase(title, id);
		
		for(BookDto bdto : testService.queryFromDatabase()) {
			result += bdto.getTitle();
		}
		return result;
	}
	
	@RequestMapping("/try/delete")
	@ResponseBody
	public String deleteQuery(int id) {
		String result = "";
		
		testService.deleteRow(id);
		
		for(BookDto bdto : testService.queryFromDatabase()) {
			result += bdto.getTitle();
		}
		return result;
	}

	/*
	 * Start old methods
	 * */
	@RequestMapping("/")
	@ResponseBody
	public String hello() {
		return "Hello World Library 2";
	}
	
	@GetMapping("/book")
	public String bookForm(Model model) {
		model.addAttribute("book", new Book());
		return "BookForm";
	}
	
	@PostMapping("/book")
	public String bookSubmit(@ModelAttribute Book book, Model model) {
		bkRepo.save(book);
		System.out.println("Book:" + book.getPages());
		model.addAttribute("book", bkRepo.loadLast());
		return "result";
	}
	
	@GetMapping("/repo")
	public String bookRepo(Model model) {
		model.addAttribute("bookList", bkRepo.load());
		return "repo";
	}
	
	@PostMapping("/repo")
	public String delete(@RequestParam(value = "id", required = false) int id, Model model) {
		bkRepo.delete(id);
		model.addAttribute("bookList", bkRepo.load());
		return "bookRepo";
	}
	
	@PostMapping("/search")
	public String search(@RequestParam(value = "bookValue", required = false) String bookValue,
			Model model) {
		System.out.println("bookValue: " + bookValue);
		model.addAttribute("searchList", bkRepo.find(bookValue));
		System.out.println("" + bkRepo.find(bookValue));
		return "searchbook";
	}
	
}
