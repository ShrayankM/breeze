package com.breeze.util;

import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.response.BookApprovalList;
import com.breeze.response.BookApprovalList.BookApprovalData;
import com.breeze.response.BookListResponse;
import com.breeze.response.BookListResponse.BookDetailsData;
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
            bookApprovalDataList.add(bookApprovalData);
        }
        response.setBookApprovalDataList(bookApprovalDataList);
        return response;
    }

    public static BookListResponse getBookListResponseFromModel(List<BreezeBookDetails> modelList) {
        BookListResponse response = new BookListResponse();

        for (BreezeBookDetails bookDetails : modelList) {
            BookDetailsData bookDetailsData = new BookDetailsData();

            bookDetailsData.setBookName(bookDetails.getBookName());
            bookDetailsData.setGenre(bookDetails.getBookGenre());
            bookDetailsData.setIsbn(bookDetails.getIsbn());
            bookDetailsData.setAuthorName(bookDetails.getAuthorName());
            bookDetailsData.setS3ImageLink(bookDetails.getS3ImageLink());
            response.getBookDetailsList().add(bookDetailsData);
        }
        return response;
    }
}
