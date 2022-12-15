package bankprojekt.verarbeitung;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Aktie implements Runnable {

    private String name;
    private String wertpapeirkennummer;
    private double kurs;
    private ScheduledExecutorService service;
    private Runnable runBerechneKurs;


    public Aktie(String name, String wertpapeirkennummer, double kurs) {
        this.name = name;
        this.wertpapeirkennummer = wertpapeirkennummer;
        this.kurs = kurs;

        this.service = Executors.newSingleThreadScheduledExecutor();
        this.runBerechneKurs = this::berechneKurs;
        service.submit(runBerechneKurs);
    }

    private void berechneKurs() {
        service.schedule(this.runBerechneKurs, 100, TimeUnit.MILLISECONDS);
        double kursveränderung = (Math.random() * 6) - 3;
        this.kurs = this.kurs + (kursveränderung / 100);

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
