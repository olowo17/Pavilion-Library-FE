package com.pavilion.libraryui.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pavilion.libraryui.BookServiceException;
import com.pavilion.libraryui.model.Book;
import com.pavilion.libraryui.model.BookPageResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.logging.Logger;

import java.util.List;

@Service
public class BookService {
    private final RestTemplate restTemplate = new RestTemplate();
    private  ObjectMapper mapper = new ObjectMapper();
    private static final String BASE_URL = "http://localhost:8080/api/books";
    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    public BookService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public BookPageResponse getAllBooks(int page, int size) {
        try {
            String url = BASE_URL + "?page=" + page + "&size=" + size;
            String json = restTemplate.getForObject(url, String.class);
            logger.info("Paginated books JSON: " + json);
            return mapper.readValue(json, BookPageResponse.class);
        } catch (Exception e) {
            throw new BookServiceException("Failed to fetch paginated books", e);
        }
    }

    public List<Book> getAll() {
        try {
            String json = restTemplate.getForObject(BASE_URL, String.class);
            logger.info("Books from backend: " + json);
            BookPageResponse response = mapper.readValue(json, BookPageResponse.class);
            return response.getBooks();
        } catch (Exception e) {
            throw new BookServiceException("Failed to fetch books from backend", e);
        }
    }

    public Book add(Book book) {
        logger.info ("saving book:" + book.toString());
        return restTemplate.postForObject(BASE_URL, book, Book.class);
    }

    public void update(Book book) {
        restTemplate.put(BASE_URL + "/" + book.getId(), book);
    }

    public void delete(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
