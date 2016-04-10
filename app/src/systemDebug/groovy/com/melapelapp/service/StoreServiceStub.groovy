package com.melapelapp.service

import com.melapelapp.domain.Address
import com.melapelapp.domain.Person
import com.melapelapp.domain.SignUpObject
import com.melapelapp.domain.User
import groovy.transform.CompileStatic

import static java.lang.Math.random
import static java.lang.Math.round

/**
 * Created by mcamacho on 4/4/16.
 */
@CompileStatic
class StoreServiceStub implements StoreService {
    static int FAIL_RANDOM = 0;
    static int NEVER_FAILS = 0;

    int policy = FAIL_RANDOM;

    @Override
    public void signupStore(SignUpObject signUpObject) throws ServerException {
        long rand = round(random() * 10) % 3

        if (policy == FAIL_RANDOM) {
        // 2 out of three time will succeed.
            if (rand == 2) {
                throw new ServerException("Error occurred in server: message")
            }
        }

        int randId = new Double(random() * 10000).intValue()

        Store.stores << [ id: randId, storeName: signUpObject.storeName, address: signUpObject.address ]
        Store.users << [ id: randId, storeId: randId, username: signUpObject.userName, password: signUpObject.password ]
    }

    @Override
    public User login(String username, String password) {
        Map user = Store.users.find { user -> ((Map) user).get("username") == username && ((Map) user).get("password") }
        if (user) {
            new User(username: (String) user.get("username"), id: (Integer) user.get("id"), storeId: (Integer) user.get("storeId"),
                    name: (String) user.get("name"), lastName: (String) user.get("lastName"))
        } else {
            return null
        }
    }

    @Override
    public List<Person> fetchPersons(Integer storeId) {
        def store = Store.stores.find { store -> ((Map) store).get("id") == storeId }
        return ((Map) store) ?.get("persons")
    }

    public static void main(String[] args) {
        StoreServiceStub serviceStub = new StoreServiceStub()
        serviceStub.policy = NEVER_FAILS

        User user = serviceStub.login("testuser@test.com", "lamisma")
        assert user && user.id && user.storeId && user.username && user.name && user.lastName

        List persons = serviceStub.fetchPersons(user.storeId)
        assert persons.size() == 10

        serviceStub.signupStore(
                new SignUpObject("storeX", "userX", "passwordX", new Address())
        )
        user = serviceStub.login("userX", "passwordX")
        //assert user.id && user.storeId && user.username && user.name && user.lastName
        assert user.id && user.storeId && user.username
    }
}