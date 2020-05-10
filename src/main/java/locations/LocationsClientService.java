package locations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class LocationsClientService {

    private RestTemplate template;

    private String url;

    public LocationsClientService(RestTemplate template, @Value("${locations.client.url}") String url) {
        this.template = template;
        this.url = url;
    }

    public void listLocations() {
        System.out.println("List locations");
        Page locations = template.getForObject(url + "/api/locations", Page.class);


        locations.getContent().forEach(l -> System.out.println(l.getName()));
    }
}
