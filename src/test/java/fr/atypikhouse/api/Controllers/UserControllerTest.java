//package fr.atypikhouse.api.Controllers;
//
//import ch.qos.logback.core.status.Status;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import fr.atypikhouse.api.Entities.User;
//import fr.atypikhouse.api.Exceptions.UserNotFoundException;
//import fr.atypikhouse.api.Services.UserDetailsServiceImplementationTest;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.bind.annotation.*;
//
//
//import static net.bytebuddy.matcher.ElementMatchers.is;
//import static org.springframework.http.RequestEntity.put;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(fr.atypikhouse.api.Controllers.UserControllerTest.class)
//public class UserControllerTest {
//    private static final String END_POINT_PATH = "/users";
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private UserDetailsServiceImplementationTest userDetailsServiceImplementationTest;
//
//    //----------------------------------------------------------------------------------------------------------------//
//    // Test Get User
//    @GetMapping("api/users/{id}")
//    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
//        try {
//            User user = service.get(id);
//            return ResponseEntity.ok(entity2Dto(user));
//
//        } catch (UserNotFoundException e) {
//            e.printStackTrace();
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // 404
//    @Test
//    public void testGetShouldReturn404NotFound() throws Exception {
//        Integer userId = 2;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        Mockito.when(service.get(userId)).thenThrow(UserNotFoundException.class);
//
//        mockMvc.perform(get(requestURI))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    // 200
//    @Test
//    public void testGetShouldReturn200OK() throws Exception {
//        Integer userId = 2;
//        String requestURI = END_POINT_PATH + "/" + userId;
//        String email = "david.parker@gmail.com";
//
//        User user = new User().email(email)
//                .prenom("David").nom("Parker")
//                .password("avid808")
//                .id(userId);
//
//        Mockito.when(service.get(userId)).thenReturn(user);
//
//        mockMvc.perform(get(requestURI))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.email", is(email)))
//                .andDo(print());
//    }
//
//    MockHttpServletResponse:
//    Status : 200;
//    Error message = null;
//    Headers : [Content-Type:MediaType.APPLICATION_JSON];
//    Content type = MediaType.APPLICATION_JSON;
//    Body = {"id":2,"email":"david.parker@gmail.com","prenom":"David","nom":"Parker"}
//
//
//    //----------------------------------------------------------------------------------------------------------------//
//    // Test Update User
//    @PutMapping("api/users/update/{id}")
//    public ResponseEntity<?> update(@PathVariable("id") Integer id,
//                                    @RequestBody @Valid User user) {
//        try {
//            user.setId(id);
//            User updatedUser = service.update(user);
//            return ResponseEntity.ok(entity2Dto(updatedUser));
//        } catch (UserNotFoundException e) {
//            e.printStackTrace();
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // 404
//    @Test
//    public void testUpdateShouldReturn404NotFound() throws Exception {
//        Integer userId = 2;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        User user = new User().id("2").email("david.parker@gmail.com")
//                .prenom("David").nom("Parker")
//                .password("avid808")
//                .id(userId);
//
//        Mockito.when(service.update(user)).thenThrow(UserNotFoundException.class);
//
//        String requestBody = objectMapper.writeValueAsString(user);
//
//        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    // 400
//    @Test
//    public void testUpdateShouldReturn400BadRequest() throws Exception {
//        Integer userId = 2;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        User user = new User().id("2").email("david.parker")
//                .prenom("David").nom("Parker")
//                .password("avid808")
//                .id(userId);
//
//        String requestBody = objectMapper.writeValueAsString(user);
//
//        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }
//
//    // 200
//    @Test
//    public void testUpdateShouldReturn200OK() throws Exception {
//        Integer userId = 2;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        String email = "david.parker@gmail.com";
//        User user = new User().id("2").email(email)
//                .prenom("David").nom("Parker")
//                .password("avid808")
//                .id(userId);
//
//        Mockito.when(service.update(user)).thenReturn(user);
//
//        String requestBody = objectMapper.writeValueAsString(user);
//
//        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is(email)))
//                .andDo(print());
//    }
//
//
//    //----------------------------------------------------------------------------------------------------------------//
//    // Test Delete User
//    @DeleteMapping("api/users/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
//        try {
//            service.delete(id);
//            return ResponseEntity.noContent().build();
//
//        } catch (UserNotFoundException e) {
//            e.printStackTrace();
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // 404
//    @Test
//    public void testDeleteShouldReturn404NotFound() throws Exception {
//        Integer userId = 2;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        Mockito.doThrow(UserNotFoundException.class).when(service).delete(userId);;
//
//        mockMvc.perform(delete(requestURI))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    // 200
//    @Test
//    public void testDeleteShouldReturn200OK() throws Exception {
//        Integer userId = 2;
//        String requestURI = END_POINT_PATH + "/" + userId;
//
//        Mockito.doNothing().when(service).delete(userId);;
//
//        mockMvc.perform(delete(requestURI))
//                .andExpect(status().isNoContent())
//                .andDo(print());
//    }
//}
