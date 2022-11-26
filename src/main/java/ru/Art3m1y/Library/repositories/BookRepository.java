package ru.Art3m1y.Library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Art3m1y.Library.models.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByName(String name);
    Optional<Book> findFirstByNameStartingWith(String staringWith);
}
