package com.breeze.constant;

import com.breeze.constant.BreezeConstants.ConfigDataType;

import static com.breeze.constant.BreezeConstants.ConfigDataType.*;

public enum BreezeDbConfigEnum {

    ENTITY_CODE_LENGTH(LONG),

    // Google book api configs
    GOOGLE_BOOKS_API_URL(STRING),
    GOOGLE_BOOKS_API_HEADER_KEY(STRING),
    GOOGLE_BOOKS_API_HEADER_VALUE(STRING),

    // Google book image configs
    GOOGLE_BOOK_IMAGE_BASE_URL(STRING),
    GOOGLE_BOOK_IMAGE_SMALL_URL(STRING),
    GOOGLE_BOOK_IMAGE_MEDIUM_URL(STRING),
    GOOGLE_BOOK_IMAGE_LARGE_URL(STRING);

    private ConfigDataType dataType;

    private BreezeDbConfigEnum(ConfigDataType dataType) {
        this.dataType = dataType;
    }

    public ConfigDataType getDataType() {
        return this.dataType;
    }
}
