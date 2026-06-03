import java.util.List;

/**
 * Ergebnis eines Organisierungsvorgangs.
 * Wird als unveränderlicher Record zurückgegeben.
 *
 * @param movedCount   Anzahl erfolgreich verschobener Dateien
 * @param unknownCount Anzahl Dateien mit unbekannter Endung (-> Sonstiges)
 * @param errorCount   Anzahl Dateien, die nicht verschoben werden konnten
 * @param errors       Liste der Fehlermeldungen
 */
public record OrganizeResult(
        int movedCount,
        int unknownCount,
        int errorCount,
        List<String> errors
) {}
