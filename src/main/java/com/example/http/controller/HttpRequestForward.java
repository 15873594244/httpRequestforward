package com.example.http.controller;
import com.example.http.service.HttpRequestForwardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
public class HttpRequestForward {
    @Autowired
    private HttpRequestForwardService httpRequestForwardService;
    @PostMapping("/httpRequsetForward")
    public String getHttpRequestForward(@RequestBody Map<String,Object>map) throws JsonProcessingException {
        return httpRequestForwardService.getHttpRequestForward(map);
    }

}
