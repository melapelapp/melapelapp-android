package com.melapelapp.service;

import com.melapelapp.domain.SignUpObject;

import static java.lang.Math.*;

/**
 * Created by mcamacho on 4/2/16.
 */
public class UserServiceStub implements UserService {

    @Override
    public void signup(SignUpObject signUpObject) throws ServerException {
        long rand = round(random() * 10) % 3;

        // 2 out of three time will succeed.
        if (rand == 2) {
            throw new ServerException("Error occurred in server: message");
        }
    }
}
