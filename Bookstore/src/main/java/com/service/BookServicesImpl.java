package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.BookDTO;
import com.entity.Author;
import com.entity.Book;
import com.exception.BookStoreException;
import com.repository.AuthorRepository;
import com.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookServicesImpl implements BookServices {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;

	public String addBook(BookDTO bookDTO) throws BookStoreException {
		// Add a new book to the database
		Optional<Book> optional = bookRepository.findById(bookDTO.getIsbn());
		if (optional.isPresent()) {
			throw new BookStoreException("BOOK ALREADY EXIST IN DATABASE");
		}

		Book book = bookDTO.prepareBook(bookDTO);
		
		Set<Author> authSet = book.getAuthorName();
		
		//seperate the author set out
		//save each author as a seperate entry in authRepo
		for (Author a: authSet) {
			authorRepository.save(a);
		}

		bookRepository.save(book);

		return book.getIsbn();
	}

	public String updateBook(BookDTO bookDTO) throws BookStoreException {
		// Update an existing book in the database

		Optional<Book> optional = bookRepository.findById(bookDTO.getIsbn());

		Book book = optional.orElseThrow(() -> new BookStoreException("BOOK NOT FOUND"));

		Book newbook = bookDTO.prepareBook(bookDTO);
		
		Set<Author> newAuthSet = newbook.getAuthorName();
		
		for (Author a: newAuthSet) {
			authorRepository.save(a);
		}

		bookRepository.save(newbook);

		return book.getIsbn();
	}

	public List<BookDTO> findBooksByTitleAndAuthor(String title, String authorName) throws BookStoreException {

		Optional<List<Book>> optional = bookRepository.findBooksByTitleAndAuthorNameNative(title, authorName);
		List<Book> bookList = optional
				.orElseThrow(() -> new BookStoreException("UNABLE TO FIND ANY BOOK WITH THAT TITLE AND AUTHOR"));

		List<BookDTO> bookDTOList = new ArrayList<>();
		for (Book b : bookList) {
			BookDTO bookDTO = b.prepareDTO(b);
			bookDTOList.add(bookDTO);
		}

		return bookDTOList;
		// Implement a search query to find books by title and/or author
		// You can use JpaRepository methods or write a custom query
	}

	public String deleteBook(String isbn) throws BookStoreException {
		// Implement book deletion with proper authorization checks
		
		Optional<Book> optional = bookRepository.findById(isbn);
		Book book = optional.orElseThrow(()->new BookStoreException("BOOK DOES NOT EXIST IN DATABASE"));
		
		bookRepository.delete(book);
		
		return isbn;
	}

}
