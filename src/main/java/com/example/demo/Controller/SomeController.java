package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/some/v1")
public class SomeController {
    private List<String> list = Stream.of(
            "Jay",
            "NotJAy",
             "Petr",
            "Petrov"
    ).collect(Collectors.toList());

    @GetMapping
    public List<String> getAll() {
        return list;
    }

}
