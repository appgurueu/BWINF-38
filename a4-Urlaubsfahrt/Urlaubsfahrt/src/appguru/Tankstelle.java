/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author lars
 */
public class Tankstelle {
    int distanz;
    int preis;
    public boolean isFinal;
    public int min_steps_to=Integer.MAX_VALUE/2;
    public HashSet<Tankstelle> best_parents=new HashSet();
    public HashSet<Tankstelle> erreichbar=new HashSet();
    public Route best_route=new Route();

    public Tankstelle(int distanz, int preis) {
        this.distanz = distanz;
        this.preis = preis;
    }
    
    public static void adjustBestRoutes(Tankstelle first, Tankstelle second) {
        if (!second.best_route.stopps.isEmpty()) {
            double zu_tanken;
            double min_zu_tanken=Urlaubsfahrt.aufgabe.verbrauch*(second.distanz-first.distanz); // Genau soviel wie noch nötig tanken
            Tankvorgang letzter=second.best_route.stopps.get(second.best_route.stopps.size()-1);
            if (letzter.tankstelle.preis < second.preis) {
                zu_tanken=Urlaubsfahrt.aufgabe.groesse_tank; // Volltanken
            } else { // Fall 2
                zu_tanken=min_zu_tanken;
            }
            double fuel_left=zu_tanken-min_zu_tanken; // Tank ist voll, (inklusives) oder es bleibt nix übrig
            zu_tanken-=first.best_route.fuel_left; // Können nicht überfüllen, und wollen sowieso weniger tanken in Fall 2
            second.best_route.fuel_left=first.best_route.fuel_left=fuel_left;
            if (fuel_left < 0) {
                System.out.println(fuel_left);
            }
            letzter.liter_getankt=zu_tanken;
            second.best_route.kosten+=zu_tanken*first.preis;
        }
    }
    
    // TODO FIX FINAL ADJUSTMENT
    public void makeFinal() {
        Urlaubsfahrt daten=Urlaubsfahrt.aufgabe;
        this.isFinal = true;
        double noch_zu_fahren=daten.laenge_strecke-this.distanz;
        //adjustBestRoutes(this);
        if (!best_route.stopps.isEmpty()) {
            adjustBestRoutes(best_route.stopps.get(best_route.stopps.size()-1).tankstelle,this);
        }
        //    Tankvorgang letztes_tanken=best_route.stopps.get(best_route.stopps.size()-1);
        //    noch_zu_fahren-=100*(letztes_tanken.liter_getankt/(double)daten.verbrauch)-(this.distanz-letztes_tanken.tankstelle.distanz);
        //}
        double zu_tanken=noch_zu_fahren*daten.verbrauch;
        zu_tanken-=best_route.fuel_left;
        best_route.stopps.add(new Tankvorgang(this, zu_tanken));
    }
    
    public Collection<List<Tankstelle>> lesRoutes() {
        Collection<List<Tankstelle>> tanken=new ArrayList();
        if (best_parents.isEmpty()) {
            ArrayList<Tankstelle> t=new ArrayList();
            t.add(this);
            tanken.add(t);
        } else {
            for (var p:best_parents) {
                for (List<Tankstelle> tss:p.lesRoutes()) {
                    List<Tankstelle> ts=new ArrayList();
                    ts.addAll(tss);
                    ts.add(this);
                    tanken.add(ts);
                }
            }
        }
        return tanken;
    }
    
    // t ist von uns aus erreichbar
    public void addErreichbar(Tankstelle t) {
        if (min_steps_to+1 < t.min_steps_to || (min_steps_to+1 == t.min_steps_to && best_route.kosten < t.best_route.kosten)) {
            t.best_route=new Route();
            t.best_route.stopps=new ArrayList();
            t.best_route.stopps.addAll(best_route.stopps);
            /*if (!best_route.stopps.isEmpty()) {
                double zu_tanken;
                double min_zu_tanken=Urlaubsfahrt.aufgabe.verbrauch*(t.distanz-distanz); // Genau soviel wie noch nötig tanken
                Tankvorgang letzter=best_route.stopps.get(best_route.stopps.size()-1);
                if (letzter.tankstelle.preis < t.preis) {
                    zu_tanken=Urlaubsfahrt.aufgabe.groesse_tank; // Volltanken
                } else { // Fall 2
                    zu_tanken=min_zu_tanken;
                }
                double fuel_left=min_zu_tanken-zu_tanken; // Tank ist voll, (inklusives) oder es bleibt nix übrig
                zu_tanken-=best_route.fuel_left; // Können nicht überfüllen, und wollen sowieso weniger tanken in Fall 2
                best_route.fuel_left=fuel_left;
                letzter.liter_getankt=zu_tanken;
                t.best_route.kosten+=zu_tanken*this.preis;
            }*/
            adjustBestRoutes(this, t);
            t.best_route.stopps.add(new Tankvorgang(this, 0));
            t.min_steps_to=min_steps_to+1;
        }
        erreichbar.add(t);
    }
    
    @Override
    public String toString() {
        return "(s="+distanz+", p="+preis+", t="+min_steps_to+")";
    }

    @Override
    public int hashCode() {
        return this.distanz;
    }

    @Override
    public boolean equals(Object obj) {
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
        return this.distanz == other.distanz;
    }
    
    
}
