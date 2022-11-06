package spielereien;

import java.time.LocalDate;
import java.util.*;

import bankprojekt.verarbeitung.*;

/**
 * verwaltet eine Menge von Kunden
 *
 * @author Doro
 */
public class Kundenmenge {


    /**
     * erstellt eine Menge von Kunden und löscht die unnötigen
     * wieder
     *
     * @param args
     */
    public static void main(String[] args) {
        Kunde anna = new Kunde("Anna", "Müller", "hier", LocalDate.parse("1979-07-13"));
        Kunde berta = new Kunde("Berta", "Beerenbaum", "hier", LocalDate.parse("1980-03-15"));
        Kunde chris = new Kunde("Chris", "Tall", "hier", LocalDate.parse("1979-01-07"));
        Kunde anton = new Kunde("Anton", "Meier", "hier", LocalDate.parse("1982-10-23"));
        Kunde adalbert = new Kunde("Bert", "Chokowski", "hier", LocalDate.parse("1970-12-24"));

        Scanner tastatur = new Scanner(System.in);
        String gesucht = tastatur.nextLine();

        Map<Long, Konto> kontenliste = Map.of(
                1L, new Girokonto(anna, 1, 1000),
                2L, new Girokonto(anna, 2, 1000),
                3L, new Sparbuch(anna, 3),
                4L, new Girokonto(berta, 4, 1000),
                5L, new Sparbuch(berta, 5),
                6L, new Girokonto(adalbert, 6, 1000),
                7L, new Girokonto(chris, 7, 1000),
                8L, new Girokonto(chris, 8, 1000),
                9L, new Sparbuch(chris, 9)
        );


        Set<Kunde> kundenListe = new TreeSet<>(new KundenComparator());
        kundenListe.add(anna);
        kundenListe.add(berta);
        kundenListe.add(chris);
        kundenListe.add(anton);
        kundenListe.add(adalbert);


        for (Kunde k:kundenListe) {
            System.out.println(k);
        }






    }


}
