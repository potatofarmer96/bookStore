package com.api;

import java.util.List;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.BookDTO;
import com.exception.BookStoreException;
import com.service.BookServices;

import jakarta.validation.Valid;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/userAPI")
public class UserAPI {
	
    @Autowired
    private BookServices bookServices;
    
    @GetMapping("/greeting")
    public ResponseEntity<String> greeting(Authentication authentication) {

        String userName = authentication.getName();
        
        String resource = "Spring Security In-memory Authentication Example - Welcome " + userName;

        return new ResponseEntity<String>(resource,HttpStatus.OK) ;
    }
    
    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@Valid @RequestBody BookDTO bookDTO) throws BookStoreException {
//    	System.out.println("object passed in:"+ bookDTO);
    	String resource = "Book with ISBN "+ bookServices.addBook(bookDTO)+" Successfully Added";

    	return new ResponseEntity<String>(resource,HttpStatus.OK) ;
    }
    
    @PutMapping("/updateBooks/{isbn}")
    public ResponseEntity<String> updateBook(@PathVariable String isbn,@Valid @RequestBody BookDTO bookDTO) throws BookStoreException {
        // Implement update logic with validation
    	String resource = bookServices.updateBook(bookDTO) +" Successfully updated";
    	
        return new ResponseEntity<String>(resource,HttpStatus.OK) ;
    }
    
    @GetMapping("/getBooks")
    public ResponseEntity<List<BookDTO>> findBooksByTitleAndAuthor(@RequestParam(required = false) String title, @RequestParam(required = false) String authorName) throws BookStoreException {
//    	System.out.println("title: " + title +" authorName: " + authorName);
    	List<BookDTO> resource = bookServices.findBooksByTitleAndAuthor(title, authorName);
//    	System.out.println("getbooks: " + resource);
    	return new ResponseEntity<List<BookDTO>>(resource, HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteBooks/{isbn}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) throws BookStoreException {
        // Implement delete logic with proper authorization checks
        String resource = bookServices.deleteBook(isbn) + " is deleted";
        
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("CMY: "+ authentication);
        
        return new ResponseEntity<String>(resource,HttpStatus.OK) ;
    }

}
