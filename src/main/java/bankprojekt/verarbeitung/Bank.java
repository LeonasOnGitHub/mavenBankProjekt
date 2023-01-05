package bankprojekt.verarbeitung;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eine Bank die verschiedene Arteken von Konten und deren Überweisungen verwaltet.
 */
public class Bank implements Cloneable, Serializable {

    private Map<Long, Konto> kontoliste = new HashMap<>();
    private final long bankleitzahl;


    /**
     * erstellt eine Bank mit der angegebenen Bankleitzahl
     *
     * @param bankleitzahl
     */
    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
    }


    /**
     * @return die Bankleitzahl
     */
    public long getBankleitzahl() {
        return this.bankleitzahl;
    }

    /**
     * erstellt ein Girokonto für den angegebenen Kunden mit einer noch nicht vergebenen Kontonummer
     * trägt das neue Konto in die Kontoliste ein
     *
     * @param inhaber
     * @return die neue Kontonummer
     */
    public long girokontoErstellen(Kunde inhaber) {
        long neueKontonummer = neuKontonummer();
        Girokonto gKonto = new Girokonto(inhaber, neueKontonummer, 100);

        kontoliste.put(neueKontonummer, gKonto);
        return neueKontonummer;
    }

    /**
     * Sie fügt das gegebene Konto k (bei dem es sich genaugenommen um ein Mock-Objekt
     * handeln sollte) in die Kontenliste der Bank ein
     *
     * @param k Konto
     * @return die neue Kontonummer
     */
    public long mockEinfuegen(Konto k) {
        long neueKontonummer = neuKontonummer();
        kontoliste.put(neueKontonummer, k);

        return neueKontonummer;
    }

    /**
     * erstellt ein Sparbuch für den angegebenen Kunden mit einer noch nicht vergebenen Kontonummer
     * trägt das neue Konto in die Kontoliste ein
     *
     * @param inhaber
     * @return die neue Kontonummer
     */
    public long sparbuchErstellen(Kunde inhaber) {
        long neueKontonummer = neuKontonummer();
        Sparbuch sparbuch = new Sparbuch(inhaber, neueKontonummer);

        kontoliste.put(neueKontonummer, sparbuch);
        return neueKontonummer;
    }

    /**
     * @return eine Auflistung aller Kontonummern + Kontostand
     */
    public String getAlleKonten() {
        String konten = "\n";
        for (Long k : kontoliste.keySet()) {
            konten += k + ": " + kontoliste.get(k).getKontostand() + "\n";
        }

        return konten;
    }

    /**
     * @return eine Liste aller gültigen Kontonummern in der Bank
     */
    public List<Long> getAlleKontonummern() {
        ArrayList<Long> kontonummerListe = new ArrayList<>();
        for (Long k : kontoliste.keySet()) {
            kontonummerListe.add(k);
        }
        return kontonummerListe;
    }

    /**
     * prüft ob die angegebene Kontonummer gültig ist und hebt in diesem fall den Betrag vom Konto ab, falls nicht gibt sie false zurück
     *
     * @param von
     * @param betrag
     * @return ob die Abhebung geklappt hat
     * @throws GesperrtException
     * @throws KontonummerNichtVorhandenException
     */
    public boolean geldAbheben(long von, double betrag) throws GesperrtException, KontonummerNichtVorhandenException {
        if (kontoliste.containsKey(von)) {
            return kontoliste.get(von).abheben(betrag);
        }
        throw new KontonummerNichtVorhandenException(von);

    }

    /**
     * prüft ob die angegebene Kontonummer gültig ist und zahlt in diesem falls den Betrag auf das Konto
     *
     * @param auf
     * @param betrag
     * @throws KontonummerNichtVorhandenException
     */
    public void geldEinzahlen(long auf, double betrag) throws KontonummerNichtVorhandenException {
        if (kontoliste.containsKey(auf)) {
            kontoliste.get(auf).einzahlen(betrag);
        } else {
            throw new KontonummerNichtVorhandenException(auf);
        }
    }

    /**
     * löscht das Konto mit der angegebenen nummer
     *
     * @param nummer
     * @return ob die löschung erfolgreich war
     * @throws KontonummerNichtVorhandenException
     */
    public boolean kontoLoeschen(long nummer) throws KontonummerNichtVorhandenException {
        if (kontoliste.containsKey(nummer)) {
            kontoliste.remove(nummer);
            return true;
        }
        throw new KontonummerNichtVorhandenException(nummer);
    }

    /**
     * @param nummer
     * @return den Kontosatand des Kontos dessen Nummer angegebn wurde
     * @throws KontonummerNichtVorhandenException
     */
    public double getKontostand(long nummer) throws KontonummerNichtVorhandenException {
        if (kontoliste.containsKey(nummer)) {
            return kontoliste.get(nummer).getKontostand();
        }
        throw new KontonummerNichtVorhandenException(nummer);
    }

    /**
     * überweist den angegebenen Betrag von dem konto mit der vonKontonr zum Konto mit der zumKontonr
     * überprüft ob die Konten in dieser Bank exestieren und ob es Girokonten sind
     *
     * @param vonKontonr
     * @param nachKontonr
     * @param betrag
     * @param verwendungszweck
     * @return ob die überweisung erfolgreich war
     * @throws KontonummerNichtVorhandenException
     * @throws nichtUeberweisungsfaehigExeption
     * @throws GesperrtException
     */
    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws KontonummerNichtVorhandenException, nichtUeberweisungsfaehigExeption, GesperrtException {
        if (kontoliste.containsKey(vonKontonr)) {
            if (kontoliste.containsKey(nachKontonr)) {
                if (kontoliste.get(vonKontonr) instanceof Ueberweisungsfaehig) {
                    if (kontoliste.get(nachKontonr) instanceof Ueberweisungsfaehig) {
                        if (((Girokonto) kontoliste.get(vonKontonr)).ueberweisungAbsenden(betrag, kontoliste.get(vonKontonr).getInhaber().getName(), nachKontonr, this.bankleitzahl, verwendungszweck)) {
                            ((Girokonto) kontoliste.get(nachKontonr)).ueberweisungEmpfangen(betrag, kontoliste.get(nachKontonr).getInhaber().getName(), vonKontonr, this.bankleitzahl, verwendungszweck);
                            return true;
                        }
                        return false;
                    }
                    throw new nichtUeberweisungsfaehigExeption(nachKontonr);
                }
                throw new nichtUeberweisungsfaehigExeption(vonKontonr);
            }
            throw new KontonummerNichtVorhandenException(nachKontonr);
        }
        throw new KontonummerNichtVorhandenException(vonKontonr);
    }


    /**
     * Die neuen Kontonummern zählen immer weiter hoch, falls es eine Kontonummer schon gibt wird die nächste freie genutzt
     *
     * @return eine noch nicht vergebene Koontonummer
     */
    private long neuKontonummer() {
        long neueKontonummer = 0;
        for (long i = 1L; neueKontonummer == 0; i++) {
            if (!kontoliste.containsKey((long) kontoliste.size() + i)) {
                neueKontonummer = kontoliste.size() + i;
            }
        }
        return neueKontonummer;
    }

    /**
     * Erstellt eine Kope dieser Bank
     * @return das kpierte Bankobjekt
     */
    public Object clone() {
        byte[] bankAsBytes = new byte[0];
        //Objekt in array Kopieren
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            bankAsBytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Objekt aus Array erzeugen
        ByteArrayInputStream bis = new ByteArrayInputStream(bankAsBytes);
        Object bankKopie = null;
        try (ObjectInput in = new ObjectInputStream(bis)) {
             bankKopie = in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return bankKopie;
    }
}
