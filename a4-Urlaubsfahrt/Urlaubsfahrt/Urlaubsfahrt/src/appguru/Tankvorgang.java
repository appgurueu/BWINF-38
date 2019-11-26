package appguru;

/**
 *
 * @author lars
 */
// SPeichert einen Tankvorgang
public class Tankvorgang {

    public Tankstelle tankstelle; // Tankstelle (Referenz)
    public double liter_getankt; // Getankte Liter

    // Konstruktor zum Initialisieren
    public Tankvorgang(Tankstelle tankstelle, double liter_getankt) {
        this.tankstelle = tankstelle;
        this.liter_getankt = liter_getankt;
    }

    @Override
    public String toString() {
        return "Bei " + tankstelle + ", " + liter_getankt + "l für "+(tankstelle.preis*liter_getankt/100)+"€ getankt";
    }
}
