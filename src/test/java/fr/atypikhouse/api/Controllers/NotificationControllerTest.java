package fr.atypikhouse.api.Controllers;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.atypikhouse.api.Entities.Notification;
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

@WebMvcTest(fr.atypikhouse.api.Controllers.NotificationControllerTest.class)
public class NotificationControllerTest {

    private static final String END_POINT_PATH = "/notifications";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    //----------------------------------------------------------------------------------//
    // Test Get Notification
    @GetMapping("api/notifications/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        try {
            Notification notification = service.get(id);
            return ResponseEntity.ok(entity2Dto(notification));

        } catch (NotificationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testGetShouldReturn404NotFound() throws Exception {
        Integer notificationId = 6;
        String requestURI = END_POINT_PATH + "/" + notificationId;

        Mockito.when(service.get(notificationId)).thenThrow(NotificationNotFoundException.class);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 200
    @Test
    public void testGetShouldReturn200OK() throws Exception {
        Integer notificationId = 6;
        String requestURI = END_POINT_PATH + "/" + notificationId;

        Notification notification = new Notification()
                .id(notificationId);

        Mockito.when(service.get(notificationId)).thenReturn(notification);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notificationId)))
                .andDo(print());
    }

    MockHttpServletResponse:
    Status = 200
    Error message = null
    Headers = [Content-Type:MediaType.APPLICATION_JSON]
    Content type = MediaType.APPLICATION_JSON
    Body = {"id":6}



    //----------------------------------------------------------------------------------//
    // Test Update Notification
    @PutMapping("api/notifications/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid Notification notification) {
        try {
            notification.setId(id);
            Notification updateNotification = service.update(notification);
            return ResponseEntity.ok(entity2Dto(updateNotification));
        } catch (NotificationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testUpdateShouldReturn404NotFound() throws Exception {
        Integer notificationId = 6;
        String requestURI = END_POINT_PATH + "/" + notificationId;

        Notification notification = new Notification().id("6")
                .id(notificationId);

        Mockito.when(service.update(notification)).thenThrow(NotificationNotFoundException.class);

        String requestBody = objectMapper.writeValueAsString(notification);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 400
    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        Integer notificationId = 6;
        String requestURI = END_POINT_PATH + "/" + notificationId;

        Notification notification = new Notification().id("6")
                .id(notificationId);

        String requestBody = objectMapper.writeValueAsString(notification);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // 200
    @Test
    public void testUpdateShouldReturn200OK() throws Exception {
        Integer notificationId = 6;
        String requestURI = END_POINT_PATH + "/" + notificationId;

        Notification notification = new Notification().id("6")
                .id(notificationId);

        Mockito.when(service.update(notification)).thenReturn(notification);

        String requestBody = objectMapper.writeValueAsString(notification);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notificationId)))
                .andDo(print());
    }


    //----------------------------------------------------------------------------------//
    // Test Delete Notification
    @DeleteMapping("api/notifications/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();

        } catch (NotificationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        Integer notificationId = 6;
        String requestURI = END_POINT_PATH + "/" + notificationId;

        Mockito.doThrow(NotificationNotFoundException.class).when(service).delete(notificationId);;

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 200
    @Test
    public void testDeleteShouldReturn200OK() throws Exception {
        Integer notificationId = 6;
        String requestURI = END_POINT_PATH + "/" + notificationId;

        Mockito.doNothing().when(service).delete(notificationId);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
