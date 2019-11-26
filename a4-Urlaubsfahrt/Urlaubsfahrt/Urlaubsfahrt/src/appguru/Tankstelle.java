package appguru;

import java.util.ArrayList;

/**
 *
 * @author lars
 */
public class Tankstelle {

    public int distanz;
    public int preis;
    public int min_stopps_bis = Integer.MAX_VALUE / 2;
    public boolean erreicht = false;
    public Route beste_route = new Route();

    public Tankstelle(int distanz, int preis) {
        this.distanz = distanz;
        this.preis = preis;
    }

    // Passt den letzten Tankvorgang an
    public static void passeLetztesTankenAn(Tankstelle vorige, Tankstelle naechste) {
        if (!naechste.beste_route.stopps.isEmpty()) {
            double zu_tanken;
            double min_zu_tanken = Urlaubsfahrt.VERBRAUCH * (naechste.distanz - vorige.distanz); // Genau soviel wie noch nötig tanken
            Tankvorgang letzter = naechste.beste_route.stopps.get(naechste.beste_route.stopps.size() - 1);
            if (letzter.tankstelle.preis < naechste.preis) {
                zu_tanken = Urlaubsfahrt.GROESSE_TANK; // Volltanken
            } else { // Fall 2
                zu_tanken = min_zu_tanken;
            }
            double sprit_uebrig = zu_tanken - min_zu_tanken; // Tank ist voll, oder es bleibt nix übrig
            zu_tanken -= vorige.beste_route.sprit_uebrig; // Können nicht überfüllen, und wollen sowieso weniger tanken in Fall 2
            naechste.beste_route.sprit_uebrig = vorige.beste_route.sprit_uebrig = sprit_uebrig; // Übrigen Sprit speichern
            letzter.liter_getankt = zu_tanken; // Rückwirkend hätten wir soviel tanken sollen
            naechste.beste_route.kosten += zu_tanken * vorige.preis;
        }
    }

    public void zielErreicht() {
        double noch_zu_fahren = Urlaubsfahrt.LAENGE_STRECKE - this.distanz;
        if (!beste_route.stopps.isEmpty()) {
            passeLetztesTankenAn(beste_route.stopps.get(beste_route.stopps.size() - 1).tankstelle, this);
        }
        double zu_tanken = noch_zu_fahren * Urlaubsfahrt.VERBRAUCH;
        zu_tanken -= beste_route.sprit_uebrig;
        beste_route.stopps.add(new Tankvorgang(this, zu_tanken));
        beste_route.kosten += zu_tanken * this.preis;
    }

    // t ist von uns aus erreichbar
    public void erreichbar(Tankstelle t) {
        if (min_stopps_bis + 1 < t.min_stopps_bis || (min_stopps_bis + 1 == t.min_stopps_bis && beste_route.kosten < t.beste_route.kosten)) {
            t.beste_route = new Route();
            t.beste_route.stopps = new ArrayList();
            t.beste_route.stopps.addAll(beste_route.stopps);
            passeLetztesTankenAn(this, t);
            t.beste_route.stopps.add(new Tankvorgang(this, 0));
            t.min_stopps_bis = min_stopps_bis + 1;
            if (erreicht) {
                t.erreicht=true;
            }
        }
    }

    @Override
    public String toString() { // Tankstelle schön formattieren
        return "Position " + distanz + " km, Preis " + (preis / 100) + "€/l"; // "(s="+distanz+", p="+preis+", t="+min_steps_to+")";
    }

    @Override
    public int hashCode() {
        return this.distanz; // Distanz als HashCode
    }

    @Override
    public boolean equals(Object obj) { // Vergleichsoperation
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tankstelle other = (Tankstelle) obj;
        return this.distanz == other.distanz; // Positionen zu vergleichen reicht per Aufgabenstellung
    }

}
