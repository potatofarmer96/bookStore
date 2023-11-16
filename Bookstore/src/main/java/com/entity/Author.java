package com.entity;

import java.time.LocalDate;

import com.dto.AuthorDTO;

//import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String authorName;
	private LocalDate birthday;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	
	public AuthorDTO prepareDTO (Author author) {
		AuthorDTO authorDTO = new AuthorDTO();
		
		authorDTO.setAuthorName(author.getAuthorName());
		authorDTO.setBirthday(author.getBirthday());
		
		return authorDTO;
	}

}
