package fr.atypikhouse.api.Controllers;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.atypikhouse.api.Entities.Reservation;
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

@WebMvcTest(fr.atypikhouse.api.Controllers.ReservationControllerTest.class)
public class ReservationControllerTest {

    private static final String END_POINT_PATH = "/reservations";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    //----------------------------------------------------------------------------------//
    // Test Get Reservation
    @GetMapping("api/reservations/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        try {
            Reservation reservation = service.get(id);
            return ResponseEntity.ok(entity2Dto(reservation));

        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testGetShouldReturn404NotFound() throws Exception {
        Integer reservationId = 3;
        String requestURI = END_POINT_PATH + "/" + reservationId;

        Mockito.when(service.get(reservationId)).thenThrow(ReservationNotFoundException.class);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 200
    @Test
    public void testGetShouldReturn200OK() throws Exception {
        Integer reservationId = 3;
        String requestURI = END_POINT_PATH + "/" + reservationId;

        Reservation reservation = new Reservation()
                .id(reservationId);

        Mockito.when(service.get(reservationId)).thenReturn(reservation);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(reservationId)))
                .andDo(print());
    }

    MockHttpServletResponse:
    Status = 200;
    Error message = null;
    Headers = [Content-Type:MediaType.APPLICATION_JSON];
    Content type = MediaType.APPLICATION_JSON;
    Body = {"id":3}



    //----------------------------------------------------------------------------------//
    // Test Update Reservation
    @PutMapping("api/reservations/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid Reservation reservation) {
        try {
            reservation.setId(id);
            Reservation updateReservation = service.update(reservation);
            return ResponseEntity.ok(entity2Dto(updateReservation));
        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testUpdateShouldReturn404NotFound() throws Exception {
        Integer reservationId = 3;
        String requestURI = END_POINT_PATH + "/" + reservationId;

        Reservation reservation = new Reservation().id("3")
                .id(reservationId);

        Mockito.when(service.update(reservation)).thenThrow(ReservationNotFoundException.class);

        String requestBody = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 400
    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        Integer reservationId = 3;
        String requestURI = END_POINT_PATH + "/" + reservationId;

        Reservation reservation = new Reservation().id("3")
                .id(reservationId);

        String requestBody = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // 200
    @Test
    public void testUpdateShouldReturn200OK() throws Exception {
        Integer reservationId = 3;
        String requestURI = END_POINT_PATH + "/" + reservationId;

        Reservation reservation = new Reservation().id("3")
                .id(reservationId);

        Mockito.when(service.update(reservation)).thenReturn(reservation);

        String requestBody = objectMapper.writeValueAsString(reservation);

        mockMvc.perform(put(requestURI).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(reservationId)))
                .andDo(print());
    }


    //----------------------------------------------------------------------------------//
    // Test Delete Reservation
    @DeleteMapping("api/reservations/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();

        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 404
    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        Integer reservationId = 3;
        String requestURI = END_POINT_PATH + "/" + reservationId;

        Mockito.doThrow(ReservationNotFoundException.class).when(service).delete(reservationId);;

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // 200
    @Test
    public void testDeleteShouldReturn200OK() throws Exception {
        Integer reservationId = 3;
        String requestURI = END_POINT_PATH + "/" + reservationId;

        Mockito.doNothing().when(service).delete(reservationId);

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
