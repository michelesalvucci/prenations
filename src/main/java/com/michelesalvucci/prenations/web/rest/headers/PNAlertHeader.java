package com.michelesalvucci.prenations.web.rest.headers;

import org.springframework.http.HttpHeaders;

public class PNAlertHeader {
    
    public static HttpHeaders createAlertHeader(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-prenations-alert", message);
        return headers;
    }
}
