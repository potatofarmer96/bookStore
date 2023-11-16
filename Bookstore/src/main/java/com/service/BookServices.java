package com.service;

import java.util.List;

import com.dto.BookDTO;
import com.exception.BookStoreException;
//import com.entity.Book;

public interface BookServices {
	
	public String addBook(BookDTO bookDTO) throws BookStoreException;
	
	public String updateBook(BookDTO bookDTO) throws BookStoreException;
	
	public String deleteBook(String isbn) throws BookStoreException;

	public List<BookDTO> findBooksByTitleAndAuthor(String title, String authorName) throws BookStoreException;
	

}
