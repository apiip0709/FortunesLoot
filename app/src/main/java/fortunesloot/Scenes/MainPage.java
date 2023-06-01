package fortunesloot.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainPage {
    private Stage primaryStage;

    public MainPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
    }

    public void show() {
        // Content
        Label contentLabel = new Label("Welcome to Financial App!");
        contentLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        contentLabel.setAlignment(Pos.CENTER);
        contentLabel.setPadding(new Insets(50));

        StackPane content = new StackPane(contentLabel);
        content.setStyle("-fx-background-color: #ECF0F1;");

        // Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(content);

        // Scene
        Scene scene = new Scene(mainLayout, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
}
