package ru.Art3m1y.Library.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.Art3m1y.Library.models.Book;
import ru.Art3m1y.Library.services.BookService;

@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookService.findByName(book.getName()) != null) {
            errors.rejectValue("name", "", "Эта книга уже есть в базе данных");
        }
    }
}
