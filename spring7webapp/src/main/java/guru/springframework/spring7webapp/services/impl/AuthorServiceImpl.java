package guru.springframework.spring7webapp.services.impl;

import org.springframework.stereotype.Service;

import guru.springframework.spring7webapp.domain.Author;
import guru.springframework.spring7webapp.repositories.AuthorRepository;
import guru.springframework.spring7webapp.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	/**
	 * @param authorRepository
	 */
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public Iterable<Author> findAll() {
			return authorRepository.findAll();
	}
}
