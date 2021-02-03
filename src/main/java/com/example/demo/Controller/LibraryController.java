package com.example.demo.Controller;


import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import com.example.demo.Service.BookService;
import com.example.demo.Service.UserService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/library")
public class LibraryController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${text.path}")
    private String textPath;

    private final UserService userService;

    private final BookService bookService;

    public LibraryController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }



    @GetMapping("/text/{some}")
    public void serveFile(@PathVariable String some, HttpServletRequest request,
                                          HttpServletResponse response) {




        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/downloads/text/");
        Path file = Paths.get(dataDirectory, some);
        if (Files.exists(file))
        {
            if(some.endsWith(".pdf")) {
                response.setContentType("application/pdf");
            }
            if(some.endsWith(".txt")) {
                response.setContentType("text/plain");
            }

            response.addHeader("Content-Disposition", "attachment; filename="+some);
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }



//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + some + "\"").body(file);
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
                           @RequestParam("imgFile") MultipartFile imgFile,
                           @RequestParam("book_text") MultipartFile bookFile) {



        if (imgFile != null && !imgFile.getOriginalFilename().isEmpty()) {
            String imgName = imgFile.getOriginalFilename();
            if(!(imgName.endsWith(".png") || imgName.endsWith(".jpeg") || imgName.endsWith(".jpg"))) {
                return "redirect:/library";
            }

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

        if (bookFile != null && !bookFile.getOriginalFilename().isEmpty()) {
            String bookName = bookFile.getOriginalFilename();
            if(!(bookName.endsWith(".txt") || bookName.endsWith(".pdf"))) {
                return "redirect:/library";
            }

            File upload = new File(textPath);

            if (!upload.exists()) {
                upload.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultName = uuidFile + "." + bookFile.getOriginalFilename();

            try {
                bookFile.transferTo(new File(textPath + "/" + resultName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            book.setBookText(resultName);
        }



        String username = userDetails.getUsername();
        User user = userService.findByEmail(username);

        book.setOwner(user);
        bookService.saveBook(book);
        return "redirect:/library";
    }


}
