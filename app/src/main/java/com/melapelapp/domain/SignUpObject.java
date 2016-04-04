package com.melapelapp.domain;

import android.location.Address;

/**
 * Created by mcamacho on 4/2/16.
 */
public class SignUpObject {
    private String storeName;
    private String userName;
    private String password;
    private Address address;

    public SignUpObject(String storeName, String userName, String password, Address address) {
        this.storeName = storeName;
        this.userName = userName;
        this.password = password;
        this.address = address;
    }
}
