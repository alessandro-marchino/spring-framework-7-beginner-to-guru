package guru.springframework.spring7aiintro.services;

import reactor.core.publisher.Flux;

public interface OllamaAIService {

	String getAnswer(String question);
	Flux<String> getAnswerStream(String question);
}
