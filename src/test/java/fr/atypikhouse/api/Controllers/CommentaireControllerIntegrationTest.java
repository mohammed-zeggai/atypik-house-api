package fr.atypikhouse.api.Controllers;

import fr.atypikhouse.api.Entities.Commentaire;
import fr.atypikhouse.api.Repositories.CommentaireRepository;
import fr.atypikhouse.api.Utils.RequestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = fr.atypikhouse.api.AtypikHouseApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentaireControllerIntegrationTest {

    private static final String END_POINT_PATH = "api/commentaires";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentaireRepository commentaireRepository;


    @Test
    public void testAddCommentaire() {
        Commentaire commentaire = new Commentaire();
        commentaire.setId(5);
        commentaire.setCommentaire("Magnifique");
        commentaire.setDate_ajout(new Date());
        commentaire.setDate_modification(new Date());

        HttpEntity<Commentaire> entity = new HttpEntity<>(commentaire, RequestUtils.buildHeadersWithToken());

        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + END_POINT_PATH + "/create", entity, String.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateLocation() {

        Commentaire oldCommentaire = commentaireRepository.findById(5).get();
        Commentaire commentaire = new Commentaire();
        commentaire.setId(6);
        commentaire.setCommentaire("Parfait");
        commentaire.setDate_ajout(new Date());
        commentaire.setDate_modification(new Date());

        HttpEntity<Commentaire> entity = new HttpEntity<>(commentaire, RequestUtils.buildHeadersWithToken());

        ResponseEntity<Commentaire> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/update/" + oldCommentaire.getId(), HttpMethod.PUT, entity, new ParameterizedTypeReference<Commentaire>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteCommentaire() {

        Commentaire commentaire = commentaireRepository.findById(6).get();

        HttpEntity<Commentaire> entity = new HttpEntity<>(RequestUtils.buildHeadersWithToken());

        ResponseEntity<Commentaire> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + END_POINT_PATH + "/delete/" + commentaire.getId(), HttpMethod.DELETE, entity, new ParameterizedTypeReference<Commentaire>() {});

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
