package com.ifortex.bookservice.specification;

import com.ifortex.bookservice.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    private BookSpecification() {}

    public static Specification<Book> titleLike(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Book> authorLike(String author) {
        return (root, query, builder) -> builder.like(root.get("author"), "%" + author + "%");
    }

    public static Specification<Book> genreLike(String genre) {
        return (root, query, builder) -> builder.like(root.get("genre"), "%" + genre + "%");
    }

    public static Specification<Book> descriptionLike(String description) {
        return (root, query, builder) -> builder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Book> yearLike(Integer year) {
        return (root, query, builder) -> builder.like(root.get("year"), "%" + year + "%");
    }
}
