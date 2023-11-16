package com.entity;

import java.util.HashSet;
import java.util.Set;

import com.dto.AuthorDTO;
import com.dto.BookDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="books")
public class Book {

	@Id
	private String isbn;
	private String title;
	private int year;
	private double price;
	private String genre;
	@ManyToMany
	@JoinTable(name = "book_authors", joinColumns = {
			@JoinColumn(name = "book_isbn") }
			, inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> authorName;

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

	public Set<Author> getAuthorName() {
		return authorName;
	}

	public void setAuthorName(Set<Author> authorName) {
		this.authorName = authorName;
	}

	public BookDTO prepareDTO(Book book) {
		BookDTO bookDTO = new BookDTO();

		bookDTO.setIsbn(book.getIsbn());
		;
		bookDTO.setGenre(book.getGenre());
		bookDTO.setPrice(book.getPrice());
		bookDTO.setTitle(book.getTitle());
		bookDTO.setYear(book.getYear());

		Set<Author> set = book.getAuthorName();

		Set<AuthorDTO> DTOSet = new HashSet<AuthorDTO>();

		for (Author author : set) {
			AuthorDTO aDTO = author.prepareDTO(author);
			DTOSet.add(aDTO);
		}

		bookDTO.setAuthorDTO(DTOSet);

		return bookDTO;
	}

}
