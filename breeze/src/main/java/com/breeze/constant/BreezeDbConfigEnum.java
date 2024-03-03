package com.breeze.constant;

import com.breeze.constant.BreezeConstants.ConfigDataType;

import static com.breeze.constant.BreezeConstants.ConfigDataType.*;

public enum BreezeDbConfigEnum {

    ENTITY_CODE_LENGTH(LONG);

    private ConfigDataType dataType;

    private BreezeDbConfigEnum(ConfigDataType dataType) {
        this.dataType = dataType;
    }

    public ConfigDataType getDataType() {
        return this.dataType;
    }
}
