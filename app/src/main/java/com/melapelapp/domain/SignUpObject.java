package com.melapelapp.domain;

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

    public String getStoreName() {
        return storeName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }
}
