package com.melapelapp.domain;

/**
 * Created by mcamacho on 4/9/16.
 */
public class Address {
    private String addressLine0;
    private String addressLine1;
    private String locality; //city/poblacion
    private String adminArea; // state
    private String postalCode;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine0() {
        return addressLine0;
    }

    public void setAddressLine0(String addressLine0) {
        this.addressLine0 = addressLine0;
    }


}
