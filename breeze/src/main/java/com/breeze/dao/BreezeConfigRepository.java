package com.breeze.dao;

import com.breeze.constant.BreezeConstants.Status;
import com.breeze.model.BreezeConfig;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BreezeConfigRepository extends GenericDao {

    List<BreezeConfig> findAllByStatus(Status status);
}
