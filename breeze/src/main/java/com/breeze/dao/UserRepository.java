package com.breeze.dao;

import com.breeze.model.BreezeUser;

public interface UserRepository {

    BreezeUser getUserByCode(String userCode);

    BreezeUser getUserByEmail(String emailAddress);
}
