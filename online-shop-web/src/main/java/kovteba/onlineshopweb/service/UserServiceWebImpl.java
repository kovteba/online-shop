package kovteba.onlineshopweb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kovteba.onlineshopcommon.pojo.User;
import kovteba.onlineshopcommon.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Service
public class UserServiceWebImpl implements UserServiceWeb {

    private final Logger log = LoggerFactory.getLogger(UserServiceWebImpl.class);


    @Value("${api.url}")
    private String urlApi;

    private final RestTemplate restTemplate;

    public UserServiceWebImpl(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        messageConverters.add(converter);
//        this.restTemplate.setMessageConverters(messageConverters);
    }

    @Override
    public String auth(JwtRequest authenticationRequest) {
        String url = urlApi + "/authenticate";
        ResponseEntity<String> response = restTemplate.postForEntity(url, authenticationRequest, String.class);
        return response.getBody();
    }

    @Override
    public User getUserByRole(String token, String Role) {
        return null;
    }

    @Override
    public User getUserByPhoneNumber(String token, String phoneNumber) {
        return null;
    }

    @Override
    public User addToBasket(String token, Long userId, Long productId, String count) {
        String url = urlApi + "/user/basket/" + userId + "/" + productId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity entity = new HttpEntity(count, headers);
        ResponseEntity<User> response = restTemplate.postForEntity(url, entity, User.class);
        return response.getBody();
    }

    @Override
    public void generateReceipt(String token, String userId) {

    }

    @Override
    public String banUserByEmail(String token, String email) {
        String url = urlApi + "/user/ban/" + email;
        return banAndUnBanUser(token, url);
    }

    @Override
    public String unBanUserByEmail(String token, String email) {
        String url = urlApi + "/user/unBan/" + email;
        return banAndUnBanUser(token, url);
    }

    private String banAndUnBanUser(String token, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);
        return response.getBody();
    }


    @Override
    public User getUserByEmail(String token) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity entity = new HttpEntity(headers);
        String url = urlApi + "/user/email";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        System.out.println("FFFFFFFFF : " + response.getBody());
        StringReader reader = new StringReader(Objects.requireNonNull(response.getBody()));
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(reader, User.class);

        return user;
    }

}
