/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author lars
 */
public class Biberverteilung implements Iterable<String> {
    public int[] biber;
    public Biberverteilung(int[] biber) {
        this.biber=biber;
    }
    public Biberverteilung telepaartie(int b1, int b2) {
        if (biber[b2] == biber[b1]) {
            return null;
        }
        int[] copied=new int[biber.length];
        System.arraycopy(biber, 0, copied, 0, biber.length);
        if (biber[b1] > biber[b2]) {
            int b1_backup=b1;
            b1=b2;
            b2=b1_backup;
        }
        copied[b2]-=copied[b1];
        copied[b1]*=2;
        return new Biberverteilung(copied);
    }
    @Override
    public String toString() {
        
        return "("+String.join(", ", this)+")";
    }

    class Zeug implements Iterator<String> {
        public int i;

        @Override
        public boolean hasNext() {
            return i < biber.length;
        }

        @Override
        public String next() {
            return Integer.toString(biber[i++]);
        }
        
    }
    @Override
    public Iterator<String> iterator() {
        return new Zeug();
    }
    public boolean equals(Object object) {
        if (object.getClass() != Biberverteilung.class) {
            return false;
        }
        Biberverteilung b=(Biberverteilung)object;
        return Arrays.equals(b.biber, this.biber);
    }
    public int hashCode() {
        return Arrays.hashCode(biber);
    }
}
