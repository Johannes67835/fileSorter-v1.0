import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FileOrganizer {
    private static ResourceBundle bndl = ResourceBundle.getBundle("msg");

    private static final Map<String, String> EXTENSION_MAP = Map.ofEntries(

            // Bilder
            Map.entry(".jpg",  bndl.getString("folder.pictures")),
            Map.entry(".jpeg", bndl.getString("folder.pictures")),
            Map.entry(".png",  bndl.getString("folder.pictures")),
            Map.entry(".gif",  bndl.getString("folder.pictures")),
            Map.entry(".bmp",  bndl.getString("folder.pictures")),
            Map.entry(".svg",  bndl.getString("folder.pictures")),
            Map.entry(".webp", bndl.getString("folder.pictures")),
            Map.entry(".heic", bndl.getString("folder.pictures")),

            // Dokumente
            Map.entry(".pdf",  bndl.getString("folder.documents")),
            Map.entry(".doc",  bndl.getString("folder.documents")),
            Map.entry(".docx", bndl.getString("folder.documents")),
            Map.entry(".txt",  bndl.getString("folder.documents")),
            Map.entry(".odt",  bndl.getString("folder.documents")),
            Map.entry(".rtf",  bndl.getString("folder.documents")),
            Map.entry(".md",   bndl.getString("folder.documents")),

            // Tabellen
            Map.entry(".xls",  bndl.getString("folder.tables")),
            Map.entry(".xlsx", bndl.getString("folder.tables")),
            Map.entry(".csv",  bndl.getString("folder.tables")),
            Map.entry(".ods",  bndl.getString("folder.tables")),

            // Präsentationen
            Map.entry(".ppt",  bndl.getString("folder.presentations")),
            Map.entry(".pptx", bndl.getString("folder.presentations")),
            Map.entry(".odp",  bndl.getString("folder.presentations")),

            // Videos
            Map.entry(".mp4",  bndl.getString("folder.videos")),
            Map.entry(".mkv",  bndl.getString("folder.videos")),
            Map.entry(".avi",  bndl.getString("folder.videos")),
            Map.entry(".mov",  bndl.getString("folder.videos")),
            Map.entry(".wmv",  bndl.getString("folder.videos")),
            Map.entry(".flv",  bndl.getString("folder.videos")),
            Map.entry(".webm", bndl.getString("folder.videos")),

            // Musik
            Map.entry(".mp3",  bndl.getString("folder.music")),
            Map.entry(".wav",  bndl.getString("folder.music")),
            Map.entry(".flac", bndl.getString("folder.music")),
            Map.entry(".aac",  bndl.getString("folder.music")),
            Map.entry(".ogg",  bndl.getString("folder.music")),
            Map.entry(".m4a",  bndl.getString("folder.music")),

            // Archive
            Map.entry(".zip",  bndl.getString("folder.archives")),
            Map.entry(".rar",  bndl.getString("folder.archives")),
            Map.entry(".7z",   bndl.getString("folder.archives")),
            Map.entry(".tar",  bndl.getString("folder.archives")),
            Map.entry(".gz",   bndl.getString("folder.archives")),

            // Code
            Map.entry(".java", bndl.getString("folder.code")),
            Map.entry(".py",   bndl.getString("folder.code")),
            Map.entry(".js",   bndl.getString("folder.code")),
            Map.entry(".ts",   bndl.getString("folder.code")),
            Map.entry(".html", bndl.getString("folder.code")),
            Map.entry(".css",  bndl.getString("folder.code")),
            Map.entry(".json", bndl.getString("folder.code")),
            Map.entry(".xml",  bndl.getString("folder.code")),
            Map.entry(".sql",  bndl.getString("folder.code")),

            // Ausführbare Dateien
            Map.entry(".exe",  bndl.getString("folder.executables")),
            Map.entry(".msi",  bndl.getString("folder.executables")),
            Map.entry(".jar",  bndl.getString("folder.executables")),
            Map.entry(".sh",   bndl.getString("folder.executables")),
            Map.entry(".bat",  bndl.getString("folder.executables"))
    );

    public OrganizeResult organize(String sourcePath) {
        int moved   = 0;
        int unknown = 0;
        int errors  = 0;
        List<String> errorMessages = new ArrayList<>();

        Path source = Paths.get(sourcePath);

        if (!Files.exists(source)) {
            errorMessages.add(bndl.getString("warning.notFound") + sourcePath);
            return new OrganizeResult(0, 0, 1, errorMessages);
        }
        if (!Files.isDirectory(source)) {
            errorMessages.add(bndl.getString("warning.isFile") + sourcePath);
            return new OrganizeResult(0, 0, 1, errorMessages);
        }

        System.out.println(source.toAbsolutePath());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            for (Path entry : stream) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }

                String fileName = entry.getFileName().toString();

                int dotIndex    = fileName.lastIndexOf('.');
                String extension;
                if(dotIndex > 0) {
                    extension = fileName.substring(dotIndex).toLowerCase();
                } else {
                    extension = "";
                }

                String folderName = EXTENSION_MAP.getOrDefault(extension, bndl.getString("folder.others"));

                if (!EXTENSION_MAP.containsKey(extension)) {
                    unknown++;
                }

                try {
                    Path targetDir  = source.resolve(folderName);
                    Path targetFile = targetDir.resolve(fileName);
                    Files.createDirectories(targetDir);


                    targetFile = resolveConflict(targetFile);

                    Files.move(entry, targetFile, StandardCopyOption.ATOMIC_MOVE);

                    System.out.printf("  [OK] %-40s -> %s%n", fileName, folderName);
                    moved++;

                } catch (IOException e) {
                    System.out.printf("  [ERROR] %s: %s%n", fileName, e.getMessage());
                    errorMessages.add(fileName + ": " + e.getMessage());
                    errors++;
                }
            }
        } catch (IOException e) {
            errorMessages.add(bndl.getString("severe.!read") + e.getMessage());
            return new OrganizeResult(moved, unknown, errors + 1, errorMessages);
        }

        return new OrganizeResult(moved, unknown, errors, errorMessages);
    }

    /**
     * Falls eine Zieldatei bereits existiert, wird ein Zähler angehängt:
     * dokument.pdf -> dokument_1.pdf -> dokument_2.pdf ...
     * mit claude gemacht
     */
    private Path resolveConflict(Path target) {
        if (!Files.exists(target)) {
            return target;
        }

        String fileName  = target.getFileName().toString();
        Path   parentDir = target.getParent();
        int    dot       = fileName.lastIndexOf('.');

        String baseName  = (dot > 0) ? fileName.substring(0, dot) : fileName;
        String extension = (dot > 0) ? fileName.substring(dot) : "";

        int counter = 1;
        Path candidate;
        do {
            candidate = parentDir.resolve(baseName + "_" + counter + extension);
            counter++;
        } while (Files.exists(candidate));

        return candidate;
    }
}
