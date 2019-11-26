/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author lars
 */
public class Telepaartie {
    public HashMap<Biberverteilung, Integer> steps_needed=new HashMap();

    public void brutey(int n) {
        for (int i=1; i < n-1; i++) {
            for (int j=n-i-1; j >= 1; j--) {
                int k = n-j-i;
                brutez(new Biberverteilung(new int[] {i, j, k}), 0);
                //System.out.println(i+","+j+","+k);
            }
        }
    }
    
    public void brutez(Biberverteilung start, int steps) {
        if (start == null) {
            //System.out.println(steps);
            return;
        }
        //System.out.println(start);
        Integer min_steps=steps_needed.get(start);
        if (min_steps != null && min_steps < steps) {
            return;
        }
        steps_needed.put(start, steps);
        for (int i=0; i <= 1; i++) {
            for (int j=i+1; j <= 2; j++) {
                brutez(start.telepaartie(i, j), steps+1);
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Telepaartie t=new Telepaartie();
        t.brutez(new Biberverteilung(new int[] {1, 2, 3}), 0);
        t.brutey(100);
    }
    
}
