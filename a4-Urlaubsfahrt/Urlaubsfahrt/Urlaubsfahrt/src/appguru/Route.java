package appguru;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author lars
 */

// Route als Struktur zum Speichern
public class Route {
    public ArrayList<Tankvorgang> stopps=new ArrayList(); // Tankvorgänge
    public double kosten; // Kosten in Cent
    public double sprit_uebrig; // Sprit übrig in Liter
    
    @Override
    public String toString() {
        // Route schön formattieren - Stopps getrennt mit "->", Insgesamtkosten, Anzahl Stopps
        return String.join(" -> ", new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return stopps.stream().map(Tankvorgang::toString).iterator();
            }
        })+" - Kosten insgesamt: "+kosten/100+"€ - Stopps: " + stopps.size();
    }
}
