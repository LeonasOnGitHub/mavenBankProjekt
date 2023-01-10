package bankprojekt.verarbeitung;

import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Sparbuch;
import bankprojekt.verarbeitung.Waehrung;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class KontoTest {

    /**
     * Testet das Abheben mit allen verschiedenen Währungen auf 0.01 genau
     * Wechselt  die Kontowährung und Testet alles nochmal
     *
     * @throws GesperrtException
     */
    @Test
    public void testAbheben() throws GesperrtException {


        for (Waehrung w : Waehrung.values()) {
            Girokonto gKonto = new Girokonto();
            gKonto.einzahlen(1000, Waehrung.EUR);

            Assertions.assertTrue(gKonto.abheben(100, Waehrung.EUR));
            Assertions.assertEquals(900, gKonto.getKontostand());

            Assertions.assertTrue(gKonto.abheben(6162, Waehrung.MKD));
            Assertions.assertEquals(800, gKonto.getKontostand());

            Assertions.assertTrue(gKonto.abheben(195.58, Waehrung.BGN));
            Assertions.assertEquals(700, gKonto.getKontostand());

            Assertions.assertTrue(gKonto.abheben(746.06, Waehrung.DKK));
            Assertions.assertEquals(600, gKonto.getKontostand());

            gKonto.waehrungswechsel(w);
            Assertions.assertEquals(w, gKonto.getAktuelleWaehrung());

        }

    }

    /**
     * Testet das wechseln der Waehrung in Bezug auf den Dispo und Bereitsabgehoben mit allen verschiedenen Währungen auf 0.01 genau
     * Wechselt  die Kontowährungen und Testet alles nochmal
     * @throws GesperrtException
     */
    @Test
    public void testDispo_BereitsAbgehoben() throws GesperrtException {

        Girokonto gKonto = new Girokonto();
        gKonto.setDispo(100);
        Sparbuch sparbuch = new Sparbuch();
        sparbuch.einzahlen(200, Waehrung.EUR);
        sparbuch.abheben(100, sparbuch.getAktuelleWaehrung());


        for (Waehrung w : Waehrung.values()) {

            gKonto.waehrungswechsel(w);
            Assertions.assertEquals(w, gKonto.getAktuelleWaehrung());
            sparbuch.waehrungswechsel(w);
            Assertions.assertEquals(w, sparbuch.getAktuelleWaehrung());

            double hundertInWaehrung = w.euroInWaehrungUmrechnen(100);

            Assertions.assertEquals(hundertInWaehrung, gKonto.getDispo());
            Assertions.assertEquals(hundertInWaehrung, sparbuch.getBereitsAbgehoben());
        }

    }

    /**
     * Testet das Zusammenspiel der verschiedenen Methoden
     * Testet einen Durchlauf von mehreren Abhebungen, waehrungswechseln, einzahlungen
     * @throws GesperrtException
     */
    @Test
    public void testDurcheinander() throws GesperrtException {
        Girokonto gKonto = new Girokonto();
        gKonto.setDispo(100);
        gKonto.einzahlen(100);


        Assertions.assertTrue(gKonto.abheben(50, Waehrung.EUR));
        Assertions.assertEquals(50, gKonto.getKontostand());

        gKonto.waehrungswechsel(Waehrung.DKK);
        Assertions.assertEquals(Waehrung.DKK, gKonto.getAktuelleWaehrung());

        Assertions.assertTrue(gKonto.abheben(Waehrung.DKK.euroInWaehrungUmrechnen(50), Waehrung.DKK));
        Assertions.assertEquals(0, gKonto.getKontostand());

        Assertions.assertTrue(gKonto.abheben(50, Waehrung.EUR));
        Assertions.assertEquals(Waehrung.DKK.euroInWaehrungUmrechnen(-50), gKonto.getKontostand());

        gKonto.waehrungswechsel(Waehrung.BGN);
        Assertions.assertEquals(Waehrung.BGN, gKonto.getAktuelleWaehrung());

        gKonto.einzahlen(Waehrung.BGN.euroInWaehrungUmrechnen(100), Waehrung.BGN);
        Assertions.assertEquals(Waehrung.BGN.euroInWaehrungUmrechnen( 50), gKonto.getKontostand());

        gKonto.waehrungswechsel(Waehrung.MKD);
        Assertions.assertEquals(Waehrung.MKD, gKonto.getAktuelleWaehrung());

        Assertions.assertTrue(gKonto.abheben(150, Waehrung.EUR));
        Assertions.assertEquals(Waehrung.MKD.euroInWaehrungUmrechnen(-100), gKonto.getKontostand());

        Assertions.assertFalse(gKonto.abheben(1, gKonto.getAktuelleWaehrung()));



    }

}
