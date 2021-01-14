package cz.vse.ruzicka;

import cz.vse.ruzicka.logika.*;
import cz.vse.ruzicka.main.Start;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    private Collection<String> listPrikazu = new ArrayList<String>();

    private SeznamPrikazu seznamPrikazu;

    public void init(IHra hra) {
        this.hra = hra;



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

                /**
                 * Při kliknutí na nápovědu se otevře nové okno s nápovědou, první koncept otevíral nápovědu z HTML souboru a otevřel jí v defaultním prohlížeči
                 */
                /*File napoveda = new File("napoveda.HTML");
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(napoveda);
                    executeCommand("napoveda");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                /**
                 * Při kliknutí na nápovědu se otevře nové okno s nápovědou, nyní se otevírá v novém okně
                 */

                try {
                    Label vNovemOkne = new Label("Vítejte ve hře JAILBREAK made by Patrik Novák...\n" +
                            "\n" +
                            "\n" +
                            "Tvým úkolem je se za pomocí různých předmětů dostat pryč z vězení kde jsi se probudil....\n" +
                            "\n" +
                            "Můžeš zadávat tyto příkazy:\n" +
                            "napoveda/obsahBatohu/poloz/jdi/seber/konec ");

                    StackPane secondaryLayout = new StackPane();
                    secondaryLayout.getChildren().add(vNovemOkne);

                    Scene secondScene = new Scene(secondaryLayout, 500, 200);

                    // New window (Stage)
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Nápověda");
                    newWindow.setScene(secondScene);

                    Stage stage = (Stage) btnNovaHra.getScene().getWindow();

                    // Set position of second window, related to primary window.
                    newWindow.setX(stage.getX() + 200);
                    newWindow.setY(stage.getY() + 100);

                    newWindow.show();
                } catch (Exception e) {
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
        updateCombobox();
    }


    private void updateCombobox() {

        /**
         * ke každému příkazu kde je to možné přidá attribut se kterým lze příkaz použít, pak ho uloží do listPrikazu a ten vypíše v comboboxu
         */

        this.comboBox.getItems().clear();
        this.listPrikazu.clear();

        //this.comboBox.setItems(FXCollections.observableArrayList(hra.vratPlatnePrikazy().vratPlatnePrikazy().keySet()));

        for (String veci : getAktualniProstor().getSeznamVeci().keySet()
        ) {
            listPrikazu.add("seber " + veci);
        }
        for (String veci : hra.getBatoh().getSeznamVeci().keySet()
        ) {
            listPrikazu.add("poloz " + veci);
        }
        for (Prostor vychod : getAktualniProstor().getVychody()
        ) {
            listPrikazu.add("jdi " + vychod.getNazev());
        }
        listPrikazu.add("napoveda");
        listPrikazu.add("obsahBatohu");
        listPrikazu.add("konec");


        this.comboBox.setItems(FXCollections.observableArrayList(listPrikazu));

        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                executeCommand(comboBox.getValue().toString());
            }
        });


    }

    private void updateMinimap() {

        /**
         * Minimapa se zobrazuje dle aktuálního prostoru
         */

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

        /**
         * Získává z aktuálního prostoru název a podle něj dává na pozadí obrázek z resourců
         */

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
