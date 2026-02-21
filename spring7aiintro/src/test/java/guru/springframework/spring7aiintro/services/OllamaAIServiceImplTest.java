package guru.springframework.spring7aiintro.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OllamaAIServiceImplTest {

	@Autowired OllamaAIService ollamaAIService;

    @Test
    void testGetAnswer() {
		String answer = ollamaAIService.getAnswer("Tell me a dad joke.");
		System.out.println("Got the answer:");
		System.out.println(answer);
    }
}
