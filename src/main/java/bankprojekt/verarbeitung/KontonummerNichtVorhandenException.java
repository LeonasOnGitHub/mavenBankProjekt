package bankprojekt.verarbeitung;

public class KontonummerNichtVorhandenException extends Exception {

    public KontonummerNichtVorhandenException(long kontonummer){super("Das Konto mit der Kontonummer " + kontonummer + " existiert nicht");}
}
