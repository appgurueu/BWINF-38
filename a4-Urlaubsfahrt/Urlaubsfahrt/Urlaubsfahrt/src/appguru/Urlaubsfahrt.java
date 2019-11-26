package appguru;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author lars
 */
public class Urlaubsfahrt {

    // Selbsterklärende Variablennamen, näheres in readFile
    public static double VERBRAUCH;
    public static int GROESSE_TANK;
    public static int FUELLUNG_TANK;
    public static int LAENGE_STRECKE;
    public static double ANFANGS_REICHWEITE;
    public static double VOLLE_REICHWEITE;
    public static Tankstelle[] TANKSTELLEN;
    public static int MIN_STOPPS_BIS = Integer.MAX_VALUE;

    // Liest die Aufgabenstellung
    public static void readFile(File file) throws Exception {
        var reader = new BufferedReader(new FileReader(file)); // Datei öffnen
        VERBRAUCH = Integer.parseInt(reader.readLine()) / 100.0; // Erste Zeile: Verbrauch pro 100km lesen, in Verbrauch pro km umrechnen, und speichern
        GROESSE_TANK = Integer.parseInt(reader.readLine()); // 2. Zeile: Tankgröße
        FUELLUNG_TANK = Integer.parseInt(reader.readLine()); // 3. Zeile: Tankfüllung
        LAENGE_STRECKE = Integer.parseInt(reader.readLine()); // 4. Zeile: Länge der Strecke
        TANKSTELLEN = new Tankstelle[Integer.parseInt(reader.readLine())]; // 5. Zeile: Anzahl Tankstellen, Array initialisieren
        for (int t = 0; t < TANKSTELLEN.length; t++) {
            // Strecke und Preis sind pro Zeile mit einem oder mehreren Leerzeichen getrennt, daher der Regex "\\s+"
            String[] strecke_und_preis = reader.readLine().split("\\s+");
            int strecke = Integer.parseInt(strecke_und_preis[0]);
            int preis = Integer.parseInt(strecke_und_preis[1]);
            Tankstelle tanke = new Tankstelle(strecke, preis); // Tankstelle erzeugen
            TANKSTELLEN[t] = tanke; // Und speichern
        }
        // Tankstellen der Strecke nach sortieren (nah am Start nach weit vom Start)
        Arrays.sort(TANKSTELLEN, 0, TANKSTELLEN.length, (Object o1, Object o2) -> Integer.compare(((Tankstelle) o1).distanz, ((Tankstelle) o2).distanz));
        ANFANGS_REICHWEITE = FUELLUNG_TANK / (double) VERBRAUCH; // Anfangsreichweite: Tankfüllung/Verbrauch
        VOLLE_REICHWEITE = GROESSE_TANK / (double) VERBRAUCH; // Maximale Reichweite: Volle Tankfüllung/Verbrauch
    }

    // Führt den Algorithmus zur Lösung durch
    public static void findeOptimalenWeg() {
        // Trivialfall: Ziel direkt erreichbar mit anfänglicher Tankfüllung
        if (ANFANGS_REICHWEITE >= LAENGE_STRECKE) {
            System.out.println("0 mal tanken notwendig, Ziel direkt erreichbar");
            return;
        }
        
        // Alle vom Start aus erreichbaren Tankstellen bestimmen
        for (int t = 0; t < TANKSTELLEN.length; t++) {
            if (TANKSTELLEN[t].distanz > ANFANGS_REICHWEITE) {
                break;
            }
            TANKSTELLEN[t].erreicht = true; // Tankstelle erreicht
            TANKSTELLEN[t].min_stopps_bis = 0; // Keine Stopps bis zur Tankstelle nötig
            TANKSTELLEN[t].beste_route.sprit_uebrig = FUELLUNG_TANK - (TANKSTELLEN[t].distanz * VERBRAUCH); // Bei Erreichen noch so viel Sprit übrig, wie von der Anfangsfüllung bleibt
        }
        
        // Tankstellen durchgehen, von Tankstellen aus erreichbare Tankstellen & Routen bestimmen
        boolean loesung_existiert = false; // existiert eine Lösung?
        Route beste_route = new Route(); // Rekordhalter-Route initialisieren
        beste_route.kosten = Double.MAX_VALUE;
        for (int t = 0; t < TANKSTELLEN.length; t++) {
            // Lässt sich von der Tankstelle aus das Ende erreichen?
            if (LAENGE_STRECKE - TANKSTELLEN[t].distanz <= VOLLE_REICHWEITE) {
                // Tankstelle muss erreichbar sein
                if (TANKSTELLEN[t].erreicht) {
                    loesung_existiert = true;
                    TANKSTELLEN[t].zielErreicht();
                    if (TANKSTELLEN[t].min_stopps_bis < MIN_STOPPS_BIS) { // Neuer Rekordhalter!
                        MIN_STOPPS_BIS = TANKSTELLEN[t].min_stopps_bis;
                        beste_route = TANKSTELLEN[t].beste_route;
                    } else if (TANKSTELLEN[t].min_stopps_bis == MIN_STOPPS_BIS) { // Gleich viele Stopps, aber vielleicht billiger?
                        if (TANKSTELLEN[t].beste_route.kosten < beste_route.kosten) { // Falls billiger
                            beste_route = TANKSTELLEN[t].beste_route; // Neuer Rekordhalter !
                        }
                    }
                }
            } else {
                // Welche anderen Tankstellen lassen sich von der Tankstelle aus erreichen
                for (int t2 = t + 1; t2 < TANKSTELLEN.length; t2++) {
                    if (TANKSTELLEN[t2].distanz - TANKSTELLEN[t].distanz > VOLLE_REICHWEITE) {
                        break;
                    }
                    TANKSTELLEN[t].erreichbar(TANKSTELLEN[t2]);
                }
            }
        }
        if (!loesung_existiert) { // Es existiert keine Lösung, das Ziel kann nicht erreicht werden
            System.out.println("Es existiert keine Lösung: die Tankstellen liegen zu weit auseinander, das Ziel kann nicht erreicht werden");
        } else { // Sonst: gebe gefundene Lösung aus
            System.out.println("Eine optimale Lösung ist: " + beste_route);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) { // Zu viele / zu wenige Argumente
            System.out.println("Argumente: <pfad_zur_datei>");
            return;
        }
        try {
            Urlaubsfahrt.readFile(new File(args[0])); // Datei lesen
        } catch (Exception ex) {
            // Fehlermeldung
            System.out.println("Datei existiert nicht / ist nicht lesbar / ist falsch formattiert.");
            return;
        }
        // Algorithmus starten
        Urlaubsfahrt.findeOptimalenWeg();
    }

}
