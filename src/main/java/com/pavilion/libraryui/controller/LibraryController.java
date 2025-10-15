package com.pavilion.libraryui.controller;

import com.pavilion.libraryui.model.Book;
import com.pavilion.libraryui.model.BookPageResponse;
import com.pavilion.libraryui.service.BookService;
import com.pavilion.libraryui.service.BookViewService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.Label;
import java.time.LocalDate;


public class LibraryController {
    private final BookService service;
    private final BookViewService viewModel;

    public LibraryController() {
        this.viewModel = new BookViewService();
        this.service = new BookService();
    }

    private int currentPage = 0;
    private int totalPages = 1;
    private final int pageSize = 15;

    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, Long> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, String> colIsbn;
    @FXML
    private TableColumn<Book, LocalDate> colPublished;

    @FXML
    private Label lblCurrentPage;
    @FXML
    private Label lblTotalPages;
    @FXML
    private TextField titleField, authorField, isbnField, publishedField, searchField;


    @FXML
    public void initialize() {
        // --- Table setup ---
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colPublished.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) fillForm(newSel);
        });

        // --- Load first page ---
        loadPage(currentPage);
    }

    private void loadPage(int page) {
        try {
            BookPageResponse response = service.getAllBooks(page, pageSize);
            tableView.getItems().setAll(response.getBooks());
            currentPage = response.getPageNumber();
            totalPages = response.getTotalPages();

            lblCurrentPage.setText(String.valueOf(currentPage + 1));
            lblTotalPages.setText(String.valueOf(totalPages));

            updatePaginationButtons();
        } catch (Exception e) {
            showError("Failed to load books: " + e.getMessage());
        }
    }

    // --- Pagination button states ---
    private void updatePaginationButtons() {
        btnPrev.setDisable(currentPage == 0);
        btnFirst.setDisable(currentPage == 0);
        btnNext.setDisable(currentPage >= totalPages - 1);
        btnLast.setDisable(currentPage >= totalPages - 1);
    }

    // --- Pagination actions ---
    @FXML
    private Button btnFirst, btnPrev, btnNext, btnLast;

    @FXML
    private void onFirstPage() {
        loadPage(0);
    }

    @FXML
    private void onPrevPage() {
        if (currentPage > 0) loadPage(currentPage - 1);
    }

    @FXML
    private void onNextPage() {
        if (currentPage < totalPages - 1) loadPage(currentPage + 1);
    }

    @FXML
    private void onLastPage() {
        loadPage(totalPages - 1);
    }

    // --- CRUD Actions ---
    @FXML
    private void onAdd() {
        try {
            viewModel.addBook(titleField.getText(), authorField.getText(), isbnField.getText(), publishedField.getText());
            clearForm();
            loadPage(currentPage);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void onUpdate() {
        Book selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a book to update.");
            return;
        }
        try {
            viewModel.updateBook(selected, titleField.getText(), authorField.getText(), isbnField.getText(), publishedField.getText());
            clearForm();
            loadPage(currentPage);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void onDelete() {
        Book selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a book to delete.");
            return;
        }
        try {
            viewModel.deleteBook(selected);
            clearForm();
            loadPage(currentPage);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void onRefresh() {
        loadPage(currentPage);
    }

    @FXML
    private void onSearch() {
        tableView.setItems(viewModel.search(searchField.getText()));
    }

    private void fillForm(Book book) {
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        isbnField.setText(book.getIsbn());
        publishedField.setText(book.getPublishedDate() != null ? book.getPublishedDate().toString() : "");
    }

    private void clearForm() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        publishedField.clear();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }


}