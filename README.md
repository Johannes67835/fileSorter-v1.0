# 📁 File Organizer

Ein Java-Desktop-Programm, das Dateien in einem Ordner automatisch anhand ihrer Dateiendung in Unterordner sortiert.

---

## Features

- Automatisches Sortieren nach Dateityp (Bilder, Dokumente, Videos, Musik, Archive, Code, ...)
- Dunkles UI im modernen Stil mit JavaFX
- Ordnerauswahl per Browse-Dialog
- Automatische Namenskonflikt-Auflösung (`datei_1.pdf`, `datei_2.pdf`, ...)
- Echtzeit-Statistik: verschobene Dateien, Sonstiges, Fehler
- Mehrsprachigkeit vorbereitet (ResourceBundle)

---

## Voraussetzungen

| Tool | Version |
|------|---------|
| JDK | 21 LTS |
| JavaFX SDK | 21.0.11 |
| IntelliJ IDEA | beliebig |

---

## Setup

### 1. Repository klonen

```bash
git clone https://github.com/dein-name/file-organizer.git
cd file-organizer
```

### 2. Projekt in IntelliJ öffnen

`File → Open` → den geklonten Ordner auswählen.

### 3. JDK setzen

`File → Project Structure → Project SDK` → **JDK 21** auswählen.

### 4. JavaFX SDK einbinden

JavaFX SDK herunterladen: https://gluonhq.com/products/javafx/
→ Version 21, passendes OS, ZIP entpacken (z. B. nach `C:\javafx-sdk-21`)

Dann in IntelliJ:

`File → Project Structure → Libraries → + → Java` → `javafx-sdk-21\lib` auswählen → OK

### 5. VM-Optionen setzen

`Run → Edit Configurations → Modify options → Add VM options`:

```
--module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls
```

Pfad entsprechend anpassen.

### 6. Starten

Run-Konfiguration `MainApp` auswählen und ▶ drücken.

---

## Projektstruktur

```
file-organizer/
├── src/
│   └── main/
│       ├── java/com/fileorganizer/
│       │   ├── Main.java              # Konsolen-Einstiegspunkt (Phase 1)
│       │   ├── MainApp.java           # JavaFX GUI (Phase 2)
│       │   ├── FileOrganizer.java     # Kernlogik (EVA-Prinzip)
│       │   └── OrganizeResult.java    # Ergebnis-Record
│       └── resources/
│           ├── style.css              # Dark-Theme Styles
│           ├── msg.properties         # Texte (Standard/Englisch)
│           └── msg_de.properties      # Texte Deutsch
├── FileOrganizer.iml
└── README.md
```

---

## Unterstützte Dateitypen

| Endungen | Zielordner |
|----------|------------|
| `.jpg` `.jpeg` `.png` `.gif` `.bmp` `.svg` `.webp` | Bilder |
| `.pdf` `.doc` `.docx` `.txt` `.odt` `.rtf` `.md` | Dokumente |
| `.xls` `.xlsx` `.csv` `.ods` | Tabellen |
| `.ppt` `.pptx` `.odp` | Präsentationen |
| `.mp4` `.mkv` `.avi` `.mov` `.wmv` `.flv` | Videos |
| `.mp3` `.wav` `.flac` `.aac` `.ogg` `.m4a` | Musik |
| `.zip` `.rar` `.7z` `.tar` `.gz` | Archive |
| `.java` `.py` `.js` `.html` `.css` `.json` `.sql` | Code |
| `.exe` `.msi` `.jar` `.bat` `.sh` | Programme |
| (alles andere) | Sonstiges |

---

## Verwendete Technologien

- **Java 21** mit NIO.2 API (`java.nio.file`)
- **JavaFX 21** für die GUI
- **ResourceBundle** für Mehrsprachigkeit

---

## Lizenz

MIT License — frei verwendbar für Schul- und Privatprojekte.
