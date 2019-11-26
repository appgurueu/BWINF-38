package appguru;

import java.util.ArrayList;

/**
 *
 * @author lars
 */
public class Telepaartie {

    // Berechnet LLL(start), wobei start eine Biberverteilung ist, iterativ
    public static int LLL(Biberverteilung start) {
        ArrayList<Biberverteilung> verteilungen = new ArrayList(); // Verteilungen
        verteilungen.add(start);
        for (int s = 1;; s++) { // Schritte zählen
            // Verteilungen für den nächsten Schritt
            ArrayList<Biberverteilung> neue_verteilungen = new ArrayList();
            // Biberverteilungen durchgehen
            for (Biberverteilung v : verteilungen) {
                // Alle Möglichkeiten, Telepaartien durchzuführen durchgehen
                for (int i = 0; i <= 1; i++) {
                    for (int j = i + 1; j <= 2; j++) {
                        Biberverteilung v_neu = v.telepaartie(i, j); // Telepaartie durchführen
                        if (v_neu == null) { // Behälter geleert
                            return s; // Anzahl benötigter Schritte zurückgeben, fertig!
                        }
                        neue_verteilungen.add(v_neu); // Neue Verteilung speichern
                    }
                }
            }
            // Nächster Schritt, nächste Verteilungen
            verteilungen = neue_verteilungen;
        }
    }

    // Berechnet L(n) - Geht alle Kombinationen durch und findet die maximale LLL
    public static int L(int n) {
        // Wenn n <= 2 ist sowieso immer ein Behälter leer
        if (n <= 2) {
            return 0;
        }
        // Ansonsten: Initialisiere "Rekordhalter" mit dem kleinstmöglichen Wert
        int max_schritte = Integer.MIN_VALUE;
        // Gehe alle Möglichkeiten durch, Gefäße zu befüllen, möglichst ohne verschiedene Anordnungen
        for (int i = 1; i < n - 1; i++) {
            for (int j = n - i - 1; j >= 1; j--) {
                int k = n - j - i;
                int schritte = LLL(new Biberverteilung(new int[]{i, j, k})); // LLL berechnen
                if (schritte > max_schritte) { // wurden mehr Schritte benötigt? (ist die LLL größer?)
                    max_schritte = schritte; // neuer Rekordhalter
                }
            }
        }
        return max_schritte; // Rekordhalter zurückgeben
    }

    public static void main(String[] args) {
        try {
            if (args.length == 1) { // für ein Argument
                int n = Integer.parseInt(args[0]); // zu Zahl umwandeln
                if (n < 0) {
                    System.out.println("Fehler: n < 0");
                } else {
                    // L(n) berechnen und ausgeben
                    if (n == 0) {
                        System.out.println("L(0)=0");
                    }
                    for (int i = 1; i <= n; i++) {
                        System.out.println("L(" + i + ")=" + L(i));
                    }
                }
            } else if (args.length == 3) { // falls drei Argumente gegeben sind
                // lese Verteilung ein
                int[] verteilung = new int[3];
                boolean schon_null = false;
                for (byte b = 0; b < 3; b++) {
                    verteilung[b] = Integer.parseInt(args[b]); // zu Zahl umwandeln
                    if (verteilung[b] <= 0) {
                        schon_null = true; // ein Behälter ist schon <= null, fertig!
                    }
                }
                Biberverteilung vert = new Biberverteilung(verteilung);
                // für einen Behälter, der schon <= 0 ist direkt 0 ausgeben, sonst LLL berechnen
                System.out.println("LLL" + vert + "=" + (schon_null ? "0":LLL(vert)));
            } else {
                // Instruktionen
                System.out.println("Geben sie 1 (=n) oder 3 (=Biberverteilung) Argumente an.");
            }
        } catch (NumberFormatException ne) {
            // Fehlermeldung
            System.out.println("Keine korrekten Zahlen");
        }
    }

}
