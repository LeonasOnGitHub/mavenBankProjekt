package bankprojekt.verarbeitung;

public class Aktie implements Runnable {

    private String name;
    private String wertpapeirkennummer;
    private double kurs;


    public Aktie(String name, String wertpapeirkennummer, double kurs) {
        this.name = name;
        this.wertpapeirkennummer = wertpapeirkennummer;
        this.kurs = kurs;
        Runnable kursRechner = this::berechneKurs;
        Thread kat = new Thread(kursRechner);
        kat.start();
    }

    private void berechneKurs() {
        while (true) {
            double kursveränderung = (Math.random() * 6) - 3;
            this.kurs = this.kurs + (kursveränderung / 100);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public void run() {

    }

    public double getKurs() {
        return this.kurs;
    }

    public String getWertpapeirkennummer() {
        return wertpapeirkennummer;
    }
}
