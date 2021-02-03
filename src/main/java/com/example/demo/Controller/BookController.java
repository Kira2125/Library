package com.example.demo.Controller;

import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import com.example.demo.Service.BookService;
import com.example.demo.Service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final UserService userService;

    private final BookService bookService;

    public BookController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }


    @GetMapping
    public String getAllBooks(Model model, @AuthenticationPrincipal UserDetails userDetails ) {

        String username = userDetails.getUsername();
        User user = userService.findByEmail(username);
        Long id = user.getId();

        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        return "books";
    }
}
