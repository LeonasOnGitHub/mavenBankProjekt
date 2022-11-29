package bankprojekt.verarbeitung;

public class nichtUeberweisungsfaehigExeption extends Throwable {


        public nichtUeberweisungsfaehigExeption(long kontonummer){super("Das Konto mit der Kontonummer " + kontonummer + " ist kein Girokonto");}

}
