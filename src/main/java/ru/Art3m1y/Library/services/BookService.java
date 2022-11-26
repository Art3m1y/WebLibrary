package ru.Art3m1y.Library.services;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Art3m1y.Library.models.Book;
import ru.Art3m1y.Library.models.Person;
import ru.Art3m1y.Library.repositories.BookRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    private final PersonService personService;

    public BookService(BookRepository bookRepository, PersonService personService) {
        this.bookRepository = bookRepository;
        this.personService = personService;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll(String page, String book_on_page, String sort_by_year) {
        if (sort_by_year == null) {
            sort_by_year = "null";
        }
        if (((page == null) || (book_on_page == null)) && (!sort_by_year.equals("true"))) {
            return bookRepository.findAll();
        } else if ((((page == null) || (book_on_page == null)) && (sort_by_year.equals("true")))) {
            return bookRepository.findAll(Sort.by("year"));
        } else if ((page != null) && (book_on_page != null) && (sort_by_year.equals("true"))) {
            return bookRepository.findAll(PageRequest.of(Integer.valueOf(page), Integer.valueOf(book_on_page), Sort.by("year"))).getContent();
        }
        else {
            return bookRepository.findAll(PageRequest.of(Integer.valueOf(page), Integer.valueOf(book_on_page))).getContent();
        }
    }
    @Transactional(readOnly = true)
    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }
    @Transactional(readOnly = true)
    public Book findByName(String name) {
        return bookRepository.findByName(name).orElse(null);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    public void update(int id, Book bookUpdated) {
        Book book = bookRepository.findById(id).orElse(null);
        book.setName(book.getName());
        book.setAuthor(book.getAuthor());
        book.setYear(book.getYear());

        bookRepository.save(book);
    }

    public void assign(int id, Person personAssign) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book.getPerson() != null) {
            book.setAssignedat(null);
        } else {
            book.setAssignedat(new Date());
        }

        book.setPerson(personService.findById(personAssign.getPerson_id()));

        bookRepository.save(book);
    }

    public Book searchBook(String startingWith) {
        return bookRepository.findFirstByNameStartingWith(startingWith).orElse(null);
    }
}
