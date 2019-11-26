package appguru;

import static appguru.Beet.BESTES_BEET;
import static appguru.Beet.NACHBARN_PRO_PUNKT;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;

/**
 *
 * @author lars
 */
public class Blumenbeet {
    
    public static byte[][] PUNKTE = new byte[8][8];

    // Setzt die Punkte p für nebenanderliegende Farben a und p
    public static void setzePunkte(byte a, byte b, byte p) {
        PUNKTE[a][b] = PUNKTE[b][a] = p;
    }

    // Gibt die Punkte für Farben a und b nebeneinander zurück
    public static int punkteFuer(byte farbe_a, byte farbe_b) {
        return PUNKTE[farbe_a][farbe_b];
    }

    public static final HashMap<String, Byte> FARBEN = new HashMap();
    public static byte ANZAHL_FARBEN = 0; // Anzahl Farben, die das Beet haben soll

    static {
        // Sieben Farben: blau, gelb, grün, orange, rosa, rot und türkis - werden Zahlen von 1-7 zugeordnet
        FARBEN.put("blau", (byte) 1);
        FARBEN.put("gelb", (byte) 2);
        FARBEN.put("gruen", (byte) 3);
        FARBEN.put("orange", (byte) 4);
        FARBEN.put("rosa", (byte) 5);
        FARBEN.put("rot", (byte) 6);
        FARBEN.put("tuerkis", (byte) 7);
    }

    // Liest die Aufgabenstellung
    public static void ladeAufgabe(File datei) throws Exception {
        BufferedReader leser = new BufferedReader(new FileReader(datei));
        ANZAHL_FARBEN = Byte.parseByte(leser.readLine());
        int i=Integer.parseInt(leser.readLine());
        String zeile;
        int n=0;
        while (n < 1 && (zeile = leser.readLine()) != null) {
            String[] teile = zeile.split(" ");
            setzePunkte(FARBEN.get(teile[0]), FARBEN.get(teile[1]), Byte.parseByte(teile[2]));
            n++;
        }
    }
    
    // Rekursive Brute-Force, beet = letztes Beet, n = wieviele Blumen schon gesetzt worden sind
    public static void bruteForce(Beet beet, int n) {
        // Alle Blumen gesetzt, werte aus
        if (n == 9) {
            // Besser als das beste bisherige Beet?
            if (beet.punkte > BESTES_BEET.punkte) {
                // Neuen Rekordhalter setzen
                BESTES_BEET = beet;
            }
            return;
        }
        int zu_setzende_blumen = 9 - n;
        // Maximum an verwendbaren Farben erreicht?
        boolean maximum_erreicht = Blumenbeet.ANZAHL_FARBEN == beet.anzahl_verwendet;
        // Zu wenig Farben bisher verwendet, ab jetzt stets neue
        boolean minimum_erreicht = Blumenbeet.ANZAHL_FARBEN - beet.anzahl_verwendet == zu_setzende_blumen;
        // Gehe alle sieben Möglichkeiten durch, eine Blume hinzuzufügen
        for (byte j = 1; j <= 7; j++) {
            // Wenn wir das Maximum erreicht haben, dürfen wir nur Farben wiederverwerten
            // Wenn wir das Minimum erreicht haben, dürfen wir nur bisher unbenutzte Farben verwenden, um zum Schluss noch genug Verschiedene zu haben
            if ((maximum_erreicht && !beet.verwendet[j - 1]) || (minimum_erreicht && beet.verwendet[j - 1])) {
                continue;
            }
            Beet neu = new Beet(beet); // Beet kopieren
            neu.fuegeBlumeHinzu(n, j); // Füge Blume hinzu
            // Dadurch neu bekommenene Punkte berechnen
            for (byte nachbar : NACHBARN_PRO_PUNKT[n]) {
                neu.punkte += punkteFuer(neu.beet[(byte) n], neu.beet[nachbar]);
            }
            // Alle Möglichkeiten für nächste Blume durchgehen
            bruteForce(neu, n + 1);
        }
    }

    public static void main(String[] args) {
        String pfad_zur_datei;
        if (args.length == 1) {
            // ein Argument gegeben = Pfad zur Datei
            pfad_zur_datei = args[0];
        } else {
            // Frage nach Pfad zur Datei
            System.out.println("Pfad zur Aufgabenstellung: ");
            Scanner eingabe=new Scanner(System.in);
            pfad_zur_datei=eingabe.nextLine();
            eingabe.close();
        }
        try {
            ladeAufgabe(new File(pfad_zur_datei)); // lade Aufgabe
        } catch (Exception e) {
            // Falls die Aufgabe nicht gelesen werden konnte
            System.out.println("Datei nicht lesbar oder falsch formattiert");
            return;
        }
        bruteForce(new Beet(), 0); // Führe Brute-Force aus
        System.out.println(Beet.BESTES_BEET); // Gebe bestes Beet aus
    }

}
