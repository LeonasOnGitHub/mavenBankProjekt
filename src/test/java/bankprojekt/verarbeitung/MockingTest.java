package bankprojekt.verarbeitung;

import bankprojekt.verarbeitung.fabriks.Kontofabrik;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class MockingTest {
    double betrag = 50.0;
    Bank bank = new Bank(1234);
    Kunde kunde = Mockito.mock(Kunde.class);
    Kontofabrik mkf = Mockito.mock(Kontofabrik.class);
    Konto mgk2 = Mockito.mock(Girokonto.class);
    Girokonto sgk = Mockito.spy(Girokonto.class);
    Sparbuch ssk = Mockito.spy(Sparbuch.class);

    @Test
    public void giroKontoTest() throws GesperrtException, KontonummerNichtVorhandenException, nichtUeberweisungsfaehigExeption {

        Mockito.when(mkf.erzeugeKonto(any(), anyLong())).thenReturn(sgk);
        long gkMockKontonummer = bank.kontoErstellen(mkf, kunde);
        long gkMock2Kontonummer = bank.kontoErstellen(mkf, kunde);
        //test prepared mocks
        Assertions.assertEquals(1, gkMockKontonummer);
        Assertions.assertEquals(2, gkMock2Kontonummer);
        //setup
        Mockito.when(sgk.getKontostand()).thenReturn(betrag);
        Mockito.doReturn(true).when(sgk).abheben(betrag);

        Mockito.when(sgk.getInhaber()).thenReturn(kunde);
        Mockito.when(sgk.getInhaber()).thenReturn(kunde);
        Mockito.when(sgk.ueberweisungAbsenden(betrag, "Mock2", gkMock2Kontonummer, bank.getBankleitzahl(), "Test")).thenReturn(true);
        Mockito.when(sgk.getInhaber().getName()).thenReturn("Mock");
        Mockito.when(sgk.getInhaber().getName()).thenReturn("Mock2");


        //exercise
        Assertions.assertTrue(bank.geldAbheben(gkMockKontonummer, betrag));
        Assertions.assertEquals(betrag, bank.getKontostand(gkMockKontonummer));
        Assertions.assertTrue(bank.geldUeberweisen(gkMockKontonummer, gkMock2Kontonummer, betrag, "Test"));
    }

    @Test
    public void sparbuchTest() throws GesperrtException, KontonummerNichtVorhandenException {
        //prepare mocks
        Bank bank = new Bank(1234);
        ssk.waehrungswechsel(Waehrung.EUR);
        Mockito.when(mkf.erzeugeKonto(any(), anyLong())).thenReturn(ssk);
        long sbMockKontonummer = bank.kontoErstellen(mkf, kunde);
        //test prepared mocks
        Assertions.assertEquals(1, sbMockKontonummer);
        //setup
        Mockito.when(ssk.getKontostand()).thenReturn(betrag);
        Mockito.doReturn(true).when(ssk).abheben(betrag);
        //exercise
        Assertions.assertTrue(bank.geldAbheben(sbMockKontonummer, betrag));
        Assertions.assertEquals(betrag, bank.getKontostand(sbMockKontonummer));

    }


    @Test
    public void badGiroKontoTest() throws GesperrtException, KontonummerNichtVorhandenException, nichtUeberweisungsfaehigExeption {
        //prepare mocks
        Bank bank = new Bank(1234);
        Mockito.when(mkf.erzeugeKonto(any(), anyLong())).thenReturn(sgk);
        long gkMockKontonummer = bank.kontoErstellen(mkf, kunde);
        Mockito.when(mkf.erzeugeKonto(any(), anyLong())).thenReturn(mgk2);
        long gkMock2Kontonummer = bank.kontoErstellen(mkf, kunde);
        Mockito.when(mkf.erzeugeKonto(any(), anyLong())).thenReturn(ssk);
        long sbMockKontonummer = bank.kontoErstellen(mkf, kunde);
        //test prepared mocks
        Assertions.assertEquals(1, gkMockKontonummer);
        Assertions.assertEquals(2, gkMock2Kontonummer);
        Assertions.assertEquals(3, sbMockKontonummer);
        //setup
        Mockito.when(sgk.getKontostand()).thenReturn(betrag);
        Mockito.when(sgk.getInhaber()).thenReturn(kunde);
        Mockito.when(mgk2.getInhaber()).thenReturn(kunde);
        Mockito.when(sgk.ueberweisungAbsenden(betrag, "Mock2", gkMock2Kontonummer, bank.getBankleitzahl(), "Test")).thenReturn(true);
        Mockito.when(sgk.getInhaber().getName()).thenReturn("Mock");
        Mockito.when(mgk2.getInhaber().getName()).thenReturn("Mock2");


        //exercise
        try {
            bank.geldAbheben(-1L, betrag);
            Assertions.fail();
        } catch (KontonummerNichtVorhandenException ignored) {
        }

        try {
            bank.geldUeberweisen(-1L, gkMock2Kontonummer, betrag, "Test");
            Assertions.fail();
        } catch (KontonummerNichtVorhandenException ignored) {
        }

        try {
            bank.geldUeberweisen(gkMockKontonummer, -1L, betrag, "Test");
            Assertions.fail();
        } catch (KontonummerNichtVorhandenException ignored) {
        }

        try {
            bank.geldUeberweisen(sbMockKontonummer, gkMock2Kontonummer, betrag, "Test");
            Assertions.fail();
        } catch (nichtUeberweisungsfaehigExeption ignored) {
        }

        try {
            bank.geldUeberweisen(gkMockKontonummer, sbMockKontonummer, betrag, "Test");
            Assertions.fail();
        } catch (nichtUeberweisungsfaehigExeption ignored) {
        }

    }
}
