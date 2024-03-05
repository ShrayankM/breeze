package com.breeze.util;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.UserBookApprovalStatus;
import com.breeze.constant.BreezeDbConfigEnum;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.request.CreateBookApproval;
import com.breeze.service.BreezeConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RequestToModelConverter {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(RequestToModelConverter.class);

    private static BreezeConfigService breezeConfigService;

    private static ObjectMapper objectMapper;

    @Autowired
    @Lazy
    private RequestToModelConverter(BreezeConfigService breezeConfigService) {
        RequestToModelConverter.breezeConfigService = breezeConfigService;
        RequestToModelConverter.objectMapper = new ObjectMapper();
    }

    public static BreezeUserBookApproval createBookApprovalRequestToModel(CreateBookApproval bookApprovalRequest) {
        BreezeUserBookApproval model = new BreezeUserBookApproval();

        // * set all the attributes for the model to persist
        model.setCode(
                MiscUtils.generateCodeForEntity(
                        BreezeConstants.USER_BOOK_APPROVAL_PREFIX,
                        breezeConfigService.getConfigValueOrDefault(BreezeDbConfigEnum.ENTITY_CODE_LENGTH, 12)
                )
        );
        model.setUserCode(bookApprovalRequest.getUserCode());
        try {
            model.setData(objectMapper.writeValueAsString(bookApprovalRequest.getBookApprovalData()));
        } catch (JsonProcessingException e) {
            logger.error("Error when converting object to JSON");
        }
        model.setApprovalStatus(UserBookApprovalStatus.SUBMITTED);

        return model;
    }
}
