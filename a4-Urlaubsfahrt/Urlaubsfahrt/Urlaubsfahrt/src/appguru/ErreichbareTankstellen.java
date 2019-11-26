/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

import java.util.HashSet;

/**
 *
 * @author lars
 */
public class ErreichbareTankstellen extends HashSet<ErreichbareTankstellen> {
    public Tankstelle tankstelle;
    public boolean isFinal;

    public ErreichbareTankstellen(Tankstelle tankstelle) {
        super();
        this.tankstelle = tankstelle;
    }
}
