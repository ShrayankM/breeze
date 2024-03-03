package com.breeze.util;

import com.breeze.model.BreezeUserApproval;
import com.breeze.response.BookApprovalResponseList;
import com.breeze.response.BookApprovalResponseList.BookApprovalData;
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

    public static BookApprovalResponseList getApprovalResponseFromModel(List<BreezeUserApproval> modelList) {

        BookApprovalResponseList response = new BookApprovalResponseList();
        List<BookApprovalData> bookApprovalDataList = new ArrayList<>();
        for (BreezeUserApproval model : modelList) {

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
}
