package appguru;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author lars
 */
public class Nummernmerker {
    public static Aufteilung BESTE_AUFTEILUNG; // speichert die bisher beste Aufteilung
    
    // Zweigt von der Nummer einen Block der Länge 2-4 ab
    public static void split(Aufteilung bisher, String nummer) {
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
            String part = nummer.substring(0, i+1);
            String remainder = nummer.substring(i+1);
            split(bisher.addPart(part), remainder);
        }
    }

    public static void main(String[] args) {
        Iterator<String> nummern;
        if (args.length == 1) {
            isNumber: {
                for (int i=0; i < args[0].length(); i++) {
                    char c=args[0].charAt(i);
                    if (c < '0' || c > '9') {
                        nummern=Arrays.stream(new String[] {args[0]}).iterator();
                        break isNumber;
                    }
                }
                File file=new File(args[0]);
                if (!file.exists() || !file.canRead()) {
                    System.out.println("Datei nicht existent oder nicht lesbar");
                }
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException ex) {
                    System.out.println("Datei nicht gefunden");
                    return;
                }
                nummern = new Iterator() {
                    private String line;
                    @Override
                    public boolean hasNext() {
                        return line != null;
                    }

                    @Override
                    public String next() {
                        try {
                            return (line=reader.readLine());
                        } catch (IOException ex) {
                            System.out.println("I/O Fehler, Abbruch");
                            System.exit(0);
                        }
                        return "";
                    } 
                };
            }
        } else {
            nummern=Arrays.stream(args).iterator();
        }
        for (String nummer: new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return nummern;
            }
        }) {
            BESTE_AUFTEILUNG = new Aufteilung(Integer.MAX_VALUE, "KEINE");
            split(new Aufteilung(), nummer);
            System.out.println(BESTE_AUFTEILUNG);
        }
    }
}
