package com.melapelapp.service;

import com.melapelapp.domain.SignUpObject;

/**
 * Created by mcamacho on 4/2/16.
 */
public interface UserService {

    public void signup(SignUpObject signUpObject) throws ServerException ;
}
