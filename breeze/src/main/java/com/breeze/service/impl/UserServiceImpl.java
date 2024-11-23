package com.breeze.service.impl;

import com.breeze.dao.GenericDao;
import com.breeze.exception.BreezeException;
import com.breeze.model.BreezeUser;
import com.breeze.request.CreateUpdateUserRequest;
import com.breeze.response.UserResponse;
import com.breeze.service.UserService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.ModelToResponseConverter;
import com.breeze.util.RequestToModelConverter;
import com.breeze.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(UserServiceImpl.class);

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    GenericDao genericDao;

    @Override
    public UserResponse createUser(CreateUpdateUserRequest request) throws BreezeException {
        requestValidator.validate(request);

        // TODO: custom-validation to be added to check if username, password, email is correct

        BreezeUser breezeUser = RequestToModelConverter.getBreezeUserFromRequest(request);
        if (breezeUser != null) {
            genericDao.create(breezeUser);
            return ModelToResponseConverter.getUserFromModel(breezeUser);
        } else {
            logger.error("Failed to create user");
        }
        return null;
    }
}
