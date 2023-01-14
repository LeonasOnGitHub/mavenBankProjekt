package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Bank;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

public abstract class Kontofabrik {

    public abstract Konto erzeugeKonto(Kunde k, long kontonummer);


}
