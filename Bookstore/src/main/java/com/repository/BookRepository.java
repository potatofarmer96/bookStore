package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
	
	@Query(value="select * from books b INNER JOIN book_authors ba ON b.isbn=ba.book_isbn INNER JOIN authors a ON a.id = ba.author_id "
			+ "where title= :title OR author_name= :authorName", nativeQuery=true)
	Optional<List<Book>> findBooksByTitleAndAuthorNameNative(@Param("title") String title, @Param("authorName") String authorName);
}
