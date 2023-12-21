package com.salve.biblioteca.controllers;

import org.hibernate.engine.internal.Collections;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private String refresh_token;
    @PostMapping("login")
    public ResponseEntity<String> token(@RequestBody User user){
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getClientId());
        System.out.println(user.getGrantType());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", user.getClientId());
        formData.add("grant_type", user.getGrantType());
        formData.add("username",user.getUsername());
        formData.add("password",user.getPassword());
        HttpEntity<MultiValueMap<String,String>>entity = new HttpEntity<>(formData,headers);
        var result = rt.postForEntity("http://localhost:8080/realms/SALVE/protocol/openid-connect/token", entity, String.class);
        return result;
    }
    @PostMapping("create")
    public ResponseEntity<String> criarConta(UserCreated user, Model model) throws Exception {
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
        this.refresh_token = jsonObj.getString("refresh_token");
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
      
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        try {
            response = restTemplate.postForEntity("http://localhost:8080/admin/realms/SALVE/users", entity, String.class);
            User user_temp = new User();
            user_temp.setUsername(user.getUsername());
            user_temp.setPassword(user.getCredentials().get(0).getValue());
            //jsonObj = new JSONObject(token(user_temp).getBody());
            
            deslogarAdm();
            return  token(user_temp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            return null;
       
        
    }


    @GetMapping("/user")
    public java.util.Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    public void deslogarAdm(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", "admin-cli");
        formData.add("refresh_token",refresh_token);
        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(formData, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8080/realms/master/protocol/openid-connect/logout", request, String.class);
    }
}
