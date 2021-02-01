package com.example.demo.Controller;


import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import com.example.demo.Service.BookService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/library")
public class BookController {

    @Value("${upload.path}")
    private String uploadPath;

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
    public String getOneUser(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        List<Book> books = bookService.getBooks(id);

        String username = userDetails.getUsername();
        User user = userService.findByEmail(username);

        model.addAttribute("books", books);
        model.addAttribute("user", user);
        model.addAttribute("id", id);


        return "user-library";
    }

    @GetMapping("{id}/create_book")
    public String getCreateForm(@PathVariable Long id, Model model, Book book) {
        model.addAttribute("userID", id);
        return "create-book";
    }

    @PostMapping("/create_book")
    public String saveBook(@AuthenticationPrincipal UserDetails userDetails, Book book,
                           @RequestParam("imgFile") MultipartFile imgFile) {

        if (imgFile != null && !imgFile.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + imgFile.getOriginalFilename();

            try {
                imgFile.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }

            book.setImgName(resultFilename);
        }


        String username = userDetails.getUsername();
        User user = userService.findByEmail(username);
        book.setOwner(user);
        bookService.saveBook(book);
        return "redirect:/library";
    }
}
