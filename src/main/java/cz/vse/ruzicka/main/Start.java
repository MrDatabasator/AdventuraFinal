/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.ruzicka.main;


import cz.vse.ruzicka.MainController;
import cz.vse.ruzicka.logika.Hra;
import cz.vse.ruzicka.logika.IHra;
import cz.vse.ruzicka.logika.Statistiky;
import cz.vse.ruzicka.uiText.TextoveRozhrani;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/*******************************************************************************
 * Třída {@code Start} je hlavní třídou projektu,
 * který ...
 *
 * @author Patrik Novák
 * @version 1.00.000
 */
public class Start extends Application
{
    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args)
    {
        List<String> vstup = Arrays.asList(args);

        if(vstup.contains("text")) {
            IHra hra = new Hra();
            TextoveRozhrani ui = new TextoveRozhrani(hra);
            ui.hraj();
            try {
                Statistiky.zapniMereni();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            launch();
            try {
                Statistiky.zapniMereni();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("JailBreak");

        FXMLLoader loader = new FXMLLoader();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("scene.fxml");
        Parent root = loader.load(stream);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        MainController controller = loader.getController();
        IHra hra = new Hra();
        controller.init(hra);
        primaryStage.show();
    }
}
