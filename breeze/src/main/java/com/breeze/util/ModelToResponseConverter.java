package com.breeze.util;

import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.response.BookApprovalList;
import com.breeze.response.BookApprovalList.BookApprovalData;
import com.breeze.response.BookDetailsResponse;
import com.breeze.response.BookListResponse;
import com.breeze.response.BookListResponse.BookData;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    @Lazy
    private ModelToResponseConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public static BookApprovalList getBookApprovalResponseFromModel(List<BreezeUserBookApproval> modelList) {

        BookApprovalList response = new BookApprovalList();
        List<BookApprovalData> bookApprovalDataList = new ArrayList<>();
        for (BreezeUserBookApproval model : modelList) {

            BookApprovalData bookApprovalData = new BookApprovalData();
            try {
                bookApprovalData = objectMapper.readValue(model.getData(), BookApprovalData.class);
            } catch (JsonProcessingException e) {
                logger.error("Error occurred when converting JSON string to object", e);
            }
            bookApprovalData.setCode(model.getCode());
            bookApprovalDataList.add(bookApprovalData);
        }
        response.setBookApprovalDataList(bookApprovalDataList);
        response.setCount(bookApprovalDataList.size());
        return response;
    }

    public static BookListResponse getBookListResponseFromModel(List<BreezeBookDetails> modelList) {
        BookListResponse response = new BookListResponse();
        List<BookData> bookDetailsList = new ArrayList<>();

        for (BreezeBookDetails bookDetails : modelList) {
            BookData bookDetailsData = new BookData();

            bookDetailsData.setCode(bookDetails.getCode());
            bookDetailsData.setName(bookDetails.getName());
            bookDetailsData.setCategory(bookDetails.getCategory());
            bookDetailsData.setIsbnSmall(bookDetails.getIsbn10());
            bookDetailsData.setIsbnLarge(bookDetails.getIsbn13());
            bookDetailsData.setAuthor(bookDetails.getAuthor());
            bookDetailsData.setThumbnail(bookDetails.getThumbnail());
            bookDetailsList.add(bookDetailsData);
        }
        response.setBookDetailsList(bookDetailsList);
        response.setCount(response.getBookDetailsList().size());
        return response;
    }

    public static BookDetailsResponse getBookDetailsResponseFromModel(BreezeBookDetails model) {
        BookDetailsResponse response = new BookDetailsResponse();

        response.setName(model.getName());
        response.setIsbnSmall(model.getIsbn10());
        response.setIsbnLarge(model.getIsbn13());
        response.setAuthor(model.getAuthor());
        response.setThumbnail(model.getThumbnail());
        response.setPublishedDate(model.getPublishedDate());
        response.setPages(model.getPages());
        response.setCategory(model.getCategory());
        response.setUserRating(model.getUserRating());
        response.setDescription(model.getDescription());

        return response;
    }
}
