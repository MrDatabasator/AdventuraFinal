package cz.vse.ruzicka.logika;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída HraTest slouží ke komplexnímu otestování
 * třídy Hra
 *
 * @author    Jarmila Pavlíčková
 * @version  pro školní rok 2014/2015
 */
public class HraTest {
    private Hra hra1;

    //== Datové atributy (statické i instancí)======================================

    //== Konstruktory a tovární metody =============================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    //== Příprava a úklid přípravku ================================================

    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @Before
    public void setUp() {
        hra1 = new Hra();
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @After
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází.
     * Při dalším rozšiřování hry doporučujeme testovat i jaké věci nebo osoby
     * jsou v místnosti a jaké věci jsou v batohu hráče.
     * 
     */
    @Test
    public void testPrubehHry() {
        assertEquals("Cela", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("seber Pacidlo");
        hra1.zpracujPrikaz("poloz Pacidlo");
        hra1.zpracujPrikaz("jdi Chodba");
        assertEquals(false, hra1.konecHry());
        assertEquals("Chodba", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi VedlejsiCela");
        assertEquals(false, hra1.konecHry());
        assertEquals("VedlejsiCela", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("seber Klic");
        assertEquals("věci v batohu: Klic ", hra1.getBatoh().nazvyVeci());
        hra1.zpracujPrikaz("jdi Chodba");
        hra1.zpracujPrikaz("seber Retez");
        assertEquals("věci v batohu: Retez Klic ", hra1.getBatoh().nazvyVeci());
        hra1.zpracujPrikaz("poloz Klic");
        hra1.zpracujPrikaz("jdi Rozcesti");
        hra1.zpracujPrikaz("jdi PravaCesta");
        hra1.zpracujPrikaz("jdi Jidelna");
        hra1.zpracujPrikaz("seber Rum");
        assertEquals("věci v batohu: Retez Rum ", hra1.getBatoh().nazvyVeci());
        hra1.zpracujPrikaz("jdi PravaCesta");
        hra1.zpracujPrikaz("jdi Rozcesti");
        hra1.zpracujPrikaz("jdi LevaCesta");
        hra1.zpracujPrikaz("jdi Sprchy");
        hra1.zpracujPrikaz("seber Mydlo");
        assertEquals("věci v batohu: Retez Rum Mydlo ", hra1.getBatoh().nazvyVeci());
        hra1.zpracujPrikaz("jdi Osoba");
        hra1.zpracujPrikaz("poloz Rum");
        assertEquals("věci v batohu: Retez Mydlo ", hra1.getBatoh().nazvyVeci());
        hra1.zpracujPrikaz("jdi Satny");
        hra1.zpracujPrikaz("seber Baseballka");
        assertEquals("věci v batohu: Retez Baseballka Mydlo ", hra1.getBatoh().nazvyVeci());
        hra1.zpracujPrikaz("jdi Vezen");
        hra1.zpracujPrikaz("poloz Baseballka");
        hra1.zpracujPrikaz("jdi HlavniHala");
        assertEquals(hra1.getHerniPlan().getViteznyProstor(), hra1.getHerniPlan().getAktualniProstor());
        hra1.zpracujPrikaz("konec");
        assertEquals(true, hra1.konecHry());
    }
}
