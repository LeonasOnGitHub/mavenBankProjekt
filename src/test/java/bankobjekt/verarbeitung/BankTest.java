package bankobjekt.verarbeitung;

import bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BankTest {
    private final Bank dkb = new Bank(4334);
    private final Kunde k1 = new Kunde("Hans", "Peter", "Hanspeterstarße 123", LocalDate.now());
    private final Kunde k2 = new Kunde("Bob", "Bob", "Huberweg 13", LocalDate.now());
    private final Kunde k3 = new Kunde("Alice", "Alice", "Fruedruchstraße 15", LocalDate.now());

    @Test
    public void erstellen() {

        Assertions.assertEquals(1L, dkb.sparbuchErstellen(k1));
        Assertions.assertEquals(2L, dkb.girokontoErstellen(k2));
        Assertions.assertEquals(3L, dkb.sparbuchErstellen(k2));
        Assertions.assertEquals(4L, dkb.girokontoErstellen(k3));


    }

    @Test
    public void abheben() throws GesperrtException, KontonummerNichtVorhandenException {
        Assertions.assertTrue(dkb.geldAbheben(dkb.girokontoErstellen(k1), 50));
        Assertions.assertFalse(dkb.geldAbheben(dkb.sparbuchErstellen(k1), 50));

        Assertions.assertEquals(-50, dkb.getKontostand(1L));
    }

    @Test
    public void einzahlen() throws KontonummerNichtVorhandenException {
        dkb.geldEinzahlen(dkb.girokontoErstellen(k1), 50);
        dkb.geldEinzahlen(dkb.sparbuchErstellen(k1), 50);

        Assertions.assertEquals(50, dkb.getKontostand(1L));
        Assertions.assertEquals(50, dkb.getKontostand(2L));
    }

    @Test
    public void loeschen() throws KontonummerNichtVorhandenException {
        dkb.sparbuchErstellen(k1);
        dkb.girokontoErstellen(k2);

        Assertions.assertTrue(dkb.kontoLoeschen(1L));
        Assertions.assertEquals(2L, dkb.getAlleKontonummern().get(0));

    }

    @Test
    public void kontostand() throws KontonummerNichtVorhandenException {
        dkb.geldEinzahlen(dkb.girokontoErstellen(k1), 50);
        dkb.geldEinzahlen(dkb.sparbuchErstellen(k1), 50);


        Assertions.assertEquals(50, dkb.getKontostand(1L));
        Assertions.assertEquals(50, dkb.getKontostand(2L));
    }

    @Test
    public void gelUeberweisen() throws KontonummerNichtVorhandenException, GesperrtException, NichtGiroKontoExeption {
        dkb.geldEinzahlen(dkb.girokontoErstellen(k1), 50);
        dkb.girokontoErstellen(k2);

        Assertions.assertTrue(dkb.geldUeberweisen(1L, 2L, 50, "test"));
        Assertions.assertEquals(0, dkb.getKontostand(1L));
        Assertions.assertEquals(50, dkb.getKontostand(2L));
    }
}
