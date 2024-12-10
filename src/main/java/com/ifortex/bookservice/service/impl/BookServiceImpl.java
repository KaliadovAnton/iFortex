package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.repository.BookRepository;
import com.ifortex.bookservice.service.BookService;
import com.ifortex.bookservice.specification.BookSpecification;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public LinkedHashMap<String, Long> getBooks() {
        var books = bookRepository.findAll();
        return books.stream()
                .flatMap(book -> book.getGenres().stream())
                .distinct()
                .collect(Collectors.toMap(
                        genre -> genre,
                        genre -> books.stream().filter(book -> book.getGenres().contains(genre)).count()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @Override
    public List<Book> getAllByCriteria(SearchCriteria searchCriteria) {
        var author = searchCriteria.getAuthor();
        var title = searchCriteria.getTitle();
        var description = searchCriteria.getDescription();
        var genre = searchCriteria.getGenre();
        var year = searchCriteria.getYear();
        var titleIsBlank = StringUtils.isBlank(title);
        var authorIsBlank = StringUtils.isBlank(author);
        var descriptionIsBlank = StringUtils.isBlank(description);
        var genreIsBlank = StringUtils.isBlank(genre);
        if (authorIsBlank &&
                year == null &&
                titleIsBlank &&
                descriptionIsBlank &&
                genreIsBlank) {
            return bookRepository.findAll();
        }
        var filters = Specification.where(authorIsBlank ? null : BookSpecification.authorLike(author))
                .and(titleIsBlank ? null : BookSpecification.titleLike(title))
                .and(descriptionIsBlank ? null : BookSpecification.descriptionLike(description))
                .and(genreIsBlank ? null : BookSpecification.genreLike(genre))
                .and(year == null ? null : BookSpecification.yearLike(year));
        return bookRepository.findAll(filters);
    }
}
