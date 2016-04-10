package com.melapelapp.service;

import com.melapelapp.domain.Person;
import com.melapelapp.domain.SignUpObject;
import com.melapelapp.domain.User;

import java.util.List;

/**
 * Created by mcamacho on 4/2/16.
 */
public interface StoreService {

    public void signupStore(SignUpObject signUpObject) throws ServerException;

    public User login(String username, String password);

    public List<Person> fetchPersons(Integer storeId);
}
