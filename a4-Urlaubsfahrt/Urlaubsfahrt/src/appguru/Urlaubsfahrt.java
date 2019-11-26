/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author lars
 */
public class Urlaubsfahrt {
    public static Urlaubsfahrt aufgabe;
    double verbrauch;
    int groesse_tank;
    int fuellung_tank;
    int laenge_strecke;
    double range, volle_range;
    Tankstelle[] tankstellen;
    HashSet<Tankstelle> erreichbar=new HashSet();
    HashSet<Tankstelle> rekordhalter=new HashSet();
    int min_steps_to=Integer.MAX_VALUE;
    
    void readFile(File file) throws FileNotFoundException, IOException {
        var reader=new BufferedReader(new FileReader(file));
        verbrauch=Integer.parseInt(reader.readLine())/100.0;
        groesse_tank=Integer.parseInt(reader.readLine());
        fuellung_tank=Integer.parseInt(reader.readLine());
        laenge_strecke=Integer.parseInt(reader.readLine());
        tankstellen=new Tankstelle[Integer.parseInt(reader.readLine())];
        for (int t=0; t < tankstellen.length; t++) {
            String[] strecke_und_preis=reader.readLine().split("\\s+");
            int strecke=Integer.parseInt(strecke_und_preis[0]);
            int preis=Integer.parseInt(strecke_und_preis[1]);
            Tankstelle tanke=new Tankstelle(strecke, preis);
            tankstellen[t]=tanke;
        }
        Arrays.sort(tankstellen, 0, tankstellen.length, (Object o1, Object o2) -> Integer.compare(((Tankstelle)o1).distanz, ((Tankstelle)o2).distanz));
        range=fuellung_tank/(double)verbrauch;
        volle_range=groesse_tank/(double)verbrauch;
    }
    
    public void algo() {
        if (range >= laenge_strecke) {
            System.out.println("0 mal tanken notwendig, Ziel direkt erreichbar");
            return;
        }
        for (int t=0; t < tankstellen.length; t++) {
            if (tankstellen[t].distanz > range) {
                System.out.println("le break: t="+t);
                break;
            }
            tankstellen[t].min_steps_to=0;
            tankstellen[t].best_route.fuel_left=fuellung_tank-(tankstellen[t].distanz*verbrauch);
            erreichbar.add(tankstellen[t]);
        }
        boolean solutionExists=false;
        Route beste_route=new Route();
        beste_route.kosten=Double.MAX_VALUE;
        for (int t=0; t < tankstellen.length; t++) {
            if (laenge_strecke - tankstellen[t].distanz <= volle_range) {
                if (tankstellen[t].min_steps_to < min_steps_to) {
                    rekordhalter=new HashSet();
                    min_steps_to=tankstellen[t].min_steps_to;
                }
                if (tankstellen[t].min_steps_to == min_steps_to) {
                    rekordhalter.add(tankstellen[t]);
                }
                tankstellen[t].makeFinal();
                solutionExists=true;
                Route contestant=tankstellen[t].best_route;
                if (contestant.kosten < beste_route.kosten) {
                    beste_route=contestant;
                }
                continue;
            }
            for (int t2=t+1; t2 < tankstellen.length; t2++) {
                if (tankstellen[t2].distanz-tankstellen[t].distanz > volle_range) {
                    break;
                }
                tankstellen[t].addErreichbar(tankstellen[t2]);
            }
        }
        if (!solutionExists) {
            System.out.println("Es existiert keine Lösung: die Tankstellen liegen zu weit auseinander, das Ziel kann nicht erreicht werden");
        } else {
            System.out.println(beste_route);
        }
        System.out.println(groesse_tank);
        /*for (Tankstelle t:rekordhalter) {
            t.lesRoutes();
            break;
            //System.out.println(t.lesRoutes());
        }*/
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        aufgabe=new Urlaubsfahrt();
        aufgabe.readFile(new File("/home/lars/bwinf38-runde1/a4-Urlaubsfahrt/beispieldaten/fahrt2.txt"));
        aufgabe.algo();
        //System.out.println(u.erreichbar);
    }
    
}
