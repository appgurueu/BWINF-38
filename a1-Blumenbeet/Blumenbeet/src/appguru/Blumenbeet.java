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
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author lars
 */
public class Blumenbeet {
    public static final Map<String, Byte> FARBEN=new HashMap();
    static {
        // Sieben Farben: blau, gelb, grün, orange, rosa, rot und türkis
        FARBEN.put("blau", (byte)1);
        FARBEN.put("gelb", (byte)2);
        FARBEN.put("gruen", (byte)3);
        FARBEN.put("orange", (byte)4);
        FARBEN.put("rosa", (byte)5);
        FARBEN.put("rot", (byte)6);
        FARBEN.put("tuerkis", (byte)7);
    }
    public byte min_farben;

    void readFile(File file) throws FileNotFoundException, IOException {
        BufferedReader reader=new BufferedReader(new FileReader(file));
        min_farben=Byte.parseByte(reader.readLine());
        reader.readLine();
        String line;
        while ((line=reader.readLine()) != null) {
            String[] teile=line.split(" ");
            Beet.setScore(FARBEN.get(teile[0]), FARBEN.get(teile[1]), Byte.parseByte(teile[2]));
        }
    }
    
    public static void main(String[] args) {
        Beet.setScore((byte)1, (byte)2, (byte)3);  // rote neben blauen
        Beet.setScore((byte)1, (byte)3, (byte)2);  // rote neben tuerkis
        Beet.bruteRec(new Beet(), 0);
    }
    
}
