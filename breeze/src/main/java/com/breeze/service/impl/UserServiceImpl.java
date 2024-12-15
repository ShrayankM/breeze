package com.breeze.service.impl;

import com.breeze.constant.BreezeConstants;
import com.breeze.constant.BreezeErrorCodes;
import com.breeze.dao.BookRepository;
import com.breeze.dao.GenericDao;
import com.breeze.dao.UserRepository;
import com.breeze.exception.BreezeException;
import com.breeze.exception.ResourceNotFoundException;
import com.breeze.exception.ValidationException;
import com.breeze.model.BreezeUser;
import com.breeze.model.BreezeUserBook;
import com.breeze.request.CreateUpdateUserRequest;
import com.breeze.request.LoginUserRequest;
import com.breeze.response.UserResponse;
import com.breeze.service.UserService;
import com.breeze.util.LoggerWrapper;
import com.breeze.util.ModelToResponseConverter;
import com.breeze.util.RequestToModelConverter;
import com.breeze.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(UserServiceImpl.class);

    @Autowired
    RequestValidator requestValidator;

    @Autowired
    GenericDao genericDao;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public UserResponse createUser(CreateUpdateUserRequest request) throws BreezeException {
        requestValidator.validate(request);

        // TODO: custom-validation to be added to check if username, password, email is correct

        // TODO check if user with email already exists
        BreezeUser breezeUser = userRepository.getUserByEmail(request.getEmailAddress());
        if (breezeUser == null) {
            breezeUser = RequestToModelConverter.getBreezeUserFromRequest(request);
            genericDao.create(breezeUser);
        } else {
            logger.info("User with email already exists skipping creating new user");
        }
        return ModelToResponseConverter.getUserFromModel(breezeUser);
    }

    @Override
    public UserResponse loginUser(LoginUserRequest request) throws BreezeException {
        requestValidator.validate(request);

        BreezeUser breezeUser = userRepository.getUserByEmail(request.getEmailAddress());
        if (breezeUser == null) {
            logger.error("User not found with email {}", request.getEmailAddress());
            throw new ResourceNotFoundException(BreezeErrorCodes.USER_NOT_FOUND_ERROR_CODE,
                    BreezeErrorCodes.USER_NOT_FOUND_ERROR_MSG);
        }

//        if (!breezeUser.getPassword().equals(request.getPassword())) {
//            logger.error("Password is incorrect for user {}", breezeUser.getEmailAddress());
//            throw new ValidationException(BreezeErrorCodes.INVALID_PASSWORD_FOR_USER_ERROR_CODE,
//                    BreezeErrorCodes.INVALID_PASSWORD_FOR_USER_ERROR_MSG);
//        }
        return ModelToResponseConverter.getUserFromModel(breezeUser);
    }

    @Override
    public UserResponse fetchUserProfile(String userCode) throws BreezeException {
        if (Objects.isNull(userCode) || !StringUtils.hasText(userCode)) {
            logger.error("User code in request is null or empty");
            throw new ValidationException(BreezeErrorCodes.INVALID_USER_CODE,
                    BreezeErrorCodes.INVALID_USER_CODE_MSG);
        }

        BreezeUser breezeUser = userRepository.getUserByCode(userCode);
        if (breezeUser == null) {
            logger.error("User not found with code {}", userCode);
            throw new ResourceNotFoundException(BreezeErrorCodes.USER_NOT_FOUND_ERROR_CODE,
                    BreezeErrorCodes.USER_NOT_FOUND_ERROR_MSG);
        }

        List<BreezeUserBook> breezeUserBookList = bookRepository.getListOfBooksForUser(userCode,
                null);

        Long readingCount = breezeUserBookList.stream()
                .map(ub -> BreezeConstants.BookStatus.READING.equals(ub.getBookStatus()) ? 1L : 0)
                .reduce(0L, Long::sum);

        Long completedCount = breezeUserBookList.stream()
                .map(ub -> BreezeConstants.BookStatus.COMPLETED.equals(ub.getBookStatus()) ? 1L : 0)
                .reduce(0L, Long::sum);

        Long wishListCount = breezeUserBookList.stream()
                .map(ub -> BreezeConstants.BookStatus.WISHLIST.equals(ub.getBookStatus()) ? 1L : 0)
                .reduce(0L, Long::sum);

        Long libraryCount = breezeUserBookList.stream()
                .map(ub -> BreezeConstants.BookStatus.LIBRARY.equals(ub.getBookStatus()) ? 1L : 0)
                .reduce(0L, Long::sum);
        libraryCount = libraryCount + readingCount + completedCount;

        UserResponse userResponse = ModelToResponseConverter.getUserFromModel(breezeUser);
        userResponse.setReadingBookCount(readingCount);
        userResponse.setCompletedBookCount(completedCount);
        userResponse.setWishlistedBookCount(wishListCount);
        userResponse.setTotalBooksInLibrary(libraryCount);

        return userResponse;
    }
}
