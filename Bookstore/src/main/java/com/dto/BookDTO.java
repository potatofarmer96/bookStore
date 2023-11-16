package com.dto;

import java.util.HashSet;
import java.util.Set;

//import javax.validation.constraints.NotNull;

import com.entity.Author;
import com.entity.Book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class BookDTO {
	
	@NotNull(message="Please provide ISBN")
	private String isbn;

	@NotNull(message="Please provide Title")
	private String title;

	@Min(value = 1600, message ="Please provide Year and pass in at least 1600 and above")
	private int year;

	@Min(value = 1, message="Please provide a price of at least $1")
	private double price;

	@NotNull(message = "Please provide Genre")
	private String genre;

	@NotNull(message = "Please provide at least 1 Author")
	@Valid
	private Set<AuthorDTO> authorDTO;

	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Set<AuthorDTO> getAuthorDTO() {
		return authorDTO;
	}
	public void setAuthorDTO(Set<AuthorDTO> authorDTO) {
		this.authorDTO = authorDTO;
	}
	
	public Book prepareBook(BookDTO bookDTO) {
		
		Book book = new Book();

		book.setIsbn(bookDTO.getIsbn());
		book.setGenre(bookDTO.getGenre());
		book.setPrice(bookDTO.getPrice());
		book.setTitle(bookDTO.getTitle());
		book.setYear(bookDTO.getYear());
		
		Set<AuthorDTO> DTOSet = bookDTO.getAuthorDTO();
		
		Set<Author> set = new HashSet<Author>();
		
 		for (AuthorDTO dto : DTOSet) {
			Author author = dto.prepareAuthor(dto);
			set.add(author);
		}
 		
 		book.setAuthorName(set);
		
		return book;
	}
}
