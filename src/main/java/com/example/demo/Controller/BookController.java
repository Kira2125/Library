package com.example.demo.Controller;


import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import com.example.demo.Service.BookService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/library")
public class BookController {

    private final UserService userService;

    private final BookService bookService;

    public BookController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("{id}")
    public String getOneUser(@PathVariable Long id, Model model) {
        List<Book> books = bookService.getBooks(id);
        model.addAttribute("books", books);
        return "user-library";
    }
}
