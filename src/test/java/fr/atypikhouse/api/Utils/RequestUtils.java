package fr.atypikhouse.api.Utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RequestUtils {

    public static HttpHeaders buildHeadersWithToken() {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjoxNjg2NjgxNTY1fQ.d9Q2mpg9FBY377mTAHVxf7bsKV53wRfIvZXmdwIOLcj6V5rbK6W4bqLliIBN2DCRKhUWRt1errd60w6rfkOc-Q");

        return headers;
    }
}
