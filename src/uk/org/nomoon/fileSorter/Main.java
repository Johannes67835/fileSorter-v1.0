package uk.org.nomoon.fileSorter;

import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Teile des Codes wurden mit Claude gelöst, da ich das Wissen dazu noch nicht hatte
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ResourceBundle bdnl = ResourceBundle.getBundle("msg");
        System.out.print(bdnl.getString("prompt.path"));
        String inputPath = scanner.nextLine().trim();

        inputPath = inputPath.replace("\"", "");

        FileOrganizer organizer = new FileOrganizer();
        OrganizeResult result = organizer.organize(inputPath);

        System.out.printf(bdnl.getString("info.processed"), result.movedCount());
        System.out.printf(bdnl.getString("info.skipped"), result.unknownCount());
        System.out.printf(bdnl.getString("info.failed"), result.errorCount());

        if (!result.errors().isEmpty()) {
            System.out.println(bdnl.getString("info.failed.infos"));
            for (String error : result.errors()) {
                System.out.println(error);
            }
        }

        System.out.println(bdnl.getString("info.done"));
    }
}
