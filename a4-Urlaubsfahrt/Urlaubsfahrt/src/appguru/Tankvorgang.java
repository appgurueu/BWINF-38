/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

/**
 *
 * @author lars
 */
public class Tankvorgang {
    public Tankstelle tankstelle;
    public double liter_getankt;

    public Tankvorgang(Tankstelle tankstelle, double liter_getankt) {
        this.tankstelle = tankstelle;
        this.liter_getankt = liter_getankt;
    }
    
    public double fahrbarerWeg() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "("+tankstelle+", "+liter_getankt+")";
    }
}
