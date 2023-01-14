package spielereien;

import bankprojekt.verarbeitung.*;

import java.util.concurrent.*;


public class Aktienspielerein {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Kunde k = new Kunde();
        Girokonto gk = new Girokonto(k, 123, 0);
        gk.einzahlen(1000);

        Aktie a1 = new Aktie("Aktie1", "1", 1);
        Aktie a2 = new Aktie("Aktie2", "2", 10);
        Aktie a3 = new Aktie("Aktie3", "3", 100);

        Future<Double> aks1 = gk.kaufauftrag(a1, 100, 0.99);
        Future<Double> aks2 = gk.kaufauftrag(a2, 10, 9.99);
        Future<Double> aks3 = gk.kaufauftrag(a3, 1, 99.9);

        System.out.println("!!!!!!!!!!!!!!! Aktie 1 gekauft bei : " + aks1.get() / 100 + " !!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!! Aktie 2 gekauft bei : " + aks2.get() / 10+ " !!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!! Aktie 3 gekauft bei : " + aks3.get()+ " !!!!!!!!!!!!!!!");
        System.out.println("Kontostand: " + gk.getKontostand());

        Future<Double> avks1 = gk.verkaufauftrag("1", 1.1);
        Future<Double> avks2 = gk.verkaufauftrag("2", 10.1);
        Future<Double> avks3 = gk.verkaufauftrag("3", 101);

        System.out.println("!!!!!!!!!!!!!!! Aktie 1 verkauft bei : " + avks1.get() / 100+ " !!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!! Aktie 2 verkauft bei : " + avks2.get() / 10+ " !!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!! Aktie 3 verkauft bei : " + avks3.get()+ " !!!!!!!!!!!!!!!");
        System.out.println("Kontostand: " + gk.getKontostand());


    }
}
