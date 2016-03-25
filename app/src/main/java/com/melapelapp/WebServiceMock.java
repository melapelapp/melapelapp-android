package com.melapelapp;

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

    public ArrayList<String> getNames()
    {
        names = new ArrayList<String>();

        names.add("AARON GABRIEL");
        names.add("ACACIO");
        names.add("ADMINISTRADOR");
        names.add("ADRIA");
        names.add("ADRIAN");
        names.add("ADRIANA");
        names.add("ADRIANA G.");
        names.add("ALAN DAVID");
        names.add("ALEJANDRA");
        names.add("ALEJANDRO");
        names.add("ALFREDO");
        names.add("ALMA LILIA");
        names.add("AMANDA");
        names.add("ANA MARIA");
        names.add("ANDRES");
        names.add("ANDRES C");
        names.add("ANGELICA");
        names.add("ANTONIO");
        names.add("ARMANDO");
        names.add("ARTURO");
        names.add("ASTRID");
        names.add("BLANCA PAOLA");
        names.add("BRENDA");
        names.add("BRUNO");
        names.add("CARLOS");
        names.add("CARLOS ALBERTO");
        names.add("CARLOS EDUARDO");
        names.add("CARMELO");
        names.add("CECILIA");
        names.add("CESAR OCTAVIO");
        names.add("CHRISTIAHN");
        names.add("CHRISTIAN IVAN");
        names.add("CLAUDIA");
        names.add("CLAUDIA JASMYN");
        names.add("CONSUELO");
        names.add("CRISTINA");
        names.add("CRUZ HECTOR");
        names.add("CYNTHIA IXCHEL");
        names.add("DANTE JAVIER");
        names.add("DAVID");
        names.add("DAVID ANDRES");
        names.add("DAVID JULIAN");
        names.add("DENISE FERNANDA");
        names.add("DIANA");
        names.add("DIEGO EDGAR");
        names.add("EDGARDO");
        names.add("EDMUNDO");
        names.add("EDUARDO");
        names.add("ELBA");
        names.add("ELSA KARINA");
        names.add("ENRIQUE");
        names.add("ENRIQUE A.");
        names.add("ERICK");
        names.add("ERIK ALFONSO");
        names.add("ERNESTO");
        names.add("ESTEBAN");
        names.add("ESTELA");
        names.add("EUGENIA");
        names.add("EUNICE");
        names.add("EVADIO");
        names.add("FCO. JAVIER");
        names.add("FELIPE");
        names.add("FELIPE DE JESUS");
        names.add("FELIX");
        names.add("FERNANDO");
        names.add("FLORENCIO");
        names.add("FRANCISCO");
        names.add("FRANCISCO J.");
        names.add("FRANCISCO JAVIER");
        names.add("FRANCISCO REFIGIO");
        names.add("FRANSISCO RICARDO");
        names.add("GABRIEL");
        names.add("GABRIELA");
        names.add("GENARO DANIEL");
        names.add("GERARDO");
        names.add("GERARDO JOAQUIN");
        names.add("GERMAN");
        names.add("GLORIA");
        names.add("GLORIA ADRIANA");
        names.add("GLORIA ALEJANDRA");
        names.add("GONZALO");
        names.add("GUADALUPE");
        names.add("GUADALUPE RAMON");
        names.add("GUILLERMINA");
        names.add("GUILLERMO");
        names.add("GUSTAV");
        names.add("GUSTAVO EDUARDO");
        names.add("HÉCTOR");
        names.add("HORACIO");
        names.add("HUGO");
        names.add("IGNACIO");
        names.add("IRENE");
        names.add("ISAAC");
        names.add("ISIS");
        names.add("ISMAEL");
        names.add("ISRAEL");
        names.add("ISRAEL NOÉ");
        names.add("IVAN V.");
        names.add("IVONNE");
        names.add("JAIME");

        return names;
    }

}
