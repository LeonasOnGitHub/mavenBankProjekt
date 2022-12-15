package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * stellt ein allgemeines Konto dar
 */
public abstract class Konto implements Comparable<Konto> {
    /**
     * gehört hier absolut nicht her!
     */
    public void ausgeben() {
        System.out.println(this.toString());
    }

    /**
     * das Aktiendepot
     */
    private Map<Aktie, Integer> aktiendepot = new HashMap<>();

    /**
     * der Kontoinhaber
     */
    private Kunde inhaber;

    /**
     * die Kontonummer
     */
    private final long nummer;

    /**
     * der aktuelle Kontostand
     */
    private double kontostand;


    /*
      die Währung des Kontobetrags
    */
    private Waehrung kontoWaehrung = Waehrung.EUR;

    /**
     * setzt den aktuellen Kontostand
     *
     * @param kontostand neuer Kontostand
     */
    protected void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

    /**
     * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
     * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
     */
    private boolean gesperrt;

    /**
     * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
     * der anfängliche Kontostand wird auf 0 gesetzt.
     *
     * @param inhaber     der Inhaber
     * @param kontonummer die gewünschte Kontonummer
     * @throws IllegalArgumentException wenn der Inhaber null
     */
    public Konto(Kunde inhaber, long kontonummer) {
        if (inhaber == null)
            throw new IllegalArgumentException("Inhaber darf nicht null sein!");
        this.inhaber = inhaber;
        this.nummer = kontonummer;
        this.kontostand = 0;
        this.gesperrt = false;
    }

    /**
     * setzt alle Eigenschaften des Kontos auf Standardwerte
     */
    public Konto() {
        this(Kunde.MUSTERMANN, 1234567);
    }

    /**
     * liefert den Kontoinhaber zurück
     *
     * @return der Inhaber
     */
    public Kunde getInhaber() {
        return this.inhaber;
    }

    /**
     * setzt den Kontoinhaber
     *
     * @param kinh neuer Kontoinhaber
     * @throws GesperrtException        wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn kinh null ist
     */
    public final void setInhaber(Kunde kinh) throws GesperrtException {
        if (kinh == null)
            throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
        if (this.gesperrt)
            throw new GesperrtException(this.nummer);
        this.inhaber = kinh;

    }

    /**
     * Zieht den gewünschten Betrag in der gewünschten währung vom Konto ab
     * falls das Konto in einer anderen währung läuft wird der Betrag auf diese umgerechnet
     *
     * @param betrag der abzuhebende Betrag
     * @param w      die Währung
     * @return ob das abheben erfolgreich war
     * @throws GesperrtException
     */
    public boolean abheben(double betrag, Waehrung w) throws GesperrtException {
        double betragInEur = w.waehrungInEuroUmrechnen(betrag);
        double eurInKontoWaehrung = kontoWaehrung.euroInWaehrungUmrechnen(betragInEur);

        abheben(eurInKontoWaehrung);

        return true;
    }

    /**
     * Erhöht den Kontostand um den Eingezahlten Betrag in der angegebenen Währung
     * falls das Konto in einer anderen währung läuft wird der Betrag auf diese umgerechnet
     *
     * @param betrag der eingezahlte Betrag
     * @param w      die Währung in der Eingezahlt wurde
     */
    public void einzahlen(double betrag, Waehrung w) {
        double betragInEur = w.waehrungInEuroUmrechnen(betrag);
        double eurInKontoWaehrung = kontoWaehrung.euroInWaehrungUmrechnen(betragInEur);

        einzahlen(eurInKontoWaehrung);
    }

    /**
     * Wechselt die Währung des Kontos auf die neue Währung und damit auch den Kontostand, den Dispo und bereitsabgehoben
     *
     * @param neu die neue Währung
     */
    public void waehrungswechsel(Waehrung neu) {
        this.kontostand = this.kontoWaehrung.waehrungInEuroUmrechnen(kontostand);
        this.kontostand = neu.euroInWaehrungUmrechnen(kontostand);

        this.kontoWaehrung = neu;

    }

    /**
     * gibt die aktulle währung des Kontos zurück
     *
     * @return Waehrung
     */
    public Waehrung getAktuelleWaehrung() {
        return kontoWaehrung;
    }

    /**
     * liefert den aktuellen Kontostand
     *
     * @return double
     */
    public double getKontostand() {
        return kontostand;
    }

    /**
     * liefert die Kontonummer zurück
     *
     * @return long
     */
    public final long getKontonummer() {
        return nummer;
    }

