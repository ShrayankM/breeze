package com.breeze.service;

import com.breeze.constant.BreezeDbConfigEnum;

public interface BreezeConfigService {

    Long getLongValue(BreezeDbConfigEnum dbConfigEnumName);

    Integer getIntegerValue(BreezeDbConfigEnum dbConfigEnumName);

    String getStringValue(BreezeDbConfigEnum dbConfigEnumName);

    <T> T getConfigValueOrDefault(BreezeDbConfigEnum dbConfigEnum, T defaultValue);
}
