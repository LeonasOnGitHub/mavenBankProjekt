package bankprojekt.verarbeitung;

/**
 * Ein Girokonto, d.h. ein Konto mit einem Dispo und der Fähigkeit,
 * Überweisungen zu senden und zu empfangen.
 * Grundsätzlich sind Überweisungen und Abhebungen möglich bis
 * zu einem Kontostand von -this.dispo
 *
 * @author Doro
 */
public class Girokonto extends Konto implements Ueberweisungsfaehig {
    /**
     * Wert, bis zu dem das Konto überzogen werden darf
     */
    private double dispo;

    /**
     * erzeugt ein leeres, nicht gesperrtes Standard-Girokonto
     * von Herrn MUSTERMANN
     */
    public Girokonto() {
        super(Kunde.MUSTERMANN, 99887766);
        this.dispo = 500;
    }

    /**
     * erzeugt ein Girokonto mit den angegebenen Werten
     *
     * @param inhaber Kontoinhaber
     * @param nummer  Kontonummer
     * @param dispo   Dispo
     * @throws IllegalArgumentException wenn der inhaber null ist oder der angegebene dispo negativ bzw. NaN ist
     */
    public Girokonto(Kunde inhaber, long nummer, double dispo) {
        super(inhaber, nummer);
        if (dispo < 0 || Double.isNaN(dispo) || Double.isInfinite(dispo))
            throw new IllegalArgumentException("Der Dispo ist nicht gültig!");
        this.dispo = dispo;
    }

    /**
     * liefert den Dispo
     *
     * @return Dispo von this
     */
    public double getDispo() {
        return dispo;
    }

    /**
     * setzt den Dispo neu
     *
     * @param dispo muss größer sein als 0
     * @throws IllegalArgumentException wenn dispo negativ bzw. NaN ist
     */
    public void setDispo(double dispo) {
        if (dispo < 0 || Double.isNaN(dispo) || Double.isInfinite(dispo))
            throw new IllegalArgumentException("Der Dispo ist nicht gültig!");
        this.dispo = dispo;
    }

    /**
     * sendet eine Ueberweisung an ein anders Konto
     * @param betrag double
     * @param empfaenger String
     * @param nachKontonr int
     * @param nachBlz int
     * @param verwendungszweck String
     * @return o die ueberweisung erfolgreich war
     * @throws GesperrtException
     */
    @Override
    public boolean ueberweisungAbsenden(double betrag,
                                        String empfaenger, long nachKontonr,
                                        long nachBlz, String verwendungszweck)
            throws GesperrtException {
        if (this.isGesperrt())
            throw new GesperrtException(this.getKontonummer());
        if (betrag < 0 || Double.isNaN(betrag) || Double.isInfinite(betrag) || empfaenger == null || verwendungszweck == null)
            throw new IllegalArgumentException("Parameter fehlerhaft");
        if (getKontostand() - betrag >= -dispo) {
            aendereKontostand(-betrag);
            return true;
        } else
            return false;
    }

    /**
     * empfaegt eine ueberweiseung von einem anderen Konto
     * @param betrag double
     * @param vonName String
     * @param vonKontonr int
     * @param vonBlz int
     * @param verwendungszweck String
     */
    @Override
    public void ueberweisungEmpfangen(double betrag, String vonName, long vonKontonr, long vonBlz, String verwendungszweck) {
        if (betrag < 0 || Double.isNaN(betrag) || Double.isInfinite(betrag) || vonName == null || verwendungszweck == null)
            throw new IllegalArgumentException("Parameter fehlerhaft");
        aendereKontostand(betrag);
    }

    @Override
    public String toString() {
        String ausgabe = "-- GIROKONTO --" + System.lineSeparator() +
                super.toString()
                + "Dispo: " + this.dispo + System.lineSeparator();
        return ausgabe;
    }


    /**
     * prueft ob der abzuhebenende Betrag kleiner ist als der Kontostand + Dispo
     */
    @Override
    public boolean abhebungPruefen(double betrag) {
        return getKontostand() + dispo - betrag >= 0;
    }

    /**
     *  wechselt die waehrung des Kontos
     * @param w die neue waehrung
     */
    public void waehrungswechsel(Waehrung w) {
        this.dispo = this.getAktuelleWaehrung().waehrungInEuroUmrechnen(this.dispo);
        this.dispo = w.euroInWaehrungUmrechnen(this.dispo);

        super.waehrungswechsel(w);
    }

}
