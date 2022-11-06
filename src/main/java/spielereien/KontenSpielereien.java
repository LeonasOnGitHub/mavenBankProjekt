import java.time.LocalDate;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kontoart;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

/**
 * Testprogramm für Konten
 * @author Doro
 *
 */
public class KontenSpielereien {

	/**
	 * Testprogramm für Konten
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) {
		Kontoart art;
		String eingabe = "GIROKONTO";
		art = Kontoart.valueOf(eingabe);
		Kontoart[] prospekt = Kontoart.values();
		for(int i=0; i<prospekt.length; i++) {
			art = prospekt[i];
			System.out.println(art.name() + ": " + art.ordinal() + " - " 
				+ art.getWerbebotschaft());
		}
		
		Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));

		Girokonto meinGiro = new Girokonto(ich, 1234, 1000.0);
		meinGiro.einzahlen(50);
		System.out.println(meinGiro);
		
		Sparbuch meinSpar = new Sparbuch(ich, 9876);
		meinSpar.einzahlen(50);
		try
		{
			boolean hatGeklappt = meinSpar.abheben(70);
			System.out.println("Abhebung hat geklappt: " + hatGeklappt);
			System.out.println(meinSpar);
		}
		catch (GesperrtException e)
		{
			System.out.println("Zugriff auf gesperrtes Konto - Polizei rufen!");
		}
		
		Konto meins = new Girokonto(ich, 97643, 300);
		if(meins instanceof Girokonto)
			System.out.println(((Girokonto) meins).getDispo());
		System.out.println("-.-----------------");
		meins.ausgeben();   //--> toString von Girokonto!!!
	}

}
