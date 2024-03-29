package bankprojekt.verarbeitung;

import java.time.LocalDate;

/**
 * ein Sparbuch, d.h. ein Konto, das nur recht eingeschränkt genutzt
 * werden kann. Insbesondere darf man monatlich nur höchstens 2000€
 * abheben, wobei der Kontostand nie unter 0,50€ fallen darf.
 *
 * @author Doro
 */
public class Sparbuch extends Konto {
    /**
     * Zinssatz, mit dem das Sparbuch verzinst wird. 0,03 entspricht 3%
     */
    private double zinssatz;

    /**
     * Monatlich erlaubter Gesamtbetrag für Abhebungen
     */
    public static final double ABHEBESUMME = 2000;

    /**
     * Betrag, der im aktuellen Monat bereits abgehoben wurde
     */
    private double bereitsAbgehoben = 0;
    /**
     * Monat und Jahr der letzten Abhebung
     */
    private LocalDate zeitpunkt = LocalDate.now();

    /**
     * ein Standard-Sparbuch
     */
    public Sparbuch() {
        zinssatz = 0.03;
    }

    /**
     * ein Standard-Sparbuch, das inhaber gehört und die angegebene Kontonummer hat
     *
     * @param inhaber     der Ko    ntoinhaber
     * @param kontonummer die Wunsch-Kontonummer
     * @throws IllegalArgumentException wenn inhaber null ist
     */
    public Sparbuch(Kunde inhaber, long kontonummer) {
        super(inhaber, kontonummer);
        zinssatz = 0.03;
    }

    @Override
    public String toString() {
        String ausgabe = "-- SPARBUCH --" + System.lineSeparator() +
                super.toString()
                + "Zinssatz: " + this.zinssatz * 100 + "%" + System.lineSeparator();
        return ausgabe;
    }

    /**
     * prueft ob auf dem Konto genug geld ist um den betrag ab zu ziehen
     * @param betrag
     * @return
     */
    @Override
    protected boolean abhebungPruefen(double betrag){
        LocalDate heute = LocalDate.now();
        if (heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear()) {
            this.bereitsAbgehoben = 0;
        }
        return getKontostand() - betrag >= 0.50 &&
                bereitsAbgehoben + betrag <= getAktuelleWaehrung().euroInWaehrungUmrechnen(Sparbuch.ABHEBESUMME);
    }

    /**
     * speichert die abhebung in bereitsabgehoben
     * @param betrag
     */
    @Override
    protected void nachbereitung(double betrag)  {
            bereitsAbgehoben += betrag;
            this.zeitpunkt = LocalDate.now();
    }

    public double getBereitsAbgehoben() {
        return bereitsAbgehoben;
    }

    /**
     * wechselt die waehrung den kontos
     * @param w die neue waherung
     */
    public void waehrungswechsel(Waehrung w) {
        this.bereitsAbgehoben = this.getAktuelleWaehrung().waehrungInEuroUmrechnen(this.bereitsAbgehoben);
        this.bereitsAbgehoben = w.euroInWaehrungUmrechnen(this.bereitsAbgehoben);

        super.waehrungswechsel(w);
    }
}
