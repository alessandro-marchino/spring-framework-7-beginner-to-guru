package guru.springframework.spring7aiintro.controllers;

import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring7aiintro.model.Answer;
import guru.springframework.spring7aiintro.model.Question;
import guru.springframework.spring7aiintro.services.OllamaAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class QuestionController {

	private final OllamaAIService service;

	@PostMapping("/ask")
	public Answer askQuestion(@RequestBody Question question) {
		return service.getAnswer(question);
	}

}
