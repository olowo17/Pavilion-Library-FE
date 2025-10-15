# Library Management Frontend

This is a JavaFX-based frontend for a Library Management System. It provides a graphical user interface to view, add, update, delete, and search books, with support for pagination and basic form handling.

## Features

- Display all books in a paginated `TableView`.
- Add, update, and delete books.
- Search books by title, author, or ISBN.
- Pagination controls: First, Previous, Next, Last page.
- Form validation and error handling.
- Centered layout for consistent UI appearance.

## Prerequisites

- Java 17 or higher
- Maven
- Backend API running (e.g., Spring Boot REST API) at `http://localhost:8080/api/books`
- JavaFX 17

## Setup

1. Clone the repository:
```bash
git clone https://github.com/olowo17/Pavilion-Library-FE.git
cd Pavilion-Library-FE
Ensure the backend API is running at http://localhost:8080/api/books.

3. Build the project with Maven: mvn javafx:run


## Usage

Use the Add button to add a new book using the form at the bottom.

Select a book in the table to populate the form for updating or deleting.

Use the Update or Delete buttons for the selected book.

Use the search box to filter books by title, author, or ISBN.

Use pagination buttons to navigate through pages.

