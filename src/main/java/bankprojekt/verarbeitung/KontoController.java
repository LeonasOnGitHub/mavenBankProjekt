package bankprojekt.verarbeitung;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Kontrolliert das Fenster und die Uebergabe der tasks an die Modelle
 */
public class KontoController extends Application {

    /**
     * Das Hauptfenster
     */
    private Stage stage;

    @FXML private Girokonto kontoModell;
    @FXML private Kunde kundenModell;
    @FXML private CheckBox sperrenCheckBox;
    @FXML private TextField betragsText;
    @FXML private Button abhebenButton;
    @FXML private Button einzahlenButton;
    @FXML private Text kontonummerText;
    @FXML private Text kontostandText;
    @FXML private TextField adressTextField;
    @FXML private Text adressText;
    @FXML private Text meldungsText;


    /**
     * Inizialisiert alle Elemente
     */
    @FXML public void initialize()
    {
        adressTextField.setText(kundenModell.getAdresse());
        adressTextField.textProperty().bindBidirectional(kundenModell.adressProperty());
        sperrenCheckBox.selectedProperty().bindBidirectional(kontoModell.gesperrtProperty());
        kontonummerText.textProperty().bind(kontoModell.nummerProperty().asString());
        kontostandText.textProperty().bind(kontoModell.kontostandproperty().asString());
        //adressText.textProperty().bind(kundenModell.adressProperty());
       // adressText.setText(kundenModell.getAdresse());
        abhebenButton.setOnAction(e ->{
            abheben(Double.parseDouble(betragsText.getText()));
        });
        einzahlenButton.setOnAction(( e) ->
                einzahlen(Double.parseDouble(betragsText.getText())));
    }

    /**
     * hebt den Betrag vom Konto ab
     * gibt aus ob die Abhebung
     * erfolreich war
     * @param betrag
     */
    @FXML private void abheben(double betrag)  {
        try {
            if (kontoModell.abheben(betrag)){
                meldungsText.setText("Abhebung war erfolfgeich!");
            }else {
             meldungsText.setText("Abhebung was nicht Erfolgreich");

            }
        } catch (GesperrtException e) {
            meldungsText.setText("Konto ist Gesperrt");
        }
    }

    /**
     * zahlt den Betrag auf das Konto ein
     * @param betrag
     */
    @FXML private void einzahlen(double betrag)  {
        kontoModell.einzahlen(betrag);
        meldungsText.setText("Einzahlen war Erfolgreich!");
    }

    /**
     * schließt das Fenster und beendet das Programm
     */
    @FXML private void schliessen()
    {
        stage.close();
    }

    /**
     * main Methode die alles andere startet
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader =
                new FXMLLoader(getClass().
                        getResource("../../KontoOberflaeche2.fxml"));
        loader.setController(this);
        Parent lc = loader.load();
        adressText.setText(kundenModell.getAdresse());
        Scene scene = new Scene(lc, 600, 475);
        kontoModell.einzahlen(10);
        stage.setTitle("Konto bearbeiten");
        stage.setScene(scene);

        stage.show();
    }
}
