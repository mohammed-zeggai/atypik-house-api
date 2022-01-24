package fr.atypikhouse.api.Security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JWTTokenResponse extends ResponseEntity<String> {

    public JWTTokenResponse(String body) {
        super(body, HttpStatus.OK);
    }
}
