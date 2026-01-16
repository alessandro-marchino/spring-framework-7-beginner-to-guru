package guru.springframework.spring7webapp.services;

import org.springframework.stereotype.Service;

import guru.springframework.spring7webapp.domain.Book;
import guru.springframework.spring7webapp.repositories.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	/**
	 * @param bookRepository
	 */
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}

}
