package com.breeze.service.impl;

import com.breeze.constant.BreezeDbConfigEnum;
import com.breeze.response.GoogleBookResponse;
import com.breeze.service.BreezeConfigService;
import com.breeze.service.GoogleRestApiService;
import com.breeze.util.LoggerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
public class GoogleRestApiServiceImpl  implements GoogleRestApiService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(GoogleRestApiServiceImpl.class);

    @Autowired
    BreezeConfigService breezeConfigService;

    @Override
    public GoogleBookResponse getBookDataUsingIsbn(String isbn) {

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(breezeConfigService.getStringValue(BreezeDbConfigEnum.GOOGLE_BOOKS_API_URL))
                .queryParam("q", "isbn:" + isbn)
                .queryParam("key", breezeConfigService.getStringValue(BreezeDbConfigEnum.GOOGLE_BOOKS_API_HEADER_VALUE))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("key", breezeConfigService.getStringValue(BreezeDbConfigEnum.GOOGLE_BOOKS_API_HEADER_VALUE));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Object> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<GoogleBookResponse> response = restTemplate.exchange(uri.toUri(), HttpMethod.GET, entity,
                    new ParameterizedTypeReference<>() {
                    });

            return response.getBody();
        } catch (Exception e) {
            logger.error("Error while reading from service = {}", uri);
            logger.error("Exception = {}", e);
            return null;
        }
    }
}
