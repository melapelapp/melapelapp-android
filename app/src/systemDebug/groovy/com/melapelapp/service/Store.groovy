package com.melapelapp.service

import com.melapelapp.domain.Address
import com.melapelapp.domain.Person
import groovy.transform.CompileStatic

/**
 * Created by mcamacho on 4/7/16.
 */
@CompileStatic
class Store {
    static Integer storeId = 11235813
    static Integer userId = 11235813

    public static List stores = [
            [
                id: storeId ,
                storeName: "defaultStore",
                persons: [
                        new Person(id: "85344", name: "SANDRA ELOISA",          lastName: "AGUILAR"),
                        new Person(id: "85345", name: "JORGE",                  lastName: "AGUILAR"),
                        new Person(id: "85376", name: "JORGE GUILLERMO",        lastName: "ARAIZA"),
                        new Person(id: "85377", name: "ROCIO",                  lastName: "ALVAREZ"),
                        new Person(id: "85413", name: "FCO.JAVIER",             lastName: "ALARCON"),
                        new Person(id: "85418", name: "DAVID",                  lastName: "ANELL"),
                        new Person(id: "85442", name: "ALEJANDRA",              lastName: "ACUÑA"),
                        new Person(id: "85449", name: "SERGIO",                 lastName: "ALMAGUER"),
                        new Person(id: "85461", name: "MARIO",                  lastName: "ALMAGUER"),
                        new Person(id: "85475", name: "EDUARDO",                lastName: "ACEVEDO"),
                ],
                address: new Address(addressLine0: "854 Charles Allen Dr", locality: "Atlanta", adminArea: "GA", postalCode: "30308")
            ],
    ]

    public static List users = [
            [ id: userId, storeId: storeId, username: "testuser@test.com", password: "lamisma", name:  "store", lastName: "owner"]
    ]

}