package com.example.demo.Repository;

import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.owner.id = ?1")
    List<Book> getBooks(Long id);
}
