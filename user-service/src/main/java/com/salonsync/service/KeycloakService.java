package com.salonsync.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakService {

      private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
      private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL+"admin/realms/master/users";

      private static final String TOKEN_URL = KEYCLOAK_BASE_URL+"/realms/master/protocol/openid-connect/token";

      private static final String CLIENT_ID = "salon-booking-client";
      private static final String CLIENT_SECRET = "uYMmaIV31FfEUXuY5uNCp3TIjJ9ro1dI";
      private static final String GRANT_TYPE = "password";
      private static final String scope = "openid profile email";
      private static final String username = "salonsync";
      private static final String password = "admin";
      private static final String clientId = "b247adf2-69b4-43a1-9f86-33f8703af1c4";
      

}
