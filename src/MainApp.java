import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) {
        ResourceBundle bndle = ResourceBundle.getBundle("msg");

        // Hide the native OS frame completely
        stage.initStyle(StageStyle.UNDECORATED);

        // title bar
        HBox titleBar = new HBox();
        titleBar.getStyleClass().add("custom-title-bar");
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setFillHeight(true);

        Label titleLabel = new Label(bndle.getString("title"));
        titleLabel.getStyleClass().add("title-text");

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button minimizeBtn = new Button("—");
        minimizeBtn.getStyleClass().add("window-control-btn");
        minimizeBtn.setOnAction(e -> stage.setIconified(true));

        Button closeBtn = new Button("✕");
        closeBtn.getStyleClass().addAll("window-control-btn", "close-btn");
        closeBtn.setOnAction(e -> stage.close());

        titleBar.getChildren().addAll(titleLabel, spacer, minimizeBtn, closeBtn);

        // Enable window dragging
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // main body
        TextField pathField = new TextField();
        pathField.setPromptText(bndle.getString("label.path"));
        HBox.setHgrow(pathField, Priority.ALWAYS);

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
        startBtn.getStyleClass().add("primary-btn");

        Label statusLabel = new Label(bndle.getString("label.ready"));

        startBtn.setOnAction(e -> {
            FileOrganizer organizer = new FileOrganizer();
            OrganizeResult result   = organizer.organize(pathField.getText());
            statusLabel.setText(String.format(
                    bndle.getString("label.finished"), result.movedCount(), result.errorCount()
            ));
        });


        HBox topRow = new HBox(8, pathField, browseBtn);
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox contentBody = new VBox(14, topRow, startBtn, statusLabel);
        contentBody.getStyleClass().add("content-body");

        VBox root = new VBox(titleBar, contentBody);
        root.getStyleClass().add("main-root");

        Scene scene = new Scene(root, 580, 190);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}