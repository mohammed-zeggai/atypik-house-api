package fr.atypikhouse.api.Controllers;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.atypikhouse.api.Entities.Commentaire;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(fr.atypikhouse.api.Controllers.CommentaireControllerTest.class)
public class CommentaireControllerTest {

    private static final String END_POINT_PATH = "/commentaires";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    //----------------------------------------------------------------------------------//
    // Test Get Commentaire
    @GetMapping("api/commentaires/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        try {
            Commentaire commentaire = service.get(id);
            return ResponseEntity.ok(entity2Dto(commentaire));

        } catch (CommentaireNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testGetShouldReturn404NotFound() throws Exception {
        Integer commentaireId = 8;
        String requestURI = END_POINT_PATH + "/" + commentaireId;

        Mockito.when(service.get(commentaireId)).thenThrow(CommentaireNotFoundException.class);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 200
    @Test
    public void testGetShouldReturn200OK() throws Exception {
        Integer commentaireId = 8;
        String requestURI = END_POINT_PATH + "/" + commentaireId;

        Commentaire commentaire = new Commentaire()
                .id(commentaireId);

        Mockito.when(service.get(commentaireId)).thenReturn(commentaire);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(commentaireId)))
                .andDo(print());
    }

    MockHttpServletResponse:
    Status = 200
    Error message = null
    Headers = [Content-Type:MediaType.APPLICATION_JSON]
    Content type = application/json
    Body = {"id":8}



    //----------------------------------------------------------------------------------//
    // Test Update Commentaire
    @PutMapping("api/commentaires/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid Commentaire commentaire) {
        try {
            commentaire.setId(id);
            Commentaire updateCommentaire = service.update(commentaire);
            return ResponseEntity.ok(entity2Dto(updateCommentaire));
        } catch (CommentaireNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testUpdateShouldReturn404NotFound() throws Exception {
        Integer commentaireId = 8;
        String requestURI = END_POINT_PATH + "/" + commentaireId;

        Commentaire commentaire = new Commentaire().id("8")
                .id(commentaireId);

        Mockito.when(service.update(commentaire)).thenThrow(CommentaireNotFoundException.class);

        String requestBody = objectMapper.writeValueAsString(commentaire);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 400
    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        Integer commentaireId = 8;
        String requestURI = END_POINT_PATH + "/" + commentaireId;

        Commentaire commentaire = new Commentaire().id("8")
                .id(commentaireId);

        String requestBody = objectMapper.writeValueAsString(commentaire);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // 200
    @Test
    public void testUpdateShouldReturn200OK() throws Exception {
        Integer commentaireId = 8;
        String requestURI = END_POINT_PATH + "/" + commentaireId;

        Commentaire commentaire = new Commentaire().id("8")
                .id(commentaireId);

        Mockito.when(service.update(commentaire)).thenReturn(commentaire);

        String requestBody = objectMapper.writeValueAsString(commentaire);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentaireId)))
                .andDo(print());
    }


    //----------------------------------------------------------------------------------//
    // Test Delete Commentaire
    @DeleteMapping("api/commentaires/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();

        } catch (CommentaireNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        Integer commentaireId = 8;
        String requestURI = END_POINT_PATH + "/" + commentaireId;

        Mockito.doThrow(CommentaireNotFoundException.class).when(service).delete(commentaireId);;

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 200
    @Test
    public void testDeleteShouldReturn200OK() throws Exception {
        Integer commentaireId = 8;
        String requestURI = END_POINT_PATH + "/" + commentaireId;

        Mockito.doNothing().when(service).delete(commentaireId);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
