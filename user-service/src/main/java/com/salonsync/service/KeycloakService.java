package com.salonsync.service;

import com.salonsync.payload.dto.Credential;
import com.salonsync.payload.dto.SignupDTO;
import com.salonsync.payload.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class KeycloakService {

      private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
      private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL+"admin/realms/master/users";

      private static final String TOKEN_URL = KEYCLOAK_BASE_URL+"/realms/master/protocol/openid-connect/token";

      private static final String CLIENT_ID = "salon-booking-client";
      private static final String CLIENT_SECRET = "HCoPXP6GGMNl38phwWbR2vD52BNsgoRc";
      private static final String GRANT_TYPE = "password";
      private static final String scope = "openid profile email";
      private static final String username = "salonsync";
      private static final String password = "admin";
      private static final String clientId = "9c9b3f51-19ad-42f6-b7cd-1290584686bc";
      

      private final RestTemplate restTemplate;


      public void createUser(SignupDTO signupDTO) throws Exception {

            String ACCESS_TOKEN = "";

            Credential credential = new Credential();
            credential.setTemporary(false);
            credential.setType("password");
            credential.setValue(signupDTO.getPassword());

            UserRequest userRequest = new UserRequest();
            userRequest.setUsername(signupDTO.getUsername());
            userRequest.setEmail(signupDTO.getEmail());
            userRequest.setEnabled(true);
            userRequest.setFirstName(signupDTO.getFirstName());
            userRequest.setLastName(signupDTO.getLastName());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(ACCESS_TOKEN);

            HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KEYCLOAK_ADMIN_API,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.CREATED) {
                  System.out.println("User created successfully");
            }

      }




}
