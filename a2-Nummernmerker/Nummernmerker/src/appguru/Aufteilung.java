package appguru;

/**
 *
 * @author lars
 */
public class Aufteilung {
    public int bloeckeMitNull;
    public String darstellung;
    public Aufteilung() {
        bloeckeMitNull = 0;
        darstellung = "";
    }

    public Aufteilung(int bloeckeMitNull, String darstellung) {
        this.bloeckeMitNull = bloeckeMitNull;
        this.darstellung = darstellung;
    }
    
    public Aufteilung addPart(String part) {
        if (part.charAt(0) == '0') {
            return new Aufteilung(bloeckeMitNull+1, darstellung+part+" ");
        }
        return new Aufteilung(bloeckeMitNull, darstellung+part+" ");
    }
    
    @Override
    public String toString() {
        return darstellung;
    }
}
