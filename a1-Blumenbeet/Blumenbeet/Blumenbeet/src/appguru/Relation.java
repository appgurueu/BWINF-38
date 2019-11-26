/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

/**
 *
 * @author mueller.l8
 */
public class Relation {
    public byte a;
    public byte b;

    public Relation(byte a, byte b) {
        this.a = a;
        this.b = b;
    }
    
    @Override
    public String toString() {
        return "("+a+", "+b+")";
    }
}
