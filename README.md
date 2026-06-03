# 📁 File Organizer

A Java desktop application that automatically sorts files in a folder into subfolders based on their file extension.

---

## Contact

**nomoon - Johannes**
- GitHub: [@Johannes67835](https://github.com/johannes67835)
- Email: contact@nomoon.org.uk

---

## Features

- Automatic sorting by file type (images, documents, videos, music, archives, code, ...)
- Dark UI with modern JavaFX styling
- Folder selection via browse dialog
- Automatic conflict resolution (`file_1.pdf`, `file_2.pdf`, ...)
- Real-time statistics: moved files, unknown types, errors
- Internationalization support via ResourceBundle

---

## Requirements

| Tool | Version |
|------|---------|
| JDK | 21 LTS |
| JavaFX SDK | 21.0.11 |
| IntelliJ IDEA | any |

---

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/your-name/file-organizer.git
cd file-organizer
```

### 2. Open in IntelliJ

`File → Open` → select the cloned folder.

### 3. Set the JDK

`File → Project Structure → Project SDK` → select **JDK 21**.

### 4. Add the JavaFX SDK

Download the JavaFX SDK: https://gluonhq.com/products/javafx/
→ Version 21, matching your OS, extract the ZIP (e.g. to `C:\javafx-sdk-21`)

Then in IntelliJ:

`File → Project Structure → Libraries → + → Java` → select `javafx-sdk-21\lib` → OK

### 5. Set VM options

`Run → Edit Configurations → Modify options → Add VM options`:

```
--module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls
```

Adjust the path accordingly.

### 6. Run

Select the `MainApp` run configuration and press ▶.

---

## Supported file types

| Extensions | Target folder |
|------------|---------------|
| `.jpg` `.jpeg` `.png` `.gif` `.bmp` `.svg` `.webp` | Images |
| `.pdf` `.doc` `.docx` `.txt` `.odt` `.rtf` `.md` | Documents |
| `.xls` `.xlsx` `.csv` `.ods` | Spreadsheets |
| `.ppt` `.pptx` `.odp` | Presentations |
| `.mp4` `.mkv` `.avi` `.mov` `.wmv` `.flv` | Videos |
| `.mp3` `.wav` `.flac` `.aac` `.ogg` `.m4a` | Music |
| `.zip` `.rar` `.7z` `.tar` `.gz` | Archives |
| `.java` `.py` `.js` `.html` `.css` `.json` `.sql` | Code |
| `.exe` `.msi` `.jar` `.bat` `.sh` | Programs |
| (everything else) | Other |

---

## Technologies

- **Java 21** with NIO.2 API (`java.nio.file`)
- **JavaFX 21** for the GUI
- **ResourceBundle** for internationalization

---

## License

MIT License — free to use for school and personal projects.
