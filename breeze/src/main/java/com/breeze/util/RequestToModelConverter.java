package com.breeze.util;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeConstants.IsbnType;
import com.breeze.constant.BreezeDbConfigEnum;
import com.breeze.model.BreezeBookDetails;
import com.breeze.model.BreezeUser;
import com.breeze.model.BreezeUserBook;
import com.breeze.request.CreateUpdateUserBookRequest;
import com.breeze.request.CreateUpdateUserRequest;
import com.breeze.response.GoogleBookResponse;
import com.breeze.service.BreezeConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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

    public static BreezeBookDetails getBookDetailsFromGoogleBookResponse(GoogleBookResponse googleBookResponse) {
        BreezeBookDetails model = new BreezeBookDetails();

        // Set all the attributes for the model to persist
        model.setCode(
                MiscUtils.generateCodeForEntity(
                        BreezeConstants.BOOK_DETAILS_PREFIX,
                        breezeConfigService.getLongValue(BreezeDbConfigEnum.ENTITY_CODE_LENGTH).intValue()
                )
        );

        if (googleBookResponse == null || CollectionUtils.isEmpty(googleBookResponse.getItems())) {
            logger.error("Google book response or its items list is null or empty");
            return null;
        }

        GoogleBookResponse.Item item = googleBookResponse.getItems().get(0);
        if (item == null) {
            logger.error("Item in google book response is null");
            return null;
        }

        model.setGoogleId(item.getId());

        GoogleBookResponse.VolumeInfo volumeInfo = item.getVolumeInfo();
        if (volumeInfo == null) {
            logger.error("VolumeInfo in item is null");
            return null;
        }

        model.setName(volumeInfo.getTitle());
        model.setSubtitle(volumeInfo.getSubtitle());

        if (!CollectionUtils.isEmpty(volumeInfo.getAuthors())) {
            model.setAuthor(volumeInfo.getAuthors().get(0));
        } else {
            logger.error("Authors list is null or empty");
        }

        // Set ISBN
        List<GoogleBookResponse.IndustryIdentifier> identifierList = volumeInfo.getIndustryIdentifiers();
        if (identifierList != null) {
            Map<String, String> identifierMap = identifierList.stream().collect(Collectors.toMap(
                    GoogleBookResponse.IndustryIdentifier::getType,
                    GoogleBookResponse.IndustryIdentifier::getIdentifier));

            model.setIsbn10(identifierMap.get(IsbnType.ISBN_10.name()));
            model.setIsbn13(identifierMap.get(IsbnType.ISBN_13.name()));
        } else {
            logger.error("Industry Identifiers list is null");
        }

        try {
            if (volumeInfo.getPublishedDate() != null) {
                model.setPublishedDate(formatter.parse(volumeInfo.getPublishedDate()));
            } else {
                logger.error("Published Date is null");
            }
        } catch (ParseException e) {
            logger.error("Error occurred while parsing published date in response", e);
        }

        if (volumeInfo.getPageCount() != null) {
            model.setPages((long) volumeInfo.getPageCount());
        } else {
            logger.error("PageCount is null");
        }

        if (!CollectionUtils.isEmpty(volumeInfo.getCategories())) {
            model.setCategory(volumeInfo.getCategories().get(0));
        } else {
            logger.error("Categories list is null or empty");
        }

        GoogleBookResponse.ImageLinks imageLinks = volumeInfo.getImageLinks();
        if (imageLinks != null) {
            model.setThumbnail(imageLinks.getThumbnail());
            model.setSmallThumbnail(imageLinks.getSmallThumbnail());
        } else {
            logger.error("ImageLinks is null");
        }

        model.setDescription(volumeInfo.getDescription());

        if (volumeInfo.getLanguage() != null) {
            model.setLanguage(volumeInfo.getLanguage().toUpperCase());
        } else {
            logger.error("Language is null");
        }
        return model;
    }


    public static BreezeUserBook getUserBookFromRequest(CreateUpdateUserBookRequest request) {
        BreezeUserBook model = new BreezeUserBook();

        // * set all the attributes for the model to persist
        model.setCode(
                MiscUtils.generateCodeForEntity(
                        BreezeConstants.USER_BOOK_PREFIX,
                        breezeConfigService.getLongValue(BreezeDbConfigEnum.ENTITY_CODE_LENGTH).intValue()
                )
        );
        model.setBookCode(request.getBookCode());
        model.setUserCode(request.getUserCode());
        model.setBookStatus(request.getBookStatus());
        model.setIsDeleted(false);
        model.setWishlist(false);
        model.setUserRating(0L);
        model.setCurrentPage(0L);
        model.setCompletedCount(0L);

        return model;
    }

    public static BreezeUser getBreezeUserFromRequest(CreateUpdateUserRequest request) {
        BreezeUser model = new BreezeUser();

        // * set all the attributes for the model to persist
        model.setCode(
                MiscUtils.generateCodeForEntity(
                        BreezeConstants.USER_PREFIX,
                        breezeConfigService.getLongValue(BreezeDbConfigEnum.ENTITY_CODE_LENGTH).intValue()
                )
        );

        model.setUserName(request.getUserName());
        model.setEmailAddress(request.getEmailAddress());
        model.setUserId(request.getUserId());
        model.setIsEmailVerified(false);
        model.setIsPhoneVerified(false);
        return model;
    }
}
