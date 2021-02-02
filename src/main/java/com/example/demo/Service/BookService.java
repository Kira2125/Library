package com.example.demo.Service;

import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import com.example.demo.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(Long id) {
        return bookRepository.getBooks(id);
    }

//    @Transactional
//    public void saveBook(Long id, Book book) {
//        entityManager.createNativeQuery("insert into books (name , user_id) values (?,?)")
//                .setParameter(1, book.getName())
//                .setParameter(2, id)
//                .executeUpdate();
//    }

    public void saveBook(Book book) {
       bookRepository.save(book);
    }


}
