package com.codedash.fetch;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FetchService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> fetchGithub(String username) {
        String url = "https://api.github.com/users/" + username;

        ResponseEntity<Map> response =
            restTemplate.getForEntity(url, Map.class);

        return response.getBody();
    }
}