package cz.vse.ruzicka.logika;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída SeznamPrikazuTest slouží ke komplexnímu otestování třídy  
 * SeznamPrikazu
 *
 * @author Luboš Pavlíček
 * @version pro školní rok 2014/2015
 */
public class SeznamPrikazuTest {
    private Hra hra;
    private PrikazKonec prKonec;
    private PrikazJdi prJdi;
    private PrikazNapoveda prNapoveda;
    private PrikazObsahBatohu prObsahBatohu;
    private PrikazSeber prSeber;
    private PrikazPoloz prPoloz;

    @Before
    public void setUp() {
        hra = new Hra();
        prKonec = new PrikazKonec(hra);
        prJdi = new PrikazJdi(hra.getHerniPlan());
        prNapoveda = new PrikazNapoveda(hra.vratPlatnePrikazy());
        prObsahBatohu = new PrikazObsahBatohu(hra.getBatoh());
        prPoloz = new PrikazPoloz(hra.getHerniPlan(), hra.getBatoh());
        prSeber = new PrikazSeber(hra.getHerniPlan(), hra.getBatoh());
    }

    @Test
    public void testVlozeniVybrani() {
        SeznamPrikazu seznPrikazu = new SeznamPrikazu();
        seznPrikazu.vlozPrikaz(prKonec);
        seznPrikazu.vlozPrikaz(prJdi);
        assertEquals(prKonec, seznPrikazu.vratPrikaz("konec"));
        assertEquals(prJdi, seznPrikazu.vratPrikaz("jdi"));
        assertEquals(null, seznPrikazu.vratPrikaz("napoveda"));
        seznPrikazu.vlozPrikaz(prNapoveda);
        assertEquals(prNapoveda, seznPrikazu.vratPrikaz("napoveda"));
    }
    @Test
    public void testJePlatnyPrikaz() {
        SeznamPrikazu seznPrikazu = new SeznamPrikazu();
        seznPrikazu.vlozPrikaz(prKonec);
        seznPrikazu.vlozPrikaz(prJdi);
        assertEquals(true, seznPrikazu.jePlatnyPrikaz("konec"));
        assertEquals(true, seznPrikazu.jePlatnyPrikaz("jdi"));
        assertEquals(false, seznPrikazu.jePlatnyPrikaz("napoveda"));
        assertEquals(false, seznPrikazu.jePlatnyPrikaz("Konec"));
        assertEquals(false, seznPrikazu.jePlatnyPrikaz("poloz"));
        assertEquals(false, seznPrikazu.jePlatnyPrikaz("seber"));
        seznPrikazu.vlozPrikaz(prNapoveda);
        seznPrikazu.vlozPrikaz(prObsahBatohu);
        seznPrikazu.vlozPrikaz(prPoloz);
        seznPrikazu.vlozPrikaz(prSeber);
        assertEquals(true, seznPrikazu.jePlatnyPrikaz("napoveda"));
        assertEquals(true, seznPrikazu.jePlatnyPrikaz("obsahBatohu"));
        assertEquals(true, seznPrikazu.jePlatnyPrikaz("poloz"));
        assertEquals(true, seznPrikazu.jePlatnyPrikaz("seber"));
    }
    
    @Test
    public void testNazvyPrikazu() {
        SeznamPrikazu seznPrikazu = new SeznamPrikazu();
        seznPrikazu.vlozPrikaz(prKonec);
        seznPrikazu.vlozPrikaz(prJdi);
        String nazvy = seznPrikazu.vratNazvyPrikazu();
        assertEquals(true, nazvy.contains("konec"));
        assertEquals(true, nazvy.contains("jdi"));
        assertEquals(false, nazvy.contains("napoveda"));
        assertEquals(false, nazvy.contains("Konec"));
        seznPrikazu.vlozPrikaz(prNapoveda);
        seznPrikazu.vlozPrikaz(prObsahBatohu);
        seznPrikazu.vlozPrikaz(prPoloz);
        seznPrikazu.vlozPrikaz(prSeber);
        nazvy = seznPrikazu.vratNazvyPrikazu();
        assertEquals(true, nazvy.contains("napoveda"));
        assertEquals(true, nazvy.contains("obsahBatohu"));
        assertEquals(true, nazvy.contains("poloz"));
        assertEquals(true, nazvy.contains("seber"));
    }
    
}
