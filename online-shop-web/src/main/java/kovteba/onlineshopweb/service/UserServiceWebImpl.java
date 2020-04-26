package kovteba.onlineshopweb.service;

import kovteba.onlineshopcommon.pojo.User;
import kovteba.onlineshopcommon.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceWebImpl implements UserServiceWeb {

    private final Logger log = LoggerFactory.getLogger(UserServiceWebImpl.class);


    @Value("${api.url}")
    private String urlApi;

    private final RestTemplate restTemplate;

    public UserServiceWebImpl(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
    }

    @Override
    public String auth(JwtRequest authenticationRequest) {
        String url = urlApi + "/authenticate";
        ResponseEntity<String> response = restTemplate.postForEntity(url, authenticationRequest, String.class);
        return response.getBody();
    }

    @Override
    public User getUserByEmail(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity entity = new HttpEntity(headers);
        String url = urlApi + "/user/email";
        ResponseEntity<User> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                User.class);
        return response.getBody();
    }

}
