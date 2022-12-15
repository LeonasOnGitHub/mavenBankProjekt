package spielereien;

import bankprojekt.verarbeitung.*;

import java.util.concurrent.Future;


public class Aktienspielerein {
    public static void main(String[] args) {
        Kunde k = new Kunde();
        Girokonto gk = new Girokonto(k, 123, 0);
        gk.einzahlen(1000);
        Aktie a = new Aktie("test", "1", 10);
        gk.kaufauftrag(a, 10, 9.99);
        for (int i = 0; i < 100; i++) {
            System.out.println("Kurs: " + a.getKurs());
            System.out.println("Kontostand: " + gk.getKontostand());
            //System.out.println(future);
            try {
                Thread.sleep(1001);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
