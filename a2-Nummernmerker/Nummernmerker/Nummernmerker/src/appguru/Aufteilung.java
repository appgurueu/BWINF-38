package appguru;

/**
 *
 * @author lars
 */

// Speichert eine Aufteilung einer Zahl
public class Aufteilung {

    public int bloeckeMitNull; // Zahl der Blöcke, die mit 0 beginnen
    public String darstellung; // Darstellung als Text

    // Konstruktor, erzeugt Aufteilung, setzt Variablen auf Anfangswerte
    public Aufteilung() {
        bloeckeMitNull = 0;
        darstellung = "";
    }

    // Anderer Konstruktor, setzt direkt Variablen auf gegebene Werte
    public Aufteilung(int bloeckeMitNull, String darstellung) {
        this.bloeckeMitNull = bloeckeMitNull;
        this.darstellung = darstellung;
    }

    // Fügt einen 2-4 stelligen Teil hinzu
    public Aufteilung addPart(String part) {
        // Wenn der Teil mit einer 0 beginnt
        if (part.charAt(0) == '0') {
            // Hat die einen zusätzlichen Block mit 0, und die Darstellung kommt hinzu
            return new Aufteilung(bloeckeMitNull + 1, darstellung + part + " ");
        }
        // Nur die Darstellung kommt hinzu
        return new Aufteilung(bloeckeMitNull, darstellung + part + " ");
    }

    @Override
    public String toString() {
        return darstellung; // Gibt einfach Darstellung zurück
    }
}
