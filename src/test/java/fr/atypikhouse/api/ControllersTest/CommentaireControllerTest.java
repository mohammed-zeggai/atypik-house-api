package fr.atypikhouse.api.ControllersTest;

import fr.atypikhouse.api.Repositories.CommentaireRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentaireControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenGetCommentaires_thenStatus200() throws Exception{
        mvc.perform(get("api/commentaires")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCommentairesId_thenStatus200() throws Exception{
        mvc.perform(get("api/commentaires/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
