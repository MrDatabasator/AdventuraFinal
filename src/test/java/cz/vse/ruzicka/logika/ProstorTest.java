package cz.vse.ruzicka.logika;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída ProstorTest slouží ke komplexnímu otestování
 * třídy Prostor
 *
 * @author    Jarmila Pavlíčková
 * @version   pro skolní rok 2014/2015
 */
public class ProstorTest
{
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
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @After
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře, 
     */
    @Test
    public  void testLzeProjit() {
        Prostor prostor1 = new Prostor("hala", "vstupní hala budovy VŠE na Jižním městě");
        Prostor prostor2 = new Prostor("bufet", "bufet, kam si můžete zajít na svačinku");
        prostor1.setVychod(prostor2);
        prostor2.setVychod(prostor1);
        assertEquals(prostor2, prostor1.vratSousedniProstor("bufet"));
        assertEquals(null, prostor2.vratSousedniProstor("pokoj"));


        Prostor PravaCesta = new Prostor("PravaCesta", "Vešel jsi do dlouhé široké chodby kterou se lyne příjemná vůně.");
        Prostor LevaCesta = new Prostor("LevaCesta", "Vešel jsi do temné uličky se schody nahoru, ve vzduchu je cítit hniloba a jediné co jde slyšet jsou kapky vody dopadající ze stropu na zem.");
        Prostor Rozcesti = new Prostor("Rozcesti", "Jsi na rozcestí kde se dělí cesta na dvě, levou a pravou.");
        Prostor Chodba = new Prostor("Chodba", "Nacházíš se v dlouhé cihlové chodbě, chodba vede do tvojí cely, cely vedle a k mohutným kovovým dveřím které na sobě mají zámek.", Rozcesti, "Klic");
        Prostor Cela = new Prostor("Cela", "Probudil jsi se ve staré špinavé cele s palčivou bolestí hlavy, nevíš jak jsi se sem dostal ale neplánuješ tu zůstat, zkus se porozhlédnout kolem a najít něco čím se dostaneš pryč.", Chodba, "Pacidlo");
        Rozcesti.setVychod(PravaCesta);
        Rozcesti.setVychod(LevaCesta);
        assertEquals(Chodba, Cela.getZavrenyVychod());
        assertEquals(null, Cela.vratSousedniProstor("Chodba"));
        assertEquals(Rozcesti, Chodba.getZavrenyVychod());
        assertEquals(null, Chodba.vratSousedniProstor("Rozcesti"));
        assertEquals(null, Rozcesti.getZavrenyVychod());
        assertEquals(LevaCesta, Rozcesti.vratSousedniProstor("LevaCesta"));
        assertEquals(PravaCesta, Rozcesti.vratSousedniProstor("PravaCesta"));
    }

}
