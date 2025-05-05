# SoloDesk – Kunden- und Rechnungsverwaltung (JavaFX)

**SoloDesk** ist eine JavaFX-Desktopanwendung zur Verwaltung von Kunden, Rechnungen und Zahlungen.  
Die Software wurde im Rahmen der Projektpräsentation im Studiengang Softwareentwicklung an der IU Internationale Hochschule entwickelt.

## 📌 Funktionen
- Login-System mit SQLite-Authentifizierung
- Übersichtliches Dashboard mit Statistiken
- Kundenverwaltung (Anlegen, Bearbeiten, Löschen)
- Rechnungs- und Zahlungshistorie pro Kunde
- SQLite-Datenbank-Anbindung
- Modularer Aufbau mit JavaFX, FXML, CSS

## 🛠️ Technologien
- Java 17
- JavaFX 23-ea
- SQLite
- Maven
- JUnit (für Tests)

## ▶️ Projekt starten

### Mit Maven:
```
bash
mvn clean install
mvn javafx:run
```

## 🧪 Beispiel-Login

Benutzername: admin  
Passwort: 123456

## 📁 Projektstruktur

```
src/
├─ main/
│ ├─ java/com.warda.solodesk/ → Controller, Modelle, Views
│ ├─ resources/ → FXML, CSS, Bilder
├─ test/ → Unit-Tests
pom.xml → Maven-Konfiguration
solodesk.db → SQLite-Datenbank
```
## 🧾 Lizenz

Dieses Projekt steht unter der MIT License.  
Es darf frei genutzt, kopiert, verändert und weitergegeben werden – unter Angabe des Urhebers.

