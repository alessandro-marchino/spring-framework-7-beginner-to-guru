package guru.springframework.spring7aiintro.services;

import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import guru.springframework.spring7aiintro.model.Answer;
import guru.springframework.spring7aiintro.model.GetCapitalRequest;
import guru.springframework.spring7aiintro.model.Question;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class OllamaAIServiceImpl implements OllamaAIService {

	private final ChatModel chatModel;
	@Value("classpath:templates/get-capital-prompt.st")
	private Resource getCapitalPrompt;
	@Value("classpath:templates/get-capital-with-info.st")
	private Resource getCapitalWithInfoPrompt;

	@Override
	public String getAnswer(String question) {
		PromptTemplate promptTemplate = new PromptTemplate(question);
		Prompt prompt = promptTemplate.create();
		ChatResponse response = chatModel.call(prompt);
		return response.getResult().getOutput().getText();
	}

	@Override
	public Answer getAnswer(Question question) {
		String response = getAnswer(question.question());
		return new Answer(response);
	}

	@Override
	public Answer getCapital(GetCapitalRequest request) {
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", request.stateOrCountry()));
		ChatResponse response = chatModel.call(prompt);
		return new Answer(response.getResult().getOutput().getText());
	}

	@Override
	public Answer getCapitalWithInfo(GetCapitalRequest request) {
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPrompt);
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", request.stateOrCountry()));
		ChatResponse response = chatModel.call(prompt);
		return new Answer(response.getResult().getOutput().getText());
	}

	@Override
	public Flux<String> getAnswerStream(String question) {
		return chatModel.stream(question);
	}
}
