package fr.atypikhouse.api.Security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final static long EXPIRATION_TIME = 864000000; // 10 jours
    private final static String SECRET = "SecretKeyToGenJWTs";
    private final static String HEADER_STRING = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer";

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException{
        try {
            // Recuperer les informations d'authentification
            fr.atypikhouse.api.Entities.User info = new ObjectMapper().readValue(req.getInputStream(), fr.atypikhouse.api.Entities.User.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    info.getEmail(),
                    info.getPassword(),
                    Collections.EMPTY_LIST
            );

            // Retourner un token d'authentification
            return authenticationManager.authenticate(token);
        } catch(IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        // Si l'authentification reussie, construire le jeton JWT
        String jsonToken = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        JWTTokenResponse response = new JWTTokenResponse(jsonToken);

        String json = new ObjectMapper().writeValueAsString(response);
        res.getWriter().write(json);
        res.flushBuffer();
    }
}