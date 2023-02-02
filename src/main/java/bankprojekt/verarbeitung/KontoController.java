package bankprojekt.verarbeitung;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class KontoController extends Application {

    /**
     * Das Hauptfenster
     */
    private Stage stage;

    @FXML private Konto kontoModell;
    @FXML private Kunde kundenModell;

    @FXML private CheckBox sperrenCheckBox;
    @FXML private TextInputDialog adressText;
    @FXML private TextInputDialog betragsText;
    @FXML private Button abhebenButton;
    @FXML private Button einzahlenButton;
    @FXML private Text konotnummerText;
    @FXML private Text kontostandText;


    @FXML public void initialize()
    {
        sperrenCheckBox.selectedProperty().bindBidirectional(kontoModell.gesperrtProperty());
        konotnummerText.textProperty().bind(kontoModell.nummerProperty().asString().concat("Kontonummer: "));
        kontostandText.textProperty().bind(kontoModell.kontostandproperty().asString().concat("Kontostand: "));
        adressText.contentTextProperty().bindBidirectional(kundenModell.adressProperty());
        abhebenButton.defaultButtonProperty().addListener((Observable e) -> {
            try {
                abheben(Double.parseDouble(betragsText.getContentText()));
            } catch (GesperrtException ex) {
                ex.printStackTrace();
            }
        });
        einzahlenButton.defaultButtonProperty().addListener((Observable e) ->
                einzahlen(Double.parseDouble(betragsText.getContentText())));
    }

    @FXML private void abheben(double betrag) throws GesperrtException {
        kontoModell.abheben(betrag);
    }
    @FXML private void einzahlen(double betrag)  {
        kontoModell.einzahlen(betrag);
    }

    @FXML private void schliessen()
    {
        stage.close();
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader =
                new FXMLLoader(getClass().
                        getResource("../../KontoOberflaeche2.fxml"));
        loader.setController(this);
        Parent lc = loader.load();
        Scene scene = new Scene(lc, 300, 275);
        stage.setTitle("Konto bearbeiten");
        stage.setScene(scene);
        stage.show();
    }
}
