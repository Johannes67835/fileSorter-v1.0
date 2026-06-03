package uk.org.nomoon.fileSorter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.util.ResourceBundle;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        ResourceBundle bndle = ResourceBundle.getBundle("msg");
        TextField pathField = new TextField();
        pathField.setPromptText(bndle.getString("label.path"));
        pathField.setPrefWidth(400);

        Button browseBtn = new Button(bndle.getString("label.browse"));
        browseBtn.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle(bndle.getString("label.directory"));
            java.io.File dir = chooser.showDialog(stage);
            if (dir != null) {
                pathField.setText(dir.getAbsolutePath());
            }
        });

        Button startBtn = new Button(bndle.getString("label.sort"));
        Label  statusLabel = new Label(bndle.getString("label.ready"));

        startBtn.setOnAction(e -> {
            FileOrganizer organizer = new FileOrganizer();
            OrganizeResult result   = organizer.organize(pathField.getText());
            statusLabel.setText(String.format(
                    bndle.getString("label.finished"), result.movedCount(), result.errorCount()
            ));
        });

        HBox topRow = new HBox(8, pathField, browseBtn);
        VBox root   = new VBox(12, topRow, startBtn, statusLabel);
        root.setStyle("-fx-padding: 20;");

        stage.setTitle("File Organizer");
        Scene scene = new Scene(root, 550, 150);
        scene.getStylesheets().add(getClass().getResource("/uk/org/nomoon/fileSorter/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
