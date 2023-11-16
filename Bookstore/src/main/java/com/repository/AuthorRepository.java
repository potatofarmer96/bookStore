package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
