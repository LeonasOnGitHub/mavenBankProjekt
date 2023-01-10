package bankprojekt.verarbeitung;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MockingTest {
double betrag = 50.0;
    @Test
    public void giroKontoTest() throws GesperrtException, KontonummerNichtVorhandenException, nichtUeberweisungsfaehigExeption {
        //prepare mocks
        Bank bank = new Bank(1234);
        Kunde kunde = Mockito.mock(Kunde.class);
        Girokonto gkMock = Mockito.mock(Girokonto.class);
        long gkMockKontonummer = bank.mockEinfuegen(gkMock);
        Girokonto gkMock2 = Mockito.mock(Girokonto.class);
        long gkMock2Kontonummer = bank.mockEinfuegen(gkMock2);
        //test prepared mocks
        Assertions.assertEquals(1, gkMockKontonummer);
        Assertions.assertEquals(2, gkMock2Kontonummer);
        //setup
        Mockito.when(gkMock.abheben(betrag, gkMock.getAktuelleWaehrung())).thenReturn(true);
        Mockito.when(gkMock.getKontostand()).thenReturn(betrag);

        Mockito.when(gkMock.getInhaber()).thenReturn(kunde);
        Mockito.when(gkMock2.getInhaber()).thenReturn(kunde);
        Mockito.when(gkMock.ueberweisungAbsenden(betrag, "Mock2", gkMock2Kontonummer, bank.getBankleitzahl(), "Test")).thenReturn(true);
        Mockito.when(gkMock.getInhaber().getName()).thenReturn("Mock");
        Mockito.when(gkMock2.getInhaber().getName()).thenReturn("Mock2");


        //exercise
        Assertions.assertTrue(bank.geldAbheben(gkMockKontonummer, betrag));
        Assertions.assertEquals(betrag, bank.getKontostand(gkMockKontonummer));
        Assertions.assertTrue(bank.geldUeberweisen(gkMockKontonummer, gkMock2Kontonummer, betrag, "Test"));
    }

    @Test
    public void sparbuchTest() throws GesperrtException, KontonummerNichtVorhandenException {
        //prepare mocks
        Bank bank = new Bank(1234);
        Sparbuch sbMock = Mockito.mock(Sparbuch.class);
        long sbMockKontonummer = bank.mockEinfuegen(sbMock);
        //test prepared mocks
        Assertions.assertEquals(1, sbMockKontonummer);
        //setup
        Mockito.when(sbMock.abheben(betrag, sbMock.getAktuelleWaehrung())).thenReturn(true);
        Mockito.when(sbMock.getKontostand()).thenReturn(betrag);
        //exercise
        Assertions.assertTrue(bank.geldAbheben(sbMockKontonummer, betrag));
        Assertions.assertEquals(betrag, bank.getKontostand(sbMockKontonummer));

    }


    @Test
    public void badGiroKontoTest() throws GesperrtException, KontonummerNichtVorhandenException, nichtUeberweisungsfaehigExeption {
        //prepare mocks
        Bank bank = new Bank(1234);
        Kunde kunde = Mockito.mock(Kunde.class);
        Girokonto gkMock = Mockito.mock(Girokonto.class);
        long gkMockKontonummer = bank.mockEinfuegen(gkMock);
        Girokonto gkMock2 = Mockito.mock(Girokonto.class);
        long gkMock2Kontonummer = bank.mockEinfuegen(gkMock2);
        Sparbuch sbMock = Mockito.mock(Sparbuch.class);
        long sbMockKontonummer = bank.mockEinfuegen(sbMock);
        //test prepared mocks
        Assertions.assertEquals(1, gkMockKontonummer);
        Assertions.assertEquals(2, gkMock2Kontonummer);
        Assertions.assertEquals(3, sbMockKontonummer);
        //setup
        Mockito.when(gkMock.abheben(betrag, gkMock.getAktuelleWaehrung())).thenReturn(true);
        Mockito.when(gkMock.getKontostand()).thenReturn(betrag);

        Mockito.when(gkMock.getInhaber()).thenReturn(kunde);
        Mockito.when(gkMock2.getInhaber()).thenReturn(kunde);
        Mockito.when(gkMock.ueberweisungAbsenden(betrag, "Mock2", gkMock2Kontonummer, bank.getBankleitzahl(), "Test")).thenReturn(true);
        Mockito.when(gkMock.getInhaber().getName()).thenReturn("Mock");
        Mockito.when(gkMock2.getInhaber().getName()).thenReturn("Mock2");


        //exercise
        try {
            bank.geldAbheben(-1L, betrag);
            Assertions.fail();
        } catch (KontonummerNichtVorhandenException ignored) {}

        try {
            bank.geldUeberweisen(-1L, gkMock2Kontonummer, betrag, "Test");
            Assertions.fail();
        } catch (KontonummerNichtVorhandenException ignored) {}

        try {
            bank.geldUeberweisen(gkMockKontonummer, -1L, betrag, "Test");
            Assertions.fail();
        } catch (KontonummerNichtVorhandenException ignored) {}

        try {
            bank.geldUeberweisen(sbMockKontonummer, gkMock2Kontonummer, betrag, "Test");
            Assertions.fail();
        } catch (nichtUeberweisungsfaehigExeption ignored) {}

        try {
            bank.geldUeberweisen(gkMockKontonummer, sbMockKontonummer, betrag, "Test");
            Assertions.fail();
        } catch (nichtUeberweisungsfaehigExeption ignored) {}

    }
}
