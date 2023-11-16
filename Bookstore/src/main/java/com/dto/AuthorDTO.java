package com.dto;

import java.time.LocalDate;


import com.entity.Author;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class AuthorDTO {
	
	@NotNull(message="Please provide AuthorName")
	private String authorName;
	
	@NotNull(message="Please provide birthday of authors")
	@PastOrPresent(message="Please provide a date that is in the past or today")
	private LocalDate birthday;
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	public Author prepareAuthor(AuthorDTO authorDTO) {
		Author author = new Author();
		author.setAuthorName(authorDTO.getAuthorName());
		author.setBirthday(authorDTO.getBirthday());
		
		return author;
	}
	
	
}
