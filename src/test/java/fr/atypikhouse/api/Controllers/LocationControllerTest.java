package fr.atypikhouse.api.Controllers;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.atypikhouse.api.Entities.Location;
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

@WebMvcTest(fr.atypikhouse.api.Controllers.LocationControllerTest.class)
public class LocationControllerTest {

    private static final String END_POINT_PATH = "/locations";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    //----------------------------------------------------------------------------------//
    // Test Get Location
    @GetMapping("api/locations/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        try {
            Location location = service.get(id);
            return ResponseEntity.ok(entity2Dto(location));

        } catch (LocationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testGetShouldReturn404NotFound() throws Exception {
        Integer locationId = 4;
        String requestURI = END_POINT_PATH + "/" + locationId;

        Mockito.when(service.get(locationId)).thenThrow(LocationNotFoundException.class);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 200
    @Test
    public void testGetShouldReturn200OK() throws Exception {
        Integer locationId = 4;
        String requestURI = END_POINT_PATH + "/" + locationId;

        Location location = new Location()
                .id(locationId);

        Mockito.when(service.get(locationId)).thenReturn(location);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)
                .andExpect(jsonPath("$.id", is(locationId)))
                .andDo(print()));
    }

    MockHttpServletResponse:
    Status = 200
    Error message = null
    Headers = [Content-Type:MediaType.APPLICATION_JSON]
    Content type = MediaType.APPLICATION_JSON
    Body = {"id":4}



    //----------------------------------------------------------------------------------//
    // Test Update Location
    @PutMapping("api/locations/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid Location location) {
        try {
            location.setId(id);
            Location updateLocation = service.update(location);
            return ResponseEntity.ok(entity2Dto(updateLocation));
        } catch (LocationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testUpdateShouldReturn404NotFound() throws Exception {
        Integer locationId = 4;
        String requestURI = END_POINT_PATH + "/" + locationId;

        Location location = new Location().id("4")
                .id(locationId);

        Mockito.when(service.update(location)).thenThrow(LocationNotFoundException.class);

        String requestBody = objectMapper.writeValueAsString(location);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 400
    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        Integer locationId = 4;
        String requestURI = END_POINT_PATH + "/" + locationId;

        Location location = new Location().id("4")
                .id(locationId);

        String requestBody = objectMapper.writeValueAsString(location);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // 200
    @Test
    public void testUpdateShouldReturn200OK() throws Exception {
        Integer locationId = 4;
        String requestURI = END_POINT_PATH + "/" + locationId;

        Location location = new Location().id("4")
                .id(locationId);

        Mockito.when(service.update(location)).thenReturn(location);

        String requestBody = objectMapper.writeValueAsString(location);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(locationId)))
                .andDo(print());
    }


    //----------------------------------------------------------------------------------//
    // Test Delete Location
    @DeleteMapping("api/locations/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();

        } catch (LocationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        Integer locationId = 4;
        String requestURI = END_POINT_PATH + "/" + locationId;

        Mockito.doThrow(LocationNotFoundException.class).when(service).delete(locationId);;

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 200
    @Test
    public void testDeleteShouldReturn200OK() throws Exception {
        Integer locationId = 4;
        String requestURI = END_POINT_PATH + "/" + locationId;

        Mockito.doNothing().when(service).delete(locationId);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
