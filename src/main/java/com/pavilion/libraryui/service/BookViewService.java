package com.pavilion.libraryui.service;


import com.pavilion.libraryui.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BookViewService {

    private final BookService service = new BookService();
    @Getter
    private final ObservableList<Book> books = FXCollections.observableArrayList();

    public void loadBooks() {
        try {
            List<Book> allBooks = service.getAll();
            books.setAll(allBooks);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load books", e);
        }
    }

    public void addBook(String title, String author, String isbn, String publishedDate) {
        try {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setPublishedDate(LocalDate.parse(publishedDate));
            Book saved = service.add(book);
            books.add(saved);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add book", e);
        }
    }

    public void updateBook(Book existing, String title, String author, String isbn, String publishedDate) {
        try {
            existing.setTitle(title);
            existing.setAuthor(author);
            existing.setIsbn(isbn);
            existing.setPublishedDate(LocalDate.parse(publishedDate));
            service.update(existing);
            loadBooks();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update book", e);
        }
    }

    public void deleteBook(Book book) {
        try {
            service.delete(book.getId());
            books.remove(book);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete book", e);
        }
    }

    public ObservableList<Book> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return books;
        String lower = keyword.toLowerCase();
        return books.filtered(b ->
                b.getTitle().toLowerCase().contains(lower)
                        || b.getAuthor().toLowerCase().contains(lower)
                        || b.getIsbn().toLowerCase().contains(lower)
        );
    }
}
