package guru.springframework.spring7webapp.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring7webapp.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
