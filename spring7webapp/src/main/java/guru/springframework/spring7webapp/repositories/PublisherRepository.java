package guru.springframework.spring7webapp.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring7webapp.domain.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

}
