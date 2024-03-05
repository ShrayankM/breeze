package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants.Status;
import com.breeze.constant.BreezeDbConfigEnum;
import com.breeze.dao.BreezeConfigRepository;
import com.breeze.model.BreezeConfig;
import com.breeze.service.BreezeConfigService;
import com.breeze.util.LoggerWrapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BreezeConfigServiceImpl implements BreezeConfigService {

    @Autowired
    private BreezeConfigRepository breezeConfigRepository;

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(BreezeConfigServiceImpl.class);

    private Map<BreezeDbConfigEnum, Object> configHashMap = new HashMap<>();

    @PostConstruct
    private void postConstruct() {
        logger.info("Starting loading config from DB ....");
        configHashMap = loadConfig();
        logger.info("Completed loading config from DB ....");
    }

    private Map<BreezeDbConfigEnum, Object> loadConfig() {

        Map<BreezeDbConfigEnum, Object> tempConfigHashMap = new HashMap<>();

        List<BreezeConfig> activeConfigList = breezeConfigRepository.findAllByStatus(Status.ACTIVE);
        Map<String, BreezeConfig> activeConfigMap = activeConfigList.stream().collect(Collectors.toMap(BreezeConfig::getName, Function.identity()));
        List<BreezeDbConfigEnum> breezeDbConfigEnumList = List.of(BreezeDbConfigEnum.values());

        if (breezeDbConfigEnumList.size() > activeConfigList.size()) {
            logger.error("The enum config size = {} and db config size = {}", breezeDbConfigEnumList.size(), activeConfigList.size());
        }

        for (BreezeDbConfigEnum breezeDbConfigEnum : breezeDbConfigEnumList) {
            String configName = breezeDbConfigEnum.name();
            BreezeConfig config = activeConfigMap.get(configName);

            if (config == null) {
                logger.error("The db config not found for {}", configName);
                continue;
            }

            if (config.getValue() == null || config.getValue().isBlank()) {
                logger.error("The db config value is NULL or EMPTY for {}", configName);
                continue;
            }

            Object value = getObjectValue(config, breezeDbConfigEnum);
            tempConfigHashMap.put(breezeDbConfigEnum, value);
        }
        return tempConfigHashMap;
    }

    private Object getObjectValue(BreezeConfig config, BreezeDbConfigEnum breezeDbConfigEnum) {
        String dbValue = config.getValue();

        switch (breezeDbConfigEnum.getDataType()) {
            case BOOLEAN -> {
                if ("TRUE".equalsIgnoreCase(dbValue) || "FALSE".equalsIgnoreCase(dbValue)) {
                    return Boolean.valueOf(dbValue);
                }
                else {
                    logger.error("Incorrect Boolean value for config in DB for name = {}", config.getName());
                    return null;
                }
            }
            case LONG -> {
                return Long.valueOf(dbValue);
            }
            case DECIMAL -> {
                return Double.valueOf(dbValue);
            }
            case STRING_LIST -> {
                return Arrays.stream(dbValue.split(",")).toList();
            }
            default -> {
                return dbValue;
            }
        }
    }


    @Override
    public Long getLongValue(BreezeDbConfigEnum dbConfigEnumName) {
        return (Long) configHashMap.get(dbConfigEnumName);
    }

    @Override
    public <T> T getConfigValueOrDefault(BreezeDbConfigEnum dbConfigEnum, T defaultValue) {
        return (T) configHashMap.getOrDefault(dbConfigEnum, defaultValue);
    }
}
