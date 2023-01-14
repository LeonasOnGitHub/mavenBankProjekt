package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.fabriks.Kontofabrik;
import org.mockito.Mockito;

public class MockkontoFabrik extends Kontofabrik {

    @Override
    public Konto erzeugeKonto(Kunde kunde, long kontonummer) {
        return Mockito.mock(Konto.class);
    }

}