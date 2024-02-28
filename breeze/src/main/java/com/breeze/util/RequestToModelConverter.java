package com.breeze.util;

import com.breeze.model.BreezeUserApproval;
import com.breeze.request.BookApprovalRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestToModelConverter {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(RequestToModelConverter.class);

    public static BreezeUserApproval createBookApprovalRequestToModel(BookApprovalRequest bookApprovalRequest) {
        BreezeUserApproval model = new BreezeUserApproval();

        // * set all the attributes for the model to persist

        return model;
    }
}
