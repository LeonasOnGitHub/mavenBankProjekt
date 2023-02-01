package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

// eine konkrete Fabrik für Girokonten
public class GirokontoFabrik extends Kontofabrik {

    /**
     *  Erstellt ein neues Gitokonto
     * @param k der Kunde des neuen Kontos
     * @param kontonummer die kontonummer des neuen kontos
     * @return das neue Girokonto
     */
    @Override
    public Konto erzeugeKonto(Kunde k, long kontonummer) {
        return new Girokonto(k, kontonummer, 100);
    }
}
