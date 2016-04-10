package com.melapelapp.service;

/**
 * Created by mcamacho on 4/8/16.
 */
public class StoreServiceFactory {

    private static StoreService storeService = new StoreServiceImpl();

    public static StoreService create() {
        return storeService;
    }



}
