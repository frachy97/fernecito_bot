package com.ferreyra.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelegramUtils {

    public static final String token = "2064148525:AAH-9Bre07ivSJJ3q4ExKL9Tl1EetGVEGDs";

    public static void sendQr(String chatID, String photoPath)
    {
        File img = new File(photoPath);
        // getting the file from disk
        FileSystemResource value = new FileSystemResource(new File(photoPath));

        // adding headers to the api
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("photo", value);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = date.format(formatter);
        String msg = "Gracias por tu compra "+formatDateTime;

        HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForEntity("https://api.telegram.org/bot"+token+"/sendPhoto?chat_id="+chatID+"&caption="+msg, requestEntity,
                String.class).getBody().toString();

        System.out.println(result);

    }
}
