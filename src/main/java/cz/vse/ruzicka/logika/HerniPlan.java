package cz.vse.ruzicka.logika;


/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Alena Buchalcevova
 *@version    z kurzu 4IT101 pro školní rok 2014/2015
 */
public class HerniPlan {
    
    private Prostor aktualniProstor;
    private Prostor viteznyProstor;
    
     /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        zalozProstoryHry();

    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {

        Prostor HlavniHala = new Prostor("HlavniHala", "Po vyhrané rvačce s agresivním vězněm jsi padl do bezvědomí, \nnevíš co se stalo dál ale jak se pomalu probouzíš začneš rozeznávat nemocniční prostředí, rychle vstaneš ze svého lůžka jestli se ti to nezdá a otevřeš dveře hlavní haly.\nNyní už víš že jsi určitě v nemocnici, všude kolem tebe prochází spousta doktorů a pacientů. Bylo tedy to co se ti stalo jen pouhým snem nebo skutečností?");
        Prostor Vezen = new Prostor("Vezen", "Přišel jsi k vězni a ten se na tebe agresivně vrhl.", HlavniHala, "Baseballka");
        Prostor Kuchyne = new Prostor("Kuchyne", "Vešel jsi do kuchyně, když v tom se na tebe otočila partička bachařů, kteří tam popíjeli, jeden vytáhl zbraň a než by jsi cokoliv stačil, cítíš jak ti po hrudi stéká krev a padáš mrtvý k zemi.");
        Prostor Jidelna = new Prostor("Jidelna", "Vešel jsi do jídelny, nevypadá to na normální vězeňskou jídelnu naopak spíš na bachařskou, jediné co vidíš na stole jsou lahve rumu když tu náhle uslyšíš z kuchyně šramot.");
        Prostor Satny = new Prostor("Satny", "Dostal jsi se do šatny, ve které jde cítit smrad potu avšak prního čeho si všimneš je děsivá silueta postavy člověka na konci chodby.");
        Prostor Osoba = new Prostor("Osoba", "Přišel si k osobě a ta tě okřikla, co tu chceš mladej, přines mi rum a pak si možná pokecáme.", Satny, "Rum");
        Prostor Sprchy = new Prostor("Sprchy", "Jsi ve sprchách, ve vzduchu je cítít vlhkost a v pozadí vidíš mezi dveřmi stát děsivou osobu.");
        Prostor PravaCesta = new Prostor("PravaCesta", "Vešel jsi do dlouhé široké chodby kterou se lyne příjemná vůně.");
        Prostor LevaCesta = new Prostor("LevaCesta", "Vešel jsi do temné uličky se schody nahoru, ve vzduchu je cítit hniloba a jediné co jde slyšet jsou kapky vody dopadající ze stropu na zem.");
        Prostor Rozcesti = new Prostor("Rozcesti", "Jsi na rozcestí kde se dělí cesta na dvě, levou a pravou.");
        Prostor Chodba = new Prostor("Chodba", "Nacházíš se v dlouhé cihlové chodbě, chodba vede do tvojí cely, cely vedle a k mohutným kovovým dveřím které na sobě mají zámek.", Rozcesti, "Klic");
        Prostor Cela = new Prostor("Cela", "Probudil jsi se ve staré špinavé cele s palčivou bolestí hlavy, nevíš jak jsi se sem dostal ale neplánuješ tu zůstat, zkus se porozhlédnout kolem a najít něco čím se dostaneš pryč.", Chodba, "Pacidlo");
        Prostor VedlejsiCela = new Prostor("VedlejsiCela", "Vstoupil jsi do vedlejší cely která již byla otevřená a zdá se že je již nějakou dobu neobydlená.");

        Chodba.setVychod(VedlejsiCela);
        VedlejsiCela.setVychod(Chodba);
        PravaCesta.setVychod(Rozcesti);
        LevaCesta.setVychod(Rozcesti);
        Rozcesti.setVychod(Chodba);
        LevaCesta.setVychod(Sprchy);
        Sprchy.setVychod(Osoba);
        Sprchy.setVychod(LevaCesta);
        Jidelna.setVychod(Kuchyne);
        Rozcesti.setVychod(PravaCesta);
        Rozcesti.setVychod(LevaCesta);
        PravaCesta.setVychod(Jidelna);
        Osoba.setVychod(Sprchy);
        Satny.setVychod(Vezen);
        Jidelna.setVychod(PravaCesta);

        Cela.vlozVec(new Vec("Pacidlo", true));
        VedlejsiCela.vlozVec(new Vec("Klic", true));
        Chodba.vlozVec(new Vec("Retez", true));
        Sprchy.vlozVec(new Vec("Mydlo", true));
        Satny.vlozVec(new Vec("Baseballka", true));
        Jidelna.vlozVec(new Vec("Rum", true));


        aktualniProstor = Cela;  // hra začíná v domečku
        viteznyProstor = HlavniHala;

        /*// vytvářejí se jednotlivé prostory
        Prostor domecek = new Prostor("domecek","domeček, ve kterém bydlí Karkulka");
        Prostor chaloupka = new Prostor("chaloupka", "chaloupka, ve které bydlí babička Karkulky");
        Prostor jeskyne = new Prostor("jeskyne","stará plesnivá jeskyně");
        Prostor les = new Prostor("les","les s jahodami, malinami a pramenem vody");
        Prostor hlubokyLes = new Prostor("hluboky_les","temný les, ve kterém lze potkat vlka");
        
        // přiřazují se průchody mezi prostory (sousedící prostory)
        domecek.setVychod(les);
        les.setVychod(domecek);
        les.setVychod(hlubokyLes);
        hlubokyLes.setVychod(les);
        hlubokyLes.setVychod(jeskyne);
        hlubokyLes.setVychod(chaloupka);
        jeskyne.setVychod(hlubokyLes);
        chaloupka.setVychod(hlubokyLes);
                
        //aktualniProstor = domecek;  // hra začíná v domečku
        viteznyProstor = chaloupka ;
        les.vlozVec(new Vec("maliny", true));
        les.vlozVec(new Vec("strom", false));
        domecek.vlozVec(new Vec("babovka", true));
        domecek.vlozVec(new Vec("vino", true));
        chaloupka.vlozVec(new Vec("babicka", false));*/

    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
    }
    /**
     *  Metoda vrací odkaz na vítězný prostor.
     *
     *@return     vítězný prostor
     */
    
    public Prostor getViteznyProstor() {
        return viteznyProstor;
    }


}
