# Telepaartie

## Lösungsidee

Da bei günstigen Telepaartien recht "schnell" ein Behältnis leer zu sein scheint (konvergiert rasch), verwenden wir eine **Brute-Force**.
Zunächst stellen wir fest, dass Telepaartien symmetrisch sind: telepaartie(a, b) = telepaartie(b, a).
Somit ergeben sich immer nur 3 mögliche Telepaartien: Behälter 1 & 2, 1 & 3 oder 2 & 3.
Um also für eine Biberverteilung b die LLL(b) zu berechnen, müssen wir schließlich 3^n Möglichkeiten betrachten - da n meist relativ gering ist, wird im Normalfall eine sehr gute Laufzeit erzielt.

Ähnlich gehen wir beim zweiten Aufgabenteil vor: L(n) = maximale LLL(b) für alle b mit insgesamt n Bibern.
Also generieren wir nun alle Möglichkeiten, n Biber auf 3 Behälter zu verteilen.
Diese gehen wir dann alle durch und bestimmen wie oben beschrieben für jede die LLL(b). Von diesen wählen wir dann das Maximum = L(n).

## Umsetzung

**Etwas anders als in der Aufgabenstellung gefordert nur mit einem und nicht mit zwei Programmen umgesetzt, siehe [Verwendung].**

### Bibliotheken

* `java.util.ArrayList`: Dynamische Liste wird als Zwischenspeicher für die iterative Brute-Force verwendet
* `java.util.Arrays`: Nützliche Array-Helfermethoden. Verwendet, um `equals` und `hashCode` zu implementieren.
* `java.util.Iterator`: Schnittstelle, die von `String.join` gebraucht und von [BiberIterator] implementiert wird.

### Klassen

#### `Biberverteilung`

Speichert eine Biberverteilung mithilfe eines `int`-Arrays (da die Länge = 3 ja schon von Anfang an feststeht). 
Liefert eine Methode `telepaartie`, die zwei Indices von Behälter nimmt, eine Telepaartie durchführt und eine neue [Biberverteilung] zurückgibt.
Falls die Telepaartie einen der beiden Behälter leeren sollte, wird `null` zurückgegeben.

##### `Biberverteilung$BiberIterator`

Eine Nutzklasse, um die Java-Funktion `String.join` nutzen zu können, die eine `Iterable<String>` nimmt und bei `Biberverteilung.toString` zum Einsatz kommt. 
Außerdem kann die Biberverteilung so `Iterable<String>` implementieren und leichter durchgegangen werden.

#### `Telepaartie`

Die Hauptklasse. Enthält die `main`-Methode, lädt die Aufgabe aus den Argumenten - bei genau einem Argument wird L(n) berechnet, bei dreien wird die LLL der [Biberverteilung] berechnet. Enthält die brute-force Methoden die dies tun:

* `berechneL`: Nimmt ein n (`int`) und probiert alle Möglichkeiten aus, n auf drei Behälter zu verteilen: Mit 2 geschachtelten `for`-Schleifen werden zunächst in den ersten Behälter k = n-2 bis 1 Biber getan, dann in den zweiten bis zu n-k-1 - also maximal einer weniger als noch übrig sind - schließlich muss ja auch für den letzten noch min. ein Biber bleiben. In diesen werden einfach die übrigen Biber getan. Für n <= 2 gibt die Methode einfach 0 zurück, da alle Möglichkeiten direkt einen leeren Behälter enthalten würden.
* `berechneLLL`: Nimmt eine Biberverteilung und berechnet iterativ (mit einer jeweils auf 3^n wachsenden ArrayList) alle Möglichkeiten, Telepaartien durchzuführen - so lange, bis ein Behälter geleert werden kann, was meist recht früh der Fall ist. Anders als bei "Nummernmerker" ist hier eine "depth-first-search" nicht zielführend, da einige Pfade sehr lange sein können (bspw. 2000 Telepaartien) und schließlich sowieso verworfen werden müssen, da irrwitzige kürzere (bspw. 8 Telepaartien) existieren. Daher nutzen wir hier einen iterativen "level-order-tree-traversal" - der Baum wird Ebene für Ebene abgesucht, sodass wir auf diese kurzen Lösungen zuerst stoßen und nicht riskieren, lange, unnütze Pfade zu untersuchen. Dafür ist zwar der Speicherverbrauch höher, doch dies spielt bei den relativ kleinen Aufgaben (siehe [Beispiele]) keine Rolle, vor Allem, da die Telepaartie schnell konvergiert ((erfahrungsgemäß) ist nach ziemlich wenigen Schritten schon ein Gefäß leer).

### Verwendung

Berechnen der LLL(a, b, c): `java -jar Biberverteilung.jar a b c`

Berechnen der L(n): `java -jar Biberverteilung.jar n`

## Beispiele

### Verteilungen

#### `(2, 4, 7)`

```
{java -jar "/home/lars/NetBeansProjects/Telepaartie/dist/Telepaartie.jar" "2" "4" "7"}
```

#### `(3, 5, 7)`

```
{java -jar "/home/lars/NetBeansProjects/Telepaartie/dist/Telepaartie.jar" "3" "5" "7"}
```

#### `(80, 64, 32)`

```
{java -jar "/home/lars/NetBeansProjects/Telepaartie/dist/Telepaartie.jar" "80" "64" "32"}
```

### Maximale Leerlauflängen (`L(n)`)

#### `n=10`

```
{java -jar "/home/lars/NetBeansProjects/Telepaartie/dist/Telepaartie.jar" 10}
```

#### `n=100`

```
{java -jar "/home/lars/NetBeansProjects/Telepaartie/dist/Telepaartie.jar" 100}
```

## Quellcode

### `Biberverteilung.java`

```java
{appguru/Biberverteilung.java}
```

### `Telepaartie.java`

```java
{appguru/Telepaartie.java}
```