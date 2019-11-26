# Lösung Aufgabe 2 "Nummernmerker"

## Lösungsidee

Wir verwenden eine **Brute-Force**, die alle Möglichkeiten, die Zahl in die gewünschten Blöcke aufzuteilen, ausprobiert, und dann davon eine der Besten auswählt.

Sei n die Länge der Zahl. Im worst case ist diese 30. Als einfache Abschätzung sind maximal 15 Blöcke möglich, pro Block ergeben sich maximal 3 Möglichkeiten (2er, 3er oder 4er Block). Natürlich braucht man (wenn man größere Blöcke verwendet) weniger, aber unsere Schätzung liegt sowieso nur darüber. Also maximal 3¹⁵=14.348.907 auszuprobierende Kombinationen (die tatsächliche Zahl liegt weit darunter).

## Umsetzung

Als Programmiersprache wird Java wegen einer guten Performance (zwar etwas langsamer als, aber doch vergleichbar mit C++) und weitgehender Plattformunabhängigkeit gewählt.

### Bibliotheken

* `java.io.File`, `java.io.FileReader`, `java.io.BufferedReader`: Um Dateien mit Zahlen lesen zu können
* `java.util.Arrays`: Für `Arrays.stream`, womit ein Array direkt in eine Stream der dann wiederum in einen durchgehbaren Iterator verwandelt werden kann
* `java.util.Iterator`: Um ebendiesen Iterator zu speichern

### Klassen

#### `Nummernmerker`

Hauptklasse. Lädt zunächst die Datei bzw. die Argumente und führt den Algorithmus aus (wo immer alle Möglichkeiten ausprobiert werden, einen 2er, 3er oder 4er Block vorne abzuspalten), und lädt die Aufgabe / die Zahlen (siehe [Verwendung]). Um möglichst viel Speicherplatz zu sparen, wird die Brute-Force rekursiv und nicht iterativ implementiert - als depth-first-search. Nicht mehr benötigte Zwischenergebnisse der schon abgesuchten Pfade können dann von der Garbage Collection gelöscht werden.
Und da die Rekursionstiefe maximal 15 beträgt, ist ein `StackOverflowError` ausgeschlossen.

#### `Aufteilung`

Wird für Aufteilungsobjekte benutzt. Speichert jeweils Aufteilung & die Zahl der Blöcke, die mit 0 beginnen.

### Verwendung

Ausgeben einer optimalen Aufteilung einer Zahl: `java -jar Nummernmerker.jar <zahl>`  
Ausgeben optimaler Aufteilungen mehrerer Zahlen: `java -jar Nummernmerker.jar <zahl_1> <zahl_2> <zahl_3> ... <zahl_n>`  
Ausgeben optimaler Aufteilungen von Zahlen aus einer Datei: `java -jar Nummernmerker.jar <pfad_zur_datei>`

## Beispiele

Die Aufteilungen der 6 angegeben Zahlen:
```
01 36 5400 606 
00 54 800 0000 51 79 734 
03 49 59 29 53 37 90 15 44 12 660 
53 19 97 48 790 22 72 560 76 20 179 
90 88 76 10 51 69 94 82 78 90 38 33 12 67 
01 10 000 0001 1000 100 11 11 11 10 10 11 
```

## Quellcode

### `Aufteilung.java`

```java
{appguru/Aufteilung.java}
```

### `Nummernmerker.java`

```java
{appguru/Nummernmerker.java}
```
