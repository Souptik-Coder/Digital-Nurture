package com.example.BookStoreAPI.service;

import com.example.BookStoreAPI.model.Book;
import com.example.BookStoreAPI.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setPrice(updatedBook.getPrice());
                    book.setIsbn(updatedBook.getIsbn());
                    return bookRepository.save(book);
                });
    }

    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Book> getFilteredBooks(String title, String author) {
        if (title != null && author != null) {
            return bookRepository.findByTitleContainingAndAuthorContaining(title, author);
        } else if (title != null) {
            return bookRepository.findByTitleContaining(title);
        } else if (author != null) {
            return bookRepository.findByAuthorContaining(author);
        } else {
            return bookRepository.findAll();
        }
    }
}
