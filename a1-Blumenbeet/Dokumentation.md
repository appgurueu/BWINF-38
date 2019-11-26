# Lösung Aufgabe 1 "Blumenbeet"

## Lösungsidee

Wir verwenden eine **optimierte Brute-Force** um alle möglichen Blumenbeete zu generieren, in denen genau j verschiedene Blumenfarben vorkommen.
Wir generieren die Beete, in dem wir der Reihe nach Blumen platzieren, und jeweils die dadurch neu erworbenen Punkte hinzuaddieren.
Wenn wir feststellen, dass wir das Maximum an verwendbaren Farben (=j) schon ausgeschöpft haben, dann verwenden wir nur noch Farben wieder;
Wenn wir feststellen, dass wir weit unter dem Maximum liegen, und noch genau so viele Plätze frei sind, wie uns Farben fehlen, dann muss ab dann jedes Mal eine bisher noch nicht verwendete Farbe eingesetzt werden.
Dies sind maximal 7⁹=40.353.607 (rund 4*10⁹) Kombinationen - recht wenig.

## Umsetzung

### Bibliotheken

* `java.util.ArrayList`: Erweiterbarer Zwischenspeicher fürs Generieren einer Liste der Nachbarn eines Punktes (deren Länge erst nach dem Generieren bekannt ist)
* `java.util.HashMap`: Speichert konstante Zuordnung Farbe -> Nummer
* `java.io.File`, `java.io.FileReader`, `java.io.BufferedReader`: Nötig zum Lesen der Aufgabenstellungs-Datei
* `java.util.Scanner`: Nützlich zum eventuellen Einlesen einer Nutzereingabe (wenn keine Argumente gegeben sind)

### Klassen

#### `Beet`

Repräsentiert ein Beet.

Das Blumenbeet wird als 3x3-Array dargestellt, gewissermaßen "auf die Seite gekippt":

```
  1  
 2 3 
4 5 6
 7 8 
  9  
```

wird zu

```
1 3 6
2 5 8
4 7 9
```

Die Nachbarschaften sehen dann wie folgt aus:

```
1-3-6
|/|/|
2-5-8
|/|/|
4-7-9
```

Dieses 3x3-Array wird als eindimensionales 9-er Array dargestellt.

Es werden Byte-Arrays benutzt, da Bytes schon ausreichen, um 7 Zahlen abzubilden.
Die verwendeten Farben werden als Boolean-Array gespeichert - pro Farbe ein Platz, ob die Farbe verwendet wurde. Außerdem speichert das Beet noch, wie viele verschiedene Farben es enthält. Kommt eine Blume hinzu werden Array und Farbanzahl geupdated.
Weiter wird eine globale Variable gespeichert, welche Nachbarpositionen eine Position einer Blume auf dem Blumenbeet jeweils hat.

#### `Blumenbeet`

Die Main-Klasse des Programms. Enthält die Main-Methode (wo die Argumente eingelesen werden) und eine Methode um die Aufgabenstellung zu laden.
Enthält & führt auch die rekursiv implementierte optimierte Brute-Force aus. Diese enthält eine for-Schleife, die alle möglichen Farben durchgeht, jeweils das Beet kopiert & die Farbe setzt und dann wieder die Brute-Force mit dem neuen Beet ausführt. Wird festgestellt, dass das Maximum an zu setzenden Farben erreicht wurde, so werden nur noch Farben "wiederverwertet" - also nur noch Farben, die schon als "verwendet" gespeichert sind (im Boolean-Array). Wird aber festgestellt, dass einem noch so viele Farben fehlen wie noch Plätze im Beet frei sind, dann werden fürs weitere Generieren des Beetes nur neue Farben, die vorher noch nicht verwendet wurden, eingesetzt.
Die Anzahl der zu verwendenden Farben wird in globalen Variablen gespeichert. Ebenso wie eine konstante Zuordnung Farbe -> Nummer (`HashMap<String, Integer>`).

### Verwendung

Lösen einer Aufgabe: `java -jar Blumenbeet.jar <pfad_zur_datei>`

## Beispiele

Farben: Wie auf der Materialwebsite angegeben ("`blau 1, gelb 2, gruen 3, orange 4, rosa 5, rot 6 und tuerkis 7`")

### `blumen.txt`

```
{java -jar "/home/lars/NetBeansProjects/Blumenbeet/dist/Blumenbeet.jar" "/home/lars/bwinf38-runde1/a1-Blumenbeet/beispieldaten/blumen.txt"}
```

### `blumen1.txt`

```
{java -jar "/home/lars/NetBeansProjects/Blumenbeet/dist/Blumenbeet.jar" "/home/lars/bwinf38-runde1/a1-Blumenbeet/beispieldaten/blumen1.txt"}
```

### `blumen2.txt`

```
{java -jar "/home/lars/NetBeansProjects/Blumenbeet/dist/Blumenbeet.jar" "/home/lars/bwinf38-runde1/a1-Blumenbeet/beispieldaten/blumen2.txt"}
```

### `blumen3.txt`

```
{java -jar "/home/lars/NetBeansProjects/Blumenbeet/dist/Blumenbeet.jar" "/home/lars/bwinf38-runde1/a1-Blumenbeet/beispieldaten/blumen3.txt"}
```

### `blumen4.txt`

```
{java -jar "/home/lars/NetBeansProjects/Blumenbeet/dist/Blumenbeet.jar" "/home/lars/bwinf38-runde1/a1-Blumenbeet/beispieldaten/blumen4.txt"}
```

### `blumen5.txt`

```
{java -jar "/home/lars/NetBeansProjects/Blumenbeet/dist/Blumenbeet.jar" "/home/lars/bwinf38-runde1/a1-Blumenbeet/beispieldaten/blumen5.txt"}
```

## Quellcode

### `Beet.java`

```java
{appguru/Beet.java}
```
### `Blumenbeet.java`

```java
{appguru/Blumenbeet.java}
```