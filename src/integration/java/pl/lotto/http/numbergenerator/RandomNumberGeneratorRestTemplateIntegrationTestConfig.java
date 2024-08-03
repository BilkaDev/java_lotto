package pl.lotto.http.numbergenerator;

import org.springframework.web.client.RestTemplate;
import pl.lotto.domain.numbergenerator.IRandomNumberGenerable;
import pl.lotto.infrastructure.numbergenerator.client.RandomGeneratorClientConfig;

public class RandomNumberGeneratorRestTemplateIntegrationTestConfig extends RandomGeneratorClientConfig {

    public IRandomNumberGenerable remoteNumberGeneratorClient(int port, int connectionTimeout, int readTimeout) {
        RestTemplate restTemplate = restTemplate(
                connectionTimeout,
                readTimeout,
                restTemplateResponseErrorHandler()
        );
        return remoteNumberGeneratorClient(restTemplate, "http://localhost", port);
    }
}
