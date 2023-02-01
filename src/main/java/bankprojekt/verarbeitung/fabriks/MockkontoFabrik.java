package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.fabriks.Kontofabrik;
import org.mockito.Mockito;
// eine konkrete Fabrik für Mockkonten
public class MockkontoFabrik extends Kontofabrik {

    /**
     * erstellt ein neues Mockkonto
     * @param kunde der Kunde des neuen Kontos
     * @param kontonummer die Kontonummer des neuen Kontos
     * @return das neue Mockkonto
     */
    @Override
    public Konto erzeugeKonto(Kunde kunde, long kontonummer) {
        return Mockito.mock(Konto.class);
    }

}