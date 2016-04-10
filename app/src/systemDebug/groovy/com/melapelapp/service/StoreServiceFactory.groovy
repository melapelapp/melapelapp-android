package com.melapelapp.service

/**
 * Created by mcamacho on 4/8/16.
 */
class StoreServiceFactory {

    private static StoreService storeService = new StoreServiceStub()

    public static StoreService create() {
        return storeService
    }
}
