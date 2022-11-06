package bankprojekt.verarbeitung;

import org.decimal4j.util.DoubleRounder;

public enum Waehrung {
    EUR(1), BGN(1.9558), MKD(61.62), DKK(7.4604);

    /*
    Der Umrechnungskues von der Währung zu Euro
     */
    private double euroKurs;
    private DoubleRounder dr = new DoubleRounder(2);

    Waehrung(double euroKurs) {
        this.euroKurs = euroKurs;
    }

    /**
     * rechnet den eingegeben Betrag von dieser währung in euro um
     *
     * @param betrag
     * @return Betrag in Euro
     */
    public double euroInWaehrungUmrechnen(double betrag) {
        double betragGerundet = dr.round(betrag * this.euroKurs);
        return betragGerundet;
    }

    /**
     * rechnet den eingegeben Betrag von Euro in diese währung um
     *
     * @param betrag
     * @return Betrag in der Währung des Objects
     */
    public double waehrungInEuroUmrechnen(double betrag) {

        double betragGerundet = dr.round(betrag / this.euroKurs);
        return betragGerundet;
    }

}
