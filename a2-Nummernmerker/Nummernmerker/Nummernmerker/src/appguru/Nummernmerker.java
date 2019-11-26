package appguru;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author lars
 */
public class Nummernmerker {

    public static Aufteilung BESTE_AUFTEILUNG; // speichert die bisher beste Aufteilung

    // Zweigt von der Nummer einen Block der Länge 2-4 ab
    public static void abspalten(Aufteilung bisher, String nummer) {
        if (nummer.equals("")) {
            if (bisher.bloeckeMitNull < BESTE_AUFTEILUNG.bloeckeMitNull) {
                BESTE_AUFTEILUNG = bisher;
            }
            return;
        }
        if (bisher.bloeckeMitNull >= BESTE_AUFTEILUNG.bloeckeMitNull) {
            return;
        }
        for (int i = 1; i < Math.min(nummer.length(), 4); i++) {
            String part = nummer.substring(0, i + 1);
            String remainder = nummer.substring(i + 1);
            abspalten(bisher.addPart(part), remainder);
        }
    }

    public static void main(String[] args) {
        Iterator<String> nummern;
        if (args.length == 1) { // Ein Argument gegeben
            // Ist es eine Zahl?
            boolean istZahl = true;
            for (int i = 0; i < args[0].length(); i++) {
                char c = args[0].charAt(i);
                if (c < '0' || c > '9') {
                    istZahl = false;
                    break;
                }
            }

            // Wenn es eine Zahl ist, dann einfach zum durchgehen vorbereiten
            if (istZahl) {
                nummern = Arrays.stream(new String[]{args[0]}).iterator();
            } else {
                // Ansonsten: Datei lesen
                File file = new File(args[0]);
                if (!file.exists() || !file.canRead()) {
                    System.out.println("Datei nicht existent oder nicht lesbar");
                }
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader(file));
                } catch (Exception ex) {
                    System.out.println("Datei nicht existent oder nicht lesbar");
                    return;
                }
                // Iterator erzeugen, der die Zahlen der Zeilen als Strings durchgeht
                nummern = new Iterator() {
                    private String line;

                    @Override
                    public boolean hasNext() {
                        return line != null;
                    }

                    @Override
                    public String next() {
                        try {
                            return (line = reader.readLine());
                        } catch (Exception ex) {
                            System.out.println("Datei nicht existent oder nicht lesbar");
                            System.exit(0);
                        }
                        return "";
                    }
                };
            }
        } else {
            // Ansonsten: einfach Argumente als Zahlen nehmen
            nummern = Arrays.stream(args).iterator();
        }
        // Alle Nummern durchgehen
        for (String nummer : new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return nummern;
            }
        }) {
            // Für jede die beste Aufteilung berechnen
            BESTE_AUFTEILUNG = new Aufteilung(Integer.MAX_VALUE, "KEINE"); // erstmal beste Aufteilung zurücksetzen
            abspalten(new Aufteilung(), nummer); // beste Aufteilung berechnen
            System.out.println(BESTE_AUFTEILUNG); // und ausgeben
        }
    }
}
