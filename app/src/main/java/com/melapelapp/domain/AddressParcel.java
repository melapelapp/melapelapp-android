package com.melapelapp.domain;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mcamacho on 4/3/16.
 */
public class AddressParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<AddressParcel>() {

        @Override
        public AddressParcel createFromParcel(Parcel parcel) {
            return new AddressParcel(parcel);
        }

        @Override
        public AddressParcel[] newArray(int i) {
            return new AddressParcel[i];
        }
    };

    private Address address;

    public AddressParcel(Address address) {
        this.address = address;
    }

    public AddressParcel(Parcel parcel) {
        String[] data = new String[5];

        parcel.readStringArray(data);
        this.address.setAddressLine(0, data[0]);
        this.address.setAddressLine(1, data[0]);
        this.address.setLocality(data[0]);
        this.address.setAdminArea(data[0]);
        this.address.setPostalCode(data[0]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                address.getAddressLine(0), address.getAddressLine(1),
                address.getLocality(), address.getAdminArea(), address.getPostalCode()
        });
    }

    public Address getAddress() {
        return address;
    }
}
