package cz.vse.ruzicka;

import cz.vse.ruzicka.logika.*;
import cz.vse.ruzicka.main.Start;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

public class MainController {

    public TextArea textOutput;
    public TextField textInput;
    private IHra hra;

    public Label background;
    public Label locationName;
    public Label locationDescription;
    public Label minimapa;

    public VBox exits;
    public VBox items;
    public VBox backpack;

    public Button btnNovaHra;
    public Button btnNapoveda;
    public Button btnKonec;

    public StackPane stackPane;
    public BorderPane borderPane;
    public ImageView imgView;
    public ComboBox comboBox;

    private SeznamPrikazu seznamPrikazu;

    public void init(IHra hra) {
        this.hra = hra;

        this.comboBox.setItems(FXCollections.observableArrayList(hra.vratPlatnePrikazy().vratPlatnePrikazy()));


        /**
         * NEED HELP
         *
         * ZADÁNÍ - Místo zadávání příkazu v textovém poli bude uživatel vybírat z rozbalovacího seznamu příkazů
         *
         * 1)JAK DOSTAT MAPU PRIKAZU DO COMBOBOXU
         *
         * 2)POTOM KDYZ KLIKNU NA ITEM V COMBOBOXU AT SE PREDVYPLNI DOLE
         */

        /*hra.vratPlatnePrikazy().vratPlatnePrikazy().entrySet().stream().forEach(e -> {
           // Label comboItem = new Label(e.getKey());
            this.comboBox.setItems(FXCollections.observableArrayList(e.getKey()));

          // InputStream stream = getClass().getClassLoader().getResourceAsStream( e.getKey() + ".jpg");
           // Image img = new Image(stream);
           // ImageView imageView = new ImageView(img);
           // imageView.setFitHeight(40);
           // imageView.setFitWidth(60);
           // batohLabel.setGraphic(imageView);



           // batohLabel.setCursor(Cursor.HAND);
            comboBox.getValue().(event -> {
                executeCommand(e.getKey() + "");
              //  executeCommand("poloz " + e.getKey());

            });
            //backpack.getChildren().add(batohLabel);
        });*/


        //       batoh.getSeznamVeci().entrySet().stream().forEach(e -> {
        //   Label batohLabel = new Label(e.getKey());

        //this.comboBox.setItems(FXCollections.observableArrayList(hra.vratPlatnePrikazy().toString()));
        //hra.vratPlatnePrikazy();

        btnNovaHra.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Start start = new Start();
                Stage primaryStage = new Stage();
                try {
                    Stage stage = (Stage) btnNovaHra.getScene().getWindow();
                    stage.close();
                    start.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnNapoveda.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                File napoveda = new File("napoveda.HTML");
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(napoveda);
                    executeCommand("napoveda");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //hra.zpracujPrikaz("napoveda");

                //TADY SE NAPÍŠE DO KONZOLE NÁPOVĚDA
            }
        });

        btnKonec.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) btnKonec.getScene().getWindow();
                stage.close();
            }
        });


        update();
    }

    private void update() {
       /* StackPane stackPane = new StackPane();
        Image image = new Image(getClass().getResourceAsStream("/image.jpg"));
        ImageView imageView = new ImageView(image);
        Button btn = new Button("Click Me!");
        stackPane.getChildren().addAll(imageView, btn);*/

        // NEFUNGUJE PROTOŽE METODA vratNazvyPrikazu() VRACÍ NULL
        //comboBox.getItems().addAll(seznamPrikazu.vratNazvyPrikazu());


        String location = getAktualniProstor().getNazev();
        locationName.setText(location);


        String description = getAktualniProstor().getPopis();
        locationDescription.setText(description);

        updateExits();
        updateItems();
        updateBackpack();
        updatePozadi();
        updateMinimap();
    }

    private void updateMinimap() {
        String currentProstor = getAktualniProstor().getNazev();

        Image image = new Image(getClass().getClassLoader().getResourceAsStream(currentProstor + "Map.jpeg"));
        imgView = new ImageView(image);
        //imgView.setFitHeight(500);
        //imgView.setFitWidth(500);
        minimapa.setGraphic(imgView);

    }

    private void updateItems() {
        Collection<Vec> itemList = getAktualniProstor().getSeznamVeci().values();
        items.getChildren().clear();

        for (Vec item : itemList) {
            String itemName = item.getJmeno();
            Label itemLabel = new Label(itemName);

            InputStream stream = getClass().getClassLoader().getResourceAsStream(itemName + ".jpeg");
            Image img = new Image(stream);
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(40);
            imageView.setFitWidth(60);
            itemLabel.setGraphic(imageView);

            if (item.jePrenositelna()) {
                itemLabel.setCursor(Cursor.HAND);

                itemLabel.setOnMouseClicked(event -> {
                    executeCommand("seber " + itemName);
                });
            } else {
                itemLabel.setTooltip(new Tooltip("Tato věc není přenositelná"));
            }

            items.getChildren().add(itemLabel);
        }

    }

    private void updatePozadi() {

        String currentProstor = getAktualniProstor().getNazev();

        Image image = new Image(getClass().getClassLoader().getResourceAsStream(currentProstor + ".jpeg"));
        imgView = new ImageView(image);
        //imgView.setFitHeight(500);
        //imgView.setFitWidth(500);
        background.setGraphic(imgView);


        //pozadi.getChildren().clear();
        //Label pozadiLabel = new Label(currentProstor);


       /* InputStream stream = getClass().getClassLoader().getResourceAsStream(currentProstor + ".jpg");
        Image img = new Image(stream);
        ImageView imageView = new ImageView(img);
        pozadiLabel.setGraphic(imageView);*/

    }

    private void updateExits() {
        Collection<Prostor> exitList = getAktualniProstor().getVychody();
        exits.getChildren().clear();

        for (Prostor prostor : exitList) {
            String exitName = prostor.getNazev();
            Label exitLabel = new Label(exitName);
            exitLabel.setTooltip(new Tooltip(prostor.getPopis()));

            InputStream stream = getClass().getClassLoader().getResourceAsStream(exitName + ".jpeg");
            Image img = new Image(stream);
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(40);
            imageView.setFitWidth(60);
            exitLabel.setGraphic(imageView);

            exitLabel.setCursor(Cursor.HAND);
            exitLabel.setOnMouseClicked(event -> {
                executeCommand("jdi " + exitName);
            });

            exits.getChildren().add(exitLabel);
        }

    }

    private void executeCommand(String command) {
        String result = hra.zpracujPrikaz(command);
        textOutput.appendText(result + "\n\n");
        update();
    }

    private void updateBackpack() {

        Batoh batoh = hra.getBatoh();
        backpack.getChildren().clear();

       /* Collection<String> veciVbatohu = batoh.vratJmenaVeci();
        //Map<String,Vec> veci = batoh.getSeznamVeci();
        for (String s: veciVbatohu){
            InputStream stream = getClass().getClassLoader().getResourceAsStream( s + ".jpg");
            Image img = new Image(stream);
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(40);
            imageView.setFitWidth(60);
            batohLabel.setGraphic(imageView);
        };*/

        batoh.getSeznamVeci().entrySet().stream().forEach(e -> {
            Label batohLabel = new Label(e.getKey());

            InputStream stream = getClass().getClassLoader().getResourceAsStream(e.getKey() + ".jpeg");
            Image img = new Image(stream);
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(40);
            imageView.setFitWidth(60);
            batohLabel.setGraphic(imageView);


            batohLabel.setCursor(Cursor.HAND);
            batohLabel.setOnMouseClicked(event -> {
                executeCommand("poloz " + e.getKey());

            });
            backpack.getChildren().add(batohLabel);
        });
    }

    private Prostor getAktualniProstor() {
        return hra.getHerniPlan().getAktualniProstor();
    }


    public void onInputKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            executeCommand(textInput.getText());
            textInput.setText("");
        }
    }


    /*public void onInputKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            hra.zpracujPrikaz(textInput.getText());
            //System.out.println("Stisknuto tlacitko ");
            executeCommand(textInput.getText());
            update();
        }
    }*/
}
