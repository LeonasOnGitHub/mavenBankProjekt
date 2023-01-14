package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

public class GirokontoFabrik extends Kontofabrik {
    @Override
    public Konto erzeugeKonto(Kunde k, long kontonummer) {
        return new Girokonto(k, kontonummer, 100);
    }
}
