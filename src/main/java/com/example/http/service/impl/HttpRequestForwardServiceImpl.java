package com.example.http.service.impl;

import com.example.http.service.HttpRequestForwardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Service
public class HttpRequestForwardServiceImpl implements HttpRequestForwardService {
    @Autowired
    private RestTemplate restTemplate;

    public String getHttpRequestForward(@RequestBody Map<String,Object>map) throws JsonProcessingException {

        String mName=(String)map.get("methodName");
        String host=(String)map.get("host");
        String url=(String)map.get("url");
        Object requestData=map.get("requestData");
        System.out.println(requestData);
        System.out.println(requestData instanceof Map);
        StringBuilder sb=new StringBuilder(host);
        sb.append(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if(mName.equals("GET")){
            HttpEntity request = new HttpEntity<>(requestData, headers);
            if(requestData instanceof Map){
                Set set=((Map<String,Object>)requestData).entrySet();
                Iterator<Map.Entry<String,String>> iterator=set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry data = iterator.next();
                    String key = (String) data.getKey();
                    String value = (String) data.getValue();
                    sb.append("?");
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                    sb.append("&");
                }
            }
            ResponseEntity<String> result=restTemplate.exchange( sb.substring(0,sb.length()-1),HttpMethod.GET,request,String.class);
            return result.getBody();
        }else if(mName.equals("POST")){
            ObjectMapper ob=new ObjectMapper();
            System.out.println(ob.writeValueAsString(requestData));
            HttpEntity<String> request = new HttpEntity<>(ob.writeValueAsString(requestData), headers);
            System.out.println(request);
            ResponseEntity<String> result=restTemplate.exchange( sb.toString(),HttpMethod.POST,request,String.class);
            return result.getBody();
        }else if(mName.equals("PUT")){
            HttpEntity request = new HttpEntity<>(null, headers);
            ResponseEntity<String> result=restTemplate.exchange( sb.toString(),HttpMethod.PUT,request,String.class);
            return result.getBody();
        }else if(mName.equals("DELETE")){
            HttpEntity<String> request = new HttpEntity<>(null, headers);
            ResponseEntity<String> result=restTemplate.exchange( sb.toString(),HttpMethod.DELETE,request,String.class);
            return result.getBody();
        }
        return "";
    }

}
