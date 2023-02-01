package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;
// Eine konkrete Fabrik für Sparbücher
public class SparbuchFabrik extends Kontofabrik{
    /**
     * erstellt ein neues Sparbuch
     * @param k der kUnde des neuen sparbuchs
     * @param kononummer die Kontonummer des neuen Sparbuchs
     * @return das neue Sparbuch
     */
    @Override
    public Konto erzeugeKonto(Kunde k, long kononummer) {
        return new Sparbuch(k, kononummer);
    }
}
