package fr.atypikhouse.api.Utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RequestUtils {

    public static HttpHeaders buildHeadersWithToken() {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJleHAiOjE3NTIxNzY2MzR9.cdGyvh0ULC40XZN-2E-yk17czcpuVvQirX4FRmNRZ5dfHQnnveM3afzsAV8cLpGUuUCsRuPVjeksOj5W-KLRfA");

        return headers;
    }
}
