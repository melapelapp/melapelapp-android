package com.melapelapp.service;

import com.melapelapp.domain.Person;
import com.melapelapp.domain.SignUpObject;
import com.melapelapp.domain.User;

import java.util.List;

/**
 * Created by mcamacho on 4/8/16.
 */
public class StoreServiceImpl implements StoreService {
    @Override
    public void signupStore(SignUpObject signUpObject) throws ServerException {

    }

    @Override
    public User login(String username, String password) {
        return null;
    }

    @Override
    public List<Person> fetchPersons(Integer storeId) {
        return null;
    }
}
