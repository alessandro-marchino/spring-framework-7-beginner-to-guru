package guru.springframework.spring7webapp.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spring7webapp.domain.Author;
import guru.springframework.spring7webapp.domain.Book;
import guru.springframework.spring7webapp.domain.Publisher;
import guru.springframework.spring7webapp.repositories.AuthorRepository;
import guru.springframework.spring7webapp.repositories.BookRepository;
import guru.springframework.spring7webapp.repositories.PublisherRepository;

@Component
public class BootstrapData implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(BootstrapData.class);

	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;

	/**
	 * @param authorRepository
	 * @param bookRepository
	 * @param publisherRepository
	 */
	public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository,
			PublisherRepository publisherRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.publisherRepository = publisherRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		Author eric = new Author();
		eric.setFirstName("Eric");
		eric.setLastName("Evans");

		Book ddd = new Book();
		ddd.setTitle("Domain Driven Design");
		ddd.setIsbn("123456");

		Author ericSaved = authorRepository.save(eric);
		Book dddSaved = bookRepository.save(ddd);

		Author rod = new Author();
		rod.setFirstName("Rod");
		rod.setLastName("Johnson");

		Book noEJB = new Book();
		noEJB.setTitle("J2EE Development without EJB");
		noEJB.setIsbn("54757585");

		Author rodSaved = authorRepository.save(rod);
		Book noEJBSaved = bookRepository.save(noEJB);

		ericSaved.getBooks().add(dddSaved);
		rodSaved.getBooks().add(noEJBSaved);
		dddSaved.getAuthors().add(ericSaved);
		noEJBSaved.getAuthors().add(rodSaved);

		Publisher publisher = new Publisher();
		publisher.setPublisherName("My Publisher");
		publisher.setAddress("123 Main");

		Publisher savedPublisher = publisherRepository.save(publisher);
		ddd.setPublisher(savedPublisher);
		noEJBSaved.setPublisher(savedPublisher);

		authorRepository.save(ericSaved);
		authorRepository.save(rodSaved);
		bookRepository.save(dddSaved);
		bookRepository.save(noEJBSaved);

		LOG.info("In Bootstrap");
		LOG.info("Author count: {}", authorRepository.count());
		LOG.info("Book count: {}", bookRepository.count());
		LOG.info("Publisher count: {}", publisherRepository.count());
	}

}
