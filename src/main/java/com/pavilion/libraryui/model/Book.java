package com.pavilion.libraryui.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Setter
@Getter
@ToString
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publishedDate;

}