package fortunesloot.Scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {
    private Stage primaryStage;

    public HomePage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        Label label = new Label("\n");
        
        Button buttonMain = new Button("THRIFTY");
        buttonMain.setPrefHeight(50);
        buttonMain.setPrefWidth(200);
        buttonMain.setOnAction(v -> {
            MainPage mainPage = new MainPage(primaryStage);
            mainPage.show();
        });
        buttonMain.setOnMouseEntered(v -> {
        });
        buttonMain.setOnMouseExited(v -> {
        });

        VBox vBox = new VBox(270, label, buttonMain);
        vBox.setAlignment(Pos.CENTER);

        StackPane spLayout = new StackPane(vBox);
        Scene scene = new Scene(spLayout, 400, 650);
        
        primaryStage.setScene(scene);
    }
}
