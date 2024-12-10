package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    void testGetAllByCriteria() {
        var expected = "The Catcher in the Rye";
        var criteria = SearchCriteria.builder().author("Salinger").title("Rye").build();
        var result = bookService.getAllByCriteria(criteria);

        assertEquals(expected, result.get(0).getTitle());
    }

    @Test
    void testGetAllByCriteria2() {
        var criteria = SearchCriteria.builder().build();
        var result = bookService.getAllByCriteria(criteria);

        assertEquals(23, result.size());
    }

    @Test
    void  testGetBooks() {
        var result = bookService.getBooks();
        var expected = new HashMap<String, Long>();
        expected.put("Adventure", 3L);
        expected.put("Horror", 1L);
        expected.put("Memoir", 1L);
        expected.put("Romance", 2L);
        expected.put("Classic", 4L);
        expected.put("History", 1L);
        expected.put("Self-Help", 2L);
        expected.put("Dystopian", 3L);
        expected.put("Thriller", 1L);
        expected.put("Fantasy", 2L);
        expected.put("Biography", 3L);
        expected.put("Non-Fiction", 7L);
        expected.put("Mystery", 1L);
        expected.put("Philosophical", 1L);
        expected.put("Fiction", 13L);
        expected.put("Historical", 1L);
        assertEquals(expected, result);
    }
}