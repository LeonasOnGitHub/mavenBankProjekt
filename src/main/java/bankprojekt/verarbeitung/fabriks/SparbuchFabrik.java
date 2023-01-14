package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

public class SparbuchFabrik extends Kontofabrik{
    @Override
    public Konto erzeugeKonto(Kunde k, long kononummer) {
        return new Sparbuch(k, kononummer);
    }
}
