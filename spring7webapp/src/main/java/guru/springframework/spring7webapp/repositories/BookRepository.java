package guru.springframework.spring7webapp.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring7webapp.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

}
