package com.melapelapp;

import com.melapelapp.domain.Person;

import java.util.ArrayList;

/**
 * Created by gesban on 1/10/2016.
 */
public class WebServiceMock {

    ArrayList<String> names;

    private WebServiceMock() {




    }



    private static WebServiceMock instance;

    public static WebServiceMock getInstance() {
        if (instance == null)
            instance = new WebServiceMock();
        return instance;
    }

    public static ArrayList<Person> getPersons() // Temporalmente en codigo duro
    {
        ArrayList<Person> persons;
        persons = new ArrayList<Person>();

        Person person0 = new Person(); person0.setId("85344") ; person0.setName("SANDRA ELOISA"); person0.setLastName("AGUILAR");
        Person person1 = new Person(); person1.setId("85345") ; person1.setName("JORGE"); person1.setLastName("AGUILAR");
        Person person2 = new Person(); person2.setId("85376") ; person2.setName("JORGE GUILLERMO"); person2.setLastName("ARAIZA");
        Person person3 = new Person(); person3.setId("85377") ; person3.setName("ROCIO"); person3.setLastName("ALVAREZ");
        Person person4 = new Person(); person4.setId("85413") ; person4.setName("FCO.JAVIER"); person4.setLastName("ALARCON");
        Person person5 = new Person(); person5.setId("85418") ; person5.setName("DAVID"); person5.setLastName("ANELL");
        Person person6 = new Person(); person6.setId("85442") ; person6.setName("ALEJANDRA"); person6.setLastName("ACUÑA");
        Person person7 = new Person(); person7.setId("85449") ; person7.setName("SERGIO"); person7.setLastName("ALMAGUER");
        Person person8 = new Person(); person8.setId("85461") ; person8.setName("MARIO"); person8.setLastName("ALMAGUER");
        Person person9 = new Person(); person9.setId("85475") ; person9.setName("EDUARDO"); person9.setLastName("ACEVEDO");
        Person person10 = new Person(); person10.setId("85496") ; person10.setName("RODRIGO"); person10.setLastName("AGUIRRE");
        Person person11 = new Person(); person11.setId("85565") ; person11.setName("MARTHA PATRICIA"); person11.setLastName("ARAUJO");
        Person person12 = new Person(); person12.setId("85581") ; person12.setName("ALEJANDRO"); person12.setLastName("ALMADA");
        Person person13 = new Person(); person13.setId("85631") ; person13.setName("JOSE ANTONIO"); person13.setLastName("ABUD");
        Person person14 = new Person(); person14.setId("85661") ; person14.setName("ARTURO"); person14.setLastName("ALMARAZ");
        Person person15 = new Person(); person15.setId("85696") ; person15.setName("RICARDO"); person15.setLastName("ALAMILLA");
        Person person16 = new Person(); person16.setId("85705") ; person16.setName("EDUARDO"); person16.setLastName("AGUILAR");
        Person person17 = new Person(); person17.setId("85734") ; person17.setName("LETICIA"); person17.setLastName("AZPILCUETA");

        persons.add(person0);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);
        persons.add(person5);
        persons.add(person6);
        persons.add(person7);
        persons.add(person8);
        persons.add(person9);
        persons.add(person10);
        persons.add(person11);
        persons.add(person12);
        persons.add(person13);
        persons.add(person14);
        persons.add(person15);
        persons.add(person16);
        persons.add(person17);


        return persons;
    }

}
