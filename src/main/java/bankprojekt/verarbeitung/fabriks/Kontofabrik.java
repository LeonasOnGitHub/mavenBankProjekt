package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Bank;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
//eine Fabrik für allerlei mögliche Kontoarten
public abstract class Kontofabrik {

    /**
     *  Erstellt ein neues Konto
     * @param k der kUnde des neuen Kontos
     * @param kontonummer die Kontonummer des neuen Kontos
     * @return ein neues Konto
     */
    public abstract Konto erzeugeKonto(Kunde k, long kontonummer);
}
