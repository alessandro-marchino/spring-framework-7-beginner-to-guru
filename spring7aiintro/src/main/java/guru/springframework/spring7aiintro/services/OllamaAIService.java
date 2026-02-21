package guru.springframework.spring7aiintro.services;

import guru.springframework.spring7aiintro.model.Answer;
import guru.springframework.spring7aiintro.model.GetCapitalRequest;
import guru.springframework.spring7aiintro.model.GetCapitalResponse;
import guru.springframework.spring7aiintro.model.Question;
import reactor.core.publisher.Flux;

public interface OllamaAIService {

	String getAnswer(String question);
	Flux<String> getAnswerStream(String question);

	Answer getAnswer(Question question);
    GetCapitalResponse getCapital(GetCapitalRequest question);
	Answer getCapitalWithInfo(GetCapitalRequest question);
}
