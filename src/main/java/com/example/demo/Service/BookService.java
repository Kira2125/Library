package com.example.demo.Service;

import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import com.example.demo.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(Long id) {
        return bookRepository.getBooks(id);
    }
}
