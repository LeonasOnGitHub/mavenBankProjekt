package bankprojekt.verarbeitung.fabriks;

import bankprojekt.verarbeitung.Bank;
import bankprojekt.verarbeitung.Konto;

public abstract class Kontofabrik {

    public abstract Konto erzeugeKonto(int auswahl);

    public void kontoAufmachen(int kontotype, Bank dkb){
       Konto k = erzeugeKonto(kontotype);

    }
}
