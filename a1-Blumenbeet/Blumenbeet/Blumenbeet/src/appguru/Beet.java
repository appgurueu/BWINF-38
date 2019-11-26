package appguru;

import java.util.ArrayList;

/**
 *
 * @author lars
 */
public class Beet {
    public static final byte[][] NACHBARN_PRO_PUNKT;
    public static Beet BESTES_BEET = new Beet();

    static {
        BESTES_BEET.punkte = Byte.MIN_VALUE;
        NACHBARN_PRO_PUNKT = new byte[9][]; // Nachbarn eines Punktes mit gegebenem Index
        for (byte x = 0; x < 3; x++) {
            for (byte y = 0; y < 3; y++) {
                // alle Punkte (x|y) durchgehen
                byte p1 = posToIndex(x, y); // Index berechnen
                ArrayList<Byte> nachbarn = new ArrayList(2); // Nachbarn (min. 2)
                for (byte x2 = 0; x2 < 3; x2++) {
                    for (byte y2 = 0; y2 < 3; y2++) {
                        // alle Punkte (x2|y2) durchgehen, Entfernungen berechnen
                        byte xd = (byte) (x - x2);
                        byte yd = (byte) (y - y2);
                        // Diagonaler Nachbar genau dann wenn x-Entfernung und y-Entfernung beide 1 oder -1 sind
                        boolean diagonal_neighbor = (xd == 1 && yd == 1) || (xd == -1 && yd == -1);
                        // Horizontaler Nachbar genau dann wenn eine von beiden Entfernungen 0 und die Andere 1 oder -1 ist
                        boolean other_neighbor = (xd == 0 && (yd == 1 || yd == -1)) || ((xd == 1 || xd == -1) && yd == 0);
                        // Wenn (x2|y2) Nachbar von (x|y) ist
                        if (diagonal_neighbor || other_neighbor) {
                            nachbarn.add(posToIndex(x2, y2)); // füge zu Nachbarn hinzu
                        }
                    }
                }
                // Konvertiere zu Array...
                byte[] nachbar_array = new byte[nachbarn.size()];
                for (byte i = 0; i < nachbarn.size(); i++) {
                    nachbar_array[i] = nachbarn.get(i);
                }
                NACHBARN_PRO_PUNKT[p1] = nachbar_array; // ...und speichere als Nachbarn von (x|y) ab
            }
        }
    }
    
    // Wandelt eine (x|y)-Koordinate in einen Index um
    public static byte posToIndex(byte x, byte y) {
        return (byte) (x * 3 + y);
    }

    // pti = posToIndex, abkürzende Schreibweise, casted außerdem (nützlich für toString)
    private static byte pti(int a, int b) {
        return posToIndex((byte) a, (byte) b);
    }

    public byte punkte = 0;
    public int anzahl_verwendet = 0;
    public byte[] beet = new byte[9];
    public boolean[] verwendet = new boolean[7];

    // Füge Blume an Stelle index hinzu
    public void fuegeBlumeHinzu(int index, byte blume) {
        beet[index] = blume; // Setze Blume
        blume -= 1;
        // Wurde diese Sorte Blume noch nicht verwendet?
        if (!verwendet[blume]) {
            // Speichere als verwendet
            verwendet[blume] = true;
            anzahl_verwendet++;
        }
    }

    public Beet() {}

    // Kopiert ein Beet
    public Beet(Beet beet) {
        this.anzahl_verwendet = beet.anzahl_verwendet;
        this.punkte = beet.punkte;
        this.beet = new byte[9];
        System.arraycopy(beet.beet, 0, this.beet, 0, beet.beet.length); // arraycopy um nicht Referenz zu übernehmen
        this.verwendet = new boolean[7];
        System.arraycopy(beet.verwendet, 0, this.verwendet, 0, beet.verwendet.length); // arraycopy um nicht Referenz zu übernehmen
    }

    @Override
    public String toString() {
        // Formattiert ein Beet
        return String.format("Punkte: "+punkte+"\nHochbeet:\n  %d  \n %d %d \n%d %d %d\n %d %d \n  %d  ",
                beet[pti(2, 0)],
                beet[pti(1, 0)], beet[pti(2, 1)],
                beet[pti(0, 0)], beet[pti(1, 1)], beet[pti(2, 2)],
                beet[pti(0, 1)], beet[pti(1, 2)],
                beet[pti(0, 2)]
        );
    }
}
