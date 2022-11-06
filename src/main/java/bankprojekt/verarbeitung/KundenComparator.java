package bankprojekt.verarbeitung;

import java.util.Comparator;

public class KundenComparator implements Comparator<Kunde> {

    @Override
    public int compare(Kunde o1, Kunde o2) {
        return o1.getGeburtstag().compareTo(o2.getGeburtstag());
    }
}
