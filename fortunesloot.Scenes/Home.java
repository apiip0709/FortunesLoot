package fortunesloot.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainPage {
    private Stage primaryStage;

    public MainPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
    }

    public void show() {
        HBox header = header();
        HBox sidebar = sideBar();

        // Content
        Label contentLabel = new Label("Welcome to Financial App!");
        contentLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        contentLabel.setAlignment(Pos.CENTER);
        contentLabel.setPadding(new Insets(50));

        StackPane content = new StackPane(contentLabel);

        // Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setBottom(sidebar);
        mainLayout.setCenter(content);

        // Scene
        Scene scene = new Scene(mainLayout, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private HBox header() {
        Label titleLabel = new Label("Fortune's Loot");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);
        HBox header = new HBox(titleLabel);
        header.setStyle("-fx-background-color: #2C3E50;");
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER);
            
        return header;        
    }
    
    private HBox sideBar() {
        // Sidebar
        Button exitButton = new Button("");

        // Set Action button
        exitButton.setOnAction(v -> {
            HomePage home = new HomePage(primaryStage);
            home.show();
        });

        exitButton.setOnMouseEntered(e -> {
            exitButton.setText("EXIT");
            exitButton.setStyle(" -fx-background-color: #3b536b; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Verdana;");
        });

        exitButton.setOnMouseExited(e -> {
            exitButton.setText("");
            exitButton.setStyle(" -fx-background-color: #34495E;");
        });

        HBox sidebar = new HBox(exitButton);
        sidebar.setStyle("-fx-background-color: #34495E;");
        sidebar.setSpacing(10);
        sidebar.setPadding(new Insets(5));
        sidebar.setAlignment(Pos.TOP_CENTER);
        
        return sidebar;
    }
}