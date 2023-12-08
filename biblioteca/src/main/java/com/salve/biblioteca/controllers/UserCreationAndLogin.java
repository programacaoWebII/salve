package com.salve.biblioteca.controllers;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salve.biblioteca.entities.auxEntity.User;
import com.salve.biblioteca.entities.auxEntity.UserCreated;




@RequestMapping("/")
@RestController
public class UserCreationAndLogin {
    @PostMapping("index")
    public ResponseEntity<String> token(@RequestBody User user){
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", user.clientId);
        formData.add("grant_type", user.grantType);
        formData.add("username",user.username);
        formData.add("password",user.password);
        HttpEntity<MultiValueMap<String,String>>entity = new HttpEntity<>(formData,headers);
        var result = rt.postForEntity("http://localhost:8080/realms/SALVE/protocol/openid-connect/token", entity, String.class);
        return result;
    }
    @PutMapping("/")
    public ResponseEntity<String> criarConta(@RequestBody UserCreated user) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", "admin-cli");
        formData.add("grant_type", "password");
        formData.add("username", "admin");
        formData.add("password", "admin");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/realms/master/protocol/openid-connect/token", request, String.class);

        JSONObject jsonObj = new JSONObject(response.getBody());
        String accessToken = jsonObj.getString("access_token");

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity("http://localhost:8080/admin/realms/SALVE/users", entity, String.class);
    }
    
}
