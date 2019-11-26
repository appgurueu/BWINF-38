# Lösung 4. Aufgabe "Urlaubsfahrt"

## Lösungsidee

Der zur Lösung entwickelte Algorithmus kann in drei Schritte aufgeteilt werden: 

### Schritt 0

Da keine explizite Garantie über das Eingabeformat vorliegt, werden die Tankstellen zunächst der Entfernung nach aufsteigend (näher am Start nach näher am Ziel) sortiert. Hierfür wird von Java ein **Quicksort** verwendet.

### Schritt 1

Es werden die vom Start aus mit der Tankfüllung erreichbaren Tankstellen bestimmt. Diese sind alle diejenigen, die maximal Tankfüllung/Verbrauch*100 Kilometer entfernt sind.

### Schritt 2

Die Tankstellen werden in der in [Schritt 0] bestimmten Reihenfolge durchgegangen. Für jede Tankstelle werden die von ihr aus mit voller Tankfüllung erreichbaren Tankstellen (alle die maximal Volle Tankfüllung/Verbrauch*100 Kilometer entfernt sind) bestimmt. Wenn eine Tankstelle so erreicht wird, vergleicht sie die bis zu ihr erfolgten Tankvorgänge auf diesem Weg mit ihrem aktuellen "Rekordhalter" (dem bisher "besten" Weg, der zu ihr gefunden wurde), sofern er existiert - sonst wurde gerade eben ein Weg zu ihr gefunden, der als bisher Bester gespeichert wird. Sind es weniger, wird die neue Route ihre "Bestroute", als Weg mit minimal vielen Tankvorgängen. Sind es gleich viele, werden die Kosten auf dem Weg verglichen - sind sie niedriger, haben wir einen neuen Rekordhalter.
Ist von einer Tankstelle aus das Ende erreichbar, wird sie mit anderen Tankstellen verglichen, von denen aus auch das Ende erreicht werden konnte. Ist die Route / der Pfad der über diese Tankstelle zum Ende führt "besser" - d.h. weniger Stopps oder geringere Kosten - haben wir einen neuen besten Weg zum Ziel gefunden.

So finden die Tankstellen der Reihe nach jeweils "beste" Routen zu sich - und können diese dann direkt an von ihnen erreichbare Tankstellen weitergeben.

Um zu bestimmen wie viel jeweils zu tanken ist, wird zunächst nicht gespeichert, wie viel getankt wird - beim nächsten Tankvorgang wird rückwirkend bestimmt, ob die vorherige Tankstelle billiger gewesen wäre - wenn ja, hätte man volltanken sollen; dies wird dann rückwirkend "gespeichert." Wenn nein, sollte man dort gerade so viel wie nötig tanken. An einem Beispiel:

1. Da der Weg möglichst kurz ist, kann keine Tankstelle übersprungen werden
2. Also reicht es, jeweils zwei Tankstellen zu vergleichen - es muss bei allen 3 getankt werden, nur wieviel ist die Frage
```
Start - Tankstelle A (billig) - Tankstelle B (sehr billig) - Tankstelle C (teuer) - Ziel
```
Es lohnt sich, zuerst bei Tankstelle A nur so viel zu tanken, wie zum Erreichen von Tankstelle B notwendig - denn dort ist der Sprit billiger. Wir stellen fest A > B, also tanken wir bei A nur so viel wie nötig. Wenn wir dann bei Tankstelle B ankommen, stellen wir fest, dass C noch teuerer ist - B < C. Also lohnt es sich, bei B vollzutanken - Sprit den wir von B noch übrig haben, müssen wir nicht bei C für einen höheren Preis erwerben. Bei C tanken wir dann nur noch soviel, wie zum Erreichen des Ziels notwendig. **Ist die zuerst kommende Tankstelle billiger, sollte man volltanken; sonst nur soviel, wie zum Erreichen der Nächsten notwendig.**

## Umsetzung

### Bibliotheken

* `java.io.File`, `java.io.FileReader`, `java.io.BufferedReader`: Zum Öffnen & Lesen der Dateien mit der Aufgabenstellung
* `java.util.Arrays`: Zum Sortieren der Eingabe
* `java.util.ArrayList`: Dynamisch erweiterbare Listen, um "beste Route" ([Route]-Klasse) zu speichern, oder erreichbare Tankstellen
* `java.util.Iterator`: Iterator, nützlich um `String.join` benutzen zu können

### Klassen

#### `Tankstelle`

Als grundlegendste Klasse implementieren wir die `Tankstelle`. Diese speichert: 

* Die Position auf dem Weg als Ganzzahl (`int`)
* Die Kosten in Cent als Ganzzahl (`int`)
* Den besten Weg, der zu ihr führt ([`Route`])
* Die von ihr aus erreichbaren Tankstellen (`ArrayList<Tankstelle>`)
  
#### `Tankvorgang`

Speichert die Tankstelle (jedoch als Referenz, nicht als Kopie) und wie viel getankt wurde (Gleitpunktzahl, `double`).

#### `Route`

Hauptsächlich eine Liste von Objekten der [`Tankvorgang`]-Klasse. 
Speichert zusätzlich noch die bisher angefallenen Kosten und den übrigen Sprit (Gleitpunktzahlen (`double`s), müssen so nicht stets neu berechnet werden).

### Verwendung

Ausgeben (einer) der "besten Routen" für eine gegebene Aufgabenstellung in einer Datei: `java -jar Urlaubsfahrt.jar <pfad_zur_datei>` (*angeben des **vollständigen** Pfades empfohlen*)

## Beispiele

### `fahrt1.txt`

`{java -jar "/home/lars/NetBeansProjects/Urlaubsfahrt/dist/Urlaubsfahrt.jar" "/home/lars/bwinf38-runde1/a4-Urlaubsfahrt/beispieldaten/fahrt1.txt"}`

### `fahrt2.txt`

`{java -jar "/home/lars/NetBeansProjects/Urlaubsfahrt/dist/Urlaubsfahrt.jar" "/home/lars/bwinf38-runde1/a4-Urlaubsfahrt/beispieldaten/fahrt2.txt"}`

### `fahrt3.txt`

`{java -jar "/home/lars/NetBeansProjects/Urlaubsfahrt/dist/Urlaubsfahrt.jar" "/home/lars/bwinf38-runde1/a4-Urlaubsfahrt/beispieldaten/fahrt3.txt"}`

### `fahrt4.txt`

`{java -jar "/home/lars/NetBeansProjects/Urlaubsfahrt/dist/Urlaubsfahrt.jar" "/home/lars/bwinf38-runde1/a4-Urlaubsfahrt/beispieldaten/fahrt4.txt"}`

### `fahrt5.txt`

`{java -jar "/home/lars/NetBeansProjects/Urlaubsfahrt/dist/Urlaubsfahrt.jar" "/home/lars/bwinf38-runde1/a4-Urlaubsfahrt/beispieldaten/fahrt5.txt"}`

## Quellcode

### `Tankstelle.java`

```java
{appguru/Tankstelle.java}
```

### `Tankvorgang.java`

```java
{appguru/Tankvorgang.java}
```

### `Route.java`

```java
{appguru/Route.java}
```

### `Urlaubsfahrt.java`

```java
{appguru/Urlaubsfahrt.java}
```
