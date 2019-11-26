/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mueller.l8
 */
public class Beet {
    //public int punkte = 0;
    public byte[] beet = new byte[9];
    public static final Relation[] RELATIONS;
    public static byte MAX_SCORE=-1;
    
    public static byte posToIndex(byte x, byte y) {
        return (byte)(x*3+y);
    }
    
    static {
        RELATIONS=new Relation[16];
        int p=0;
        for (byte x=0; x < 3; x++) {
            for (byte y=0; y < 3; y++) {
                for (byte x2=0; x2 < 3; x2++) {
                    for (byte y2=0; y2 < 3; y2++) {
                        byte xd=(byte)(x-x2); byte yd=(byte)(y-y2);
                        if ((xd == 1 && yd == 1) || ((xd >= 0 && yd >= 0 && xd <= 1 && yd <= 1) && xd != yd)) {
                            if (p >= 16) {
                                continue;
                            }
                            RELATIONS[p]=new Relation(posToIndex(x,y),posToIndex(x2,y2));
                            System.out.println(new Relation(posToIndex(x,y),posToIndex(x2,y2)));
                            p++;
                        }
                    }
                }
            }
        }
    }
    
    public static List<Beet> bruteAll() {
        List<Beet> beete=new ArrayList();
        beete.add(new Beet());
        for (int i=0; i < 9; i++) {
            List<Beet> neue_beete=new ArrayList();
            for (Beet beet:beete) {
                for (byte j=0; j < 7; j++) {
                    Beet neu=new Beet(beet);
                    neu.beet[i] = j;
                    neue_beete.add(beet);
                }
            }
            beete=neue_beete;
        }
        return beete;
    }
    
    public static void bruteRec(Beet beet, int n) {
        if (n == 9) {
            byte score=(byte)beet.getScore();
            if (score > MAX_SCORE) {
                System.out.println(beet);
                MAX_SCORE=score;
                System.out.println(score);
            }
            return;
        }
        for (byte j=1; j <= 7; j++) {
            Beet neu=new Beet(beet);
            neu.beet[n] = j;
            bruteRec(neu, n+1);
        }
    }
    
    public static byte[][] scores = new byte[8][8];
    
    public static void setScore(byte a, byte b, byte p) {
        scores[a][b]=scores[b][a]=p;
    }
    public static int getScore(byte a, byte b) {
       return scores[a][b];
    }
    
    public int getScore() {
        int score=0;
        for (Relation r:RELATIONS) {
           score+=getScore(beet[r.a], beet[r.b]);
        }
        return score;
    }
    public Beet(){
        this.beet=new byte[9];
    }
    public Beet(Beet beet) {
        this.beet=new byte[9];
        System.arraycopy(beet.beet, 0, this.beet, 0, beet.beet.length);
    }
    
    private byte pti(int a, int b) {
        return posToIndex((byte)a, (byte)b);
    }
    
    @Override
    public String toString() {
        return String.format("  %d  \n %d %d \n%d %d %d\n %d %d \n  %d  ",
                    beet[pti(2,0)],
                beet[pti(1,0)], beet[pti(2,1)],
            beet[pti(0,0)], beet[pti(1,1)], beet[pti(2,2)],
                beet[pti(0,1)], beet[pti(1,2)],
                    beet[pti(0,2)]
        );
    }
}
