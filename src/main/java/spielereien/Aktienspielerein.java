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

      /*  ExecutorService service = Executors.newSingleThreadExecutor();
        Callable<Double> checkKurs = () -> gk.kaufauftrag(a1, 100, 0.99).get();
        System.out.println(service.submit(checkKurs));
*/

        Future<Double> aks1= gk.kaufauftrag(a1, 100, 0.99);
        Future<Double> aks2= gk.kaufauftrag(a2, 10, 9.99);
        Future<Double> aks3= gk.kaufauftrag(a3, 1, 99.9);

        for (int i = 0; i < 100; i++) {
           /* System.out.println("Aktie 1 Kues : " + a1.getKurs());
            System.out.println("Aktie 2 Kues : " + a2.getKurs());
            System.out.println("Aktie 3 Kues : " + a3.getKurs());
           */ if (aks1.isDone() && aks2.isDone() && aks3.isDone()) {
                System.out.println("Aktie 1 gekauft bei : " + aks1.get() / 100);
                System.out.println("Aktie 2 gekauft bei : " + aks2.get() / 10);
                System.out.println("Aktie 3 gekauft bei : " + aks3.get());
                System.out.println("Kontostand: " + gk.getKontostand());
                break;
            }
            try {
                Thread.sleep(1001);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
