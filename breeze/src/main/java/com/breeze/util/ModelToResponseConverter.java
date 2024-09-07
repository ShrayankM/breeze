package com.breeze.util;

import com.breeze.constant.BreezeDbConfigEnum;
import com.breeze.model.BreezeBookDetails;
import com.breeze.response.BookDataResponse;
import com.breeze.response.BookDetailsResponse;
import com.breeze.service.BreezeConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelToResponseConverter {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(ModelToResponseConverter.class);

    private static ObjectMapper objectMapper;

    private static BreezeConfigService breezeConfigService;

    @Autowired
    @Lazy
    private ModelToResponseConverter(BreezeConfigService breezeConfigService) {
        this.objectMapper = new ObjectMapper();
        this.breezeConfigService = breezeConfigService;
    }

    public static List<BookDataResponse> getBookListResponseFromModel(List<BreezeBookDetails> modelList) {
        List<BookDataResponse> bookDetailsList = new ArrayList<>();

        for (BreezeBookDetails bookDetails : modelList) {
            BookDataResponse bookDetailsData = new BookDataResponse();

            bookDetailsData.setCode(bookDetails.getCode());
            bookDetailsData.setName(bookDetails.getName());
            bookDetailsData.setCategory(bookDetails.getCategory());
            bookDetailsData.setIsbnSmall(bookDetails.getIsbn10());
            bookDetailsData.setIsbnLarge(bookDetails.getIsbn13());
            bookDetailsData.setAuthor(bookDetails.getAuthor());
//            bookDetailsData.setThumbnail(bookDetails.getThumbnail());

            // creating thumbnail url (small) for list response
            String thumbnailUrl = breezeConfigService.getStringValue(BreezeDbConfigEnum.GOOGLE_BOOK_IMAGE_BASE_URL) +
                    bookDetails.getGoogleId() +
                    breezeConfigService.getStringValue(BreezeDbConfigEnum.GOOGLE_BOOK_IMAGE_SMALL_URL);
            bookDetailsData.setThumbnail(thumbnailUrl);

            bookDetailsList.add(bookDetailsData);
        }
        return bookDetailsList;
    }

    public static BookDetailsResponse getBookDetailsResponseFromModel(BreezeBookDetails model) {
        BookDetailsResponse response = new BookDetailsResponse();

        response.setName(model.getName());
        response.setIsbnSmall(model.getIsbn10());
        response.setIsbnLarge(model.getIsbn13());
        response.setAuthor(model.getAuthor());
//        response.setThumbnail(model.getThumbnail());

        // creating thumbnail url (small) for list response
        String thumbnailUrl = breezeConfigService.getStringValue(BreezeDbConfigEnum.GOOGLE_BOOK_IMAGE_BASE_URL) +
                model.getGoogleId() +
                breezeConfigService.getStringValue(BreezeDbConfigEnum.GOOGLE_BOOK_IMAGE_LARGE_URL);
        response.setThumbnail(thumbnailUrl);

        response.setPublishedDate(model.getPublishedDate());
        response.setPages(model.getPages());
        response.setCategory(model.getCategory());
        response.setUserRating(model.getUserRating());
        response.setDescription(model.getDescription());

        return response;
    }
}
