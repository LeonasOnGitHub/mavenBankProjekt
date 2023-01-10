package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Sparbuch;

public class RealesKontoFabrik extends Kontofabrik {
    @Override
    public Konto erzeugeKonto(int auswahl) {
        return switch (auswahl) {
            case 1 -> new Girokonto();
            case 2 -> new Sparbuch();
            default -> null;
        };
    }
}
