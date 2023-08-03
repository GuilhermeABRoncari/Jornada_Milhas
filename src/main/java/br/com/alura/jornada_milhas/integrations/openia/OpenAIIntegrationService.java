package br.com.alura.jornada_milhas.integrations.openia;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@NoArgsConstructor
public class OpenAIIntegrationService {

    @Value("${api.key.from.openai}")
    private String apiKey;
    private static final Integer MAX_TOKENS = 255;
    private static final String PROMPT_MESSAGE = "Faça um resumo sobre %s enfatizando o porque este lugar é incrível. Utilize uma linguagem informal e até 100 caracteres no máximo em cada parágrafo. Crie 2 parágrafos neste resumo.";
    private static final String FREE_TEXT_MODEL = "text-davinci-003";


    public OpenAIIntegrationService(String apiKey) {
        this.apiKey = apiKey;
    }

    public String generateText(String destinationName) {
        OpenAiService service = new OpenAiService(apiKey);
        CompletionRequest request = CompletionRequest.builder()
                .model(FREE_TEXT_MODEL)
                .prompt(PROMPT_MESSAGE.formatted(destinationName))
                .maxTokens(MAX_TOKENS)
                .build();

        return service.createCompletion(request).getChoices().get(0).getText();
    }
}
