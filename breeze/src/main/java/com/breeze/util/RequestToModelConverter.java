package com.breeze.util;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.IsbnType;
import com.breeze.constant.BreezeConstants.UserBookApprovalStatus;
import com.breeze.constant.BreezeDbConfigEnum;
import com.breeze.exception.BreezeException;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUserBookApproval;
import com.breeze.request.CreateBookApproval;
import com.breeze.response.GoogleBookResponse;
import com.breeze.service.BreezeConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class RequestToModelConverter {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(RequestToModelConverter.class);

    private static BreezeConfigService breezeConfigService;

    private static ObjectMapper objectMapper;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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
            logger.error("Error when converting object to JSON string message = {}", e.getMessage());
        }
        model.setApprovalStatus(UserBookApprovalStatus.SUBMITTED);

        return model;
    }

    public static BreezeBookDetails getBookDetailsFromApprovalRequest(BreezeUserBookApproval bookApprovalRequest) throws BreezeException {
        BreezeBookDetails model = new BreezeBookDetails();

        try {
            model = objectMapper.readValue(bookApprovalRequest.getData(), BreezeBookDetails.class);
        } catch (JsonProcessingException e) {
            logger.error("Exception while parsing json string to object message = {}", e.getMessage());
            return null;
        }

        // * set all the attributes for the model to persist
        model.setCode(
                MiscUtils.generateCodeForEntity(
                        BreezeConstants.BOOK_DETAILS_PREFIX,
                        breezeConfigService.getConfigValueOrDefault(BreezeDbConfigEnum.ENTITY_CODE_LENGTH, 12)
                )
        );

        return model;
    }

    public static BreezeBookDetails getBookDetailsFromGoogleBookResponse(GoogleBookResponse googleBookResponse) {
        BreezeBookDetails model = new BreezeBookDetails();

        // * set all the attributes for the model to persist
        model.setCode(
                MiscUtils.generateCodeForEntity(
                        BreezeConstants.BOOK_DETAILS_PREFIX,
                        breezeConfigService.getConfigValueOrDefault(BreezeDbConfigEnum.ENTITY_CODE_LENGTH, 12)
                )
        );

        GoogleBookResponse.Item item = googleBookResponse.getItems().get(0);
        model.setName(item.getVolumeInfo().getTitle());
        model.setAuthor(item.getVolumeInfo().getAuthors().get(0));

        // * set Isbn
        List<GoogleBookResponse.IndustryIdentifier> identifierList = item.getVolumeInfo().getIndustryIdentifiers();
        Map<String, String> identifierMap = identifierList.stream().collect(Collectors.toMap(
                        GoogleBookResponse.IndustryIdentifier::getType,
                        GoogleBookResponse.IndustryIdentifier::getIdentifier));

        model.setIsbn10(identifierMap.get(IsbnType.ISBN_10.name()) != null ? identifierMap.get(IsbnType.ISBN_10.name()) : null);
        model.setIsbn13(identifierMap.get(IsbnType.ISBN_13.name()) != null ? identifierMap.get(IsbnType.ISBN_13.name()) : null);

        try {
            model.setPublishedDate(formatter.parse(item.getVolumeInfo().getPublishedDate()));
        } catch (ParseException e) {
            logger.error("Error occurred while parsing published date in response");
            logger.error("Error message = {}", e);
        }

        model.setPages((long) item.getVolumeInfo().getPageCount());
        model.setCategory(item.getVolumeInfo().getCategories().get(0));
        model.setThumbnail(item.getVolumeInfo().getImageLinks().getThumbnail());
        model.setSmallThumbnail(item.getVolumeInfo().getImageLinks().getSmallThumbnail());

        if (item.getVolumeInfo().getDescription().length() > 255) {
            model.setDescription(item.getVolumeInfo().getDescription().substring(0, 255));
        } else {
            model.setDescription(item.getVolumeInfo().getDescription());
        }
        return model;
    }
}
