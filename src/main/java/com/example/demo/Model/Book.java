package com.example.demo.Model;


import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "books")
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    @Column(name = "img_name")
    String imgName;

    @Column(name = "book_text")
    String bookText;
}
