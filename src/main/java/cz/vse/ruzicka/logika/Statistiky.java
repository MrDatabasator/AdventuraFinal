package cz.vse.ruzicka.logika;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Statistiky {

    static int pocetZadanychPrikazu = 0;
    static int pocetNavstivenychMistnosti = 1;
    static long timeElapsed;
    static long startTime;
    static long endTime;

    public static void zapniMereni() throws InterruptedException {
        startTime = new Date().getTime();
        TimeUnit.SECONDS.sleep(5);
    }

    public static void vypniMereni() {
        endTime = new Date().getTime();
    }

    /*public static void vratStatistiky() {
        timeElapsed = endTime - startTime;
        System.out.println("Za tuhle hru jsi navštívil " + pocetNavstivenychMistnosti + " místností.");
        System.out.println("Zadal jsi přesně " + pocetZadanychPrikazu + " příkazů.");
        System.out.println("Odehrál jsi celých " + TimeUnit.MILLISECONDS.toSeconds(timeElapsed) + " sekund.");
    }*/

    public static int vratPocetNavstivenychMistnosti() {
        return pocetNavstivenychMistnosti;
    }

    public static int vratPocetZadanychPrikazu() {
        return pocetZadanychPrikazu;
    }

    public static long vratCasHrani() {
        timeElapsed = endTime - startTime;
        //long a = TimeUnit.MILLISECONDS.toSeconds(timeElapsed);
        /**
         * z nějakého důvodu nefunguje time unit proto pro převod času jsem použil jinou funkci
         */
        int seconds = (int) ((timeElapsed / 1000) % 60);
        return seconds;
    }

    public static void pridejPrikaz() {
        pocetZadanychPrikazu++;
    }

    public static void pridejNavstivenouMistnost() {
        pocetNavstivenychMistnosti++;
    }

}
