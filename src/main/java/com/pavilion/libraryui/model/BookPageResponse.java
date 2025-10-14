package com.pavilion.libraryui.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookPageResponse {
    private List<Book> books;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
}