    /**
     * liefert zurück, ob das Konto gesperrt ist oder nicht
     *
     * @return true, wenn das Konto gesperrt ist
     */
    public final boolean isGesperrt() {
        return gesperrt;
    }

    /**
     * Erhöht den Kontostand um den eingezahlten Betrag.
     *
     * @param betrag double
     * @throws IllegalArgumentException wenn der betrag negativ ist
     */
    public void einzahlen(double betrag) {
        if (betrag < 0 || Double.isNaN(betrag) || Double.isInfinite(betrag)) {
            throw new IllegalArgumentException("Falscher Betrag");
        }
        setKontostand(getKontostand() + betrag);
    }

    /**
     * Gibt eine Zeichenkettendarstellung der Kontodaten zurück.
     */
    @Override
    public String toString() {
        String ausgabe;
        ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
                + System.getProperty("line.separator");
        ausgabe += "Inhaber: " + this.inhaber;
        ausgabe += "Aktueller Kontostand: " + getKontostandFormatiert() + " ";
        ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
        return ausgabe;
    }

    /**
     * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist
     * und die speziellen Abheberegeln des jeweiligen Kontotyps die Abhebung erlauben
     *
     * @param betrag double
     * @return true, wenn die Abhebung geklappt hat,
     * false, wenn sie abgelehnt wurde
     * @throws GesperrtException        wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der betrag negativ oder unendlich ist
     */
    public abstract boolean abheben(double betrag)
            throws GesperrtException;

    /**
     * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
     */
    public final void sperren() {
        this.gesperrt = true;
    }

    /**
     * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
     */
    public final void entsperren() {
        this.gesperrt = false;
    }


    /**
     * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
     *
     * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
     */
    public final String getGesperrtText() {
        if (this.gesperrt) {
            return "GESPERRT";
        } else {
            return "";
        }
    }

    /**
     * liefert die ordentlich formatierte Kontonummer
     *
     * @return auf 10 Stellen formatierte Kontonummer
     */
    public String getKontonummerFormatiert() {
        return String.format("%10d", this.nummer);
    }

    /**
     * liefert den ordentlich formatierten Kontostand
     *
     * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol €
     */
    public String getKontostandFormatiert() {
        return String.format("%10.2f " + this.kontoWaehrung, this.getKontostand());
    }

    /**
     * Vergleich von this mit other; Zwei Konten gelten als gleich,
     * wen sie die gleiche Kontonummer haben
     *
     * @param other das Vergleichskonto
     * @return true, wenn beide Konten die gleiche Nummer haben
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (this.getClass() != other.getClass())
            return false;
        if (this.nummer == ((Konto) other).nummer)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
    }

    @Override
    public int compareTo(Konto other) {
        if (other.getKontonummer() > this.getKontonummer())
            return -1;
        if (other.getKontonummer() < this.getKontonummer())
            return 1;
        return 0;
    }

    public Future<Double> kaufauftrag(Aktie a, int anzahl, double hoechstpreis) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Callable<Double> checkKurs = () -> {
            double betrag;
            while (true) {
                if (a.getKurs() < hoechstpreis) {
                    betrag =a.getKurs()*anzahl;
                    if (kontostand >= betrag) {
                        kontostand = kontostand - betrag;
                        if (aktiendepot.containsKey(a)) {
                            aktiendepot.put(a, anzahl + aktiendepot.get(a));
                        }else{
                            aktiendepot.put(a, anzahl);
                        }
                        break;
                    }
                }
            }
            return betrag;
        };
        Future<Double> aktieKaufen = service.submit(checkKurs);
        aktieKaufen.isDone();
        return aktieKaufen;
    }
    public Future<Double> verkaufauftrag(String wkn, double minimalpreis){

        ExecutorService service = Executors.newSingleThreadExecutor();

        Callable<Double> checkKurs = () -> {
            Aktie a = null;
            for (Aktie aktie:aktiendepot.keySet()) {
                if (aktie.getWertpapeirkennummer().equals(wkn)) {
                    a=aktie;
                }
            }
            double betrag;
            while (true) {
                if (a.getKurs() > minimalpreis) {
                    betrag =a.getKurs()*anzahl;
                    if (kontostand >= betrag) {
                        kontostand = kontostand - betrag;
                        if (aktiendepot.containsKey(a)) {
                            aktiendepot.put(a, anzahl + aktiendepot.get(a));
                        }else{
                            aktiendepot.put(a, anzahl);
                        }
                        break;
                    }
                }
            }
            return betrag;
        };
        Future<Double> aktieKaufen = service.submit(checkKurs);
        aktieKaufen.isDone();
        return aktieKaufen;
    }

        return null;
    }
}
