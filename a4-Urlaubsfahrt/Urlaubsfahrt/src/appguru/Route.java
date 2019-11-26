/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

import java.util.ArrayList;

/**
 *
 * @author lars
 */
public class Route {
    public ArrayList<Tankvorgang> stopps=new ArrayList();
    public double kosten;
    public double fuel_left;

    void addTankvorgang(Tankvorgang tankvorgang) {
        kosten+=tankvorgang.liter_getankt*tankvorgang.tankstelle.preis;
        stopps.add(tankvorgang);
    }
    
    public String toString() {
        return stopps.toString();
    }
}
