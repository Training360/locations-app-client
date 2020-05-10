package locations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@SpringBootApplication
@Slf4j
public class LocationsAppClientApplication implements CommandLineRunner {

    @Autowired
    private LocationsClientService service;

    public static void main(String[] args) {
        SpringApplication.run(LocationsAppClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,
                                     @Value("${locations.client.proxy.enabled}") boolean proxyEnabled,
                                     @Value("${locations.client.proxy.host}") String proxyHost,
                                     @Value("${locations.client.proxy.port}") int proxyPort) {
		if (proxyEnabled) {
		    log.info("Using proxy: {} {}", proxyHost, proxyPort);
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			requestFactory.setProxy(proxy);
			return builder.requestFactory(() -> requestFactory).build();
		}
		else {
		    log.info("Don't using proxy");
			return builder.build();
		}
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }


    @Override
    public void run(String... args) throws Exception {
        service.listLocations();
    }


}
