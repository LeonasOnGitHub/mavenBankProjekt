package bankprojekt.verarbeitung;

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
    public void gelUeberweisen() throws KontonummerNichtVorhandenException, GesperrtException, nichtUeberweisungsfaehigExeption {
        dkb.geldEinzahlen(dkb.girokontoErstellen(k1), 50);
        dkb.girokontoErstellen(k2);

        Assertions.assertTrue(dkb.geldUeberweisen(1L, 2L, 50, "test"));
        Assertions.assertEquals(0, dkb.getKontostand(1L));
        Assertions.assertEquals(50, dkb.getKontostand(2L));
    }

    /**
     * Testet ob die Kopie direkt nach dem Aufruf von clone() dem Original entsprecht.
     * Testet ob die Kopie  aber unabhängig vom Original ist. Wenn also Geld auf ein Konto
     * der Original-Bank eingezahlt wird, darf sich das Konto in der Kopie-Bank dadurch
     * nicht verändern.
     * @throws KontonummerNichtVorhandenException //sollte aber nicht
     * @throws GesperrtException //sollte aber nicht
     */
    @Test
    public void kopierungsTest() throws KontonummerNichtVorhandenException, GesperrtException {
        dkb.girokontoErstellen(k1);
        dkb.girokontoErstellen(k2);
        dkb.girokontoErstellen(k3);
        Bank bc = (Bank) dkb.clone();

        Assertions.assertEquals(dkb.getAlleKonten(), bc.getAlleKonten());
        Assertions.assertEquals(dkb.getKontostand(1L), bc.getKontostand(1L));

        dkb.geldEinzahlen(1L, 100);
        Assertions.assertNotEquals(dkb.getKontostand(1L), bc.getKontostand(1L));

        dkb.geldAbheben(1L, 100);
        Assertions.assertEquals(dkb.getKontostand(1L), bc.getKontostand(1L));
    }
}
