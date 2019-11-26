package appguru;

import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author lars
 */

// Speichert eine Biberverteilung
public class Biberverteilung implements Iterable<String> {

    public int[] biber; // Array der Biber-Anzahlen = Behälter

    // Konstruktor
    public Biberverteilung(int[] biber) {
        this.biber = biber;
    }

    // Telepaartie: Erzeugt veränderte Kopie der Biberverteilung, gibt null zurück wenn die Telepaartie einen Behälter leert
    public Biberverteilung telepaartie(int b1, int b2) {
        if (biber[b2] == biber[b1]) { // Telepaartie leert b1 / b2
            return null; // null zurückgeben
        }
        // Array kopieren
        int[] copied = new int[biber.length];
        System.arraycopy(biber, 0, copied, 0, biber.length);
        // Falls Inhalt von b1 > Inhalt b2, vertausche b1 und b2, sodass b1 immer weniger Biber hat
        if (biber[b1] > biber[b2]) {
            int b1_backup = b1;
            b1 = b2;
            b2 = b1_backup;
        }
        // Ziehe von b2 die Biber von b1 ab (b2 hat ja mehr)
        copied[b2] -= copied[b1];
        // Verdoppple die Anzahl der Biber in b1 (dem mit weniger)
        copied[b1] *= 2;
        return new Biberverteilung(copied); // Gebe eine neue Biberverteilung mit dem kopierten Array zurück
    }

    @Override
    public String toString() {
        // Gibt eine Biberverteilung aus, Anzahlen mit Kommas getrennt
        return "(" + String.join(", ", this) + ")";
    }

    // Iterator, um String.join nutzen & Anzahlen als Strings durchgehen zu können
    class BiberIterator implements Iterator<String> {

        public int i;

        @Override
        public boolean hasNext() {
            return i < biber.length;
        }

        @Override
        public String next() {
            return Integer.toString(biber[i++]);
        }

    }

    @Override
    public Iterator<String> iterator() {
        return new BiberIterator(); // Iterator erzeugen & zurückgeben
    }

    @Override
    public boolean equals(Object object) {
        // Prüft, ob zwei Biberverteilungen gleich sind; nutzt hauptsächlich Arrays.equals
        if (object.getClass() != Biberverteilung.class) {
            return false;
        }
        Biberverteilung b = (Biberverteilung) object;
        return Arrays.equals(b.biber, this.biber);
    }

    @Override
    public int hashCode() {
        // Generiert einen Hash
        return Arrays.hashCode(biber);
    }
}
