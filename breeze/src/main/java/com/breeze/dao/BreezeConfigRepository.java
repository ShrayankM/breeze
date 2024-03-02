package com.breeze.dao;

import com.breeze.constant.BreezeConstants.BreezeStatus;
import com.breeze.model.BreezeConfig;

import java.util.List;

public interface BreezeConfigRepository extends GenericDao {

    List<BreezeConfig> findAllByStatus(BreezeStatus status);
}
