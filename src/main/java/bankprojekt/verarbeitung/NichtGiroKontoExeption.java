package bankprojekt.verarbeitung;

public class NichtGiroKontoExeption extends Throwable {


        public NichtGiroKontoExeption(long kontonummer){super("Das Konto mit der Kontonummer " + kontonummer + " ist kein Girokonto");}

}
