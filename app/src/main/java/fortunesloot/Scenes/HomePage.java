package fortunesloot.Scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {
    private Stage primaryStage;

    public HomePage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        // label untuk space
        Label label = new Label("\n");

        // menambahkan background
        Image bgImage = new Image("/images/bgHome.png");
        ImageView bgIv = new ImageView(bgImage);

        bgIv.setOpacity(0.8);
        bgIv.setFitWidth(400);
        bgIv.setFitHeight(650);
            
        // menmbuat button main
        Button buttonMain = new Button("THRIFTY");
        buttonMain.setStyle(" -fx-background-color: transparent; -fx-font-size: 23px; -fx-font-weight: bold; -fx-font-family: Verdana; -fx-text-fill: white;");
        buttonMain.setPrefHeight(50);
        buttonMain.setPrefWidth(200);

        // action button
        buttonMain.setOnAction(v -> {
            MainPage mainPage = new MainPage(primaryStage);
            mainPage.show();
        });

        buttonMain.setOnMouseEntered(v -> {
            buttonMain.setStyle(" -fx-background-radius: 25; -fx-background-color: red; -fx-font-size: 23px; -fx-font-weight: bold; -fx-font-family: Verdana; -fx-text-fill: white;");
        });

        buttonMain.setOnMouseExited(v -> {
            buttonMain.setStyle(" -fx-background-color: transparent; -fx-font-size: 23px; -fx-font-weight: bold; -fx-font-family: Verdana; -fx-text-fill: white;");
        });

        // membuat button exit
        Button buttonExit = new Button("EXIT");
        buttonExit.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");

        // action button
        buttonExit.setOnAction(v -> {
            LoginPage lp = new LoginPage(primaryStage);
            lp.show();
        });

        buttonExit.setOnMouseEntered(v -> {
            buttonExit.setStyle("-fx-background-radius: 25; -fx-background-color: red; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
        });

        buttonExit.setOnMouseExited(v -> {
            buttonExit.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");
        });

        // layout HomePage
        VBox vBox = new VBox(110, buttonMain, buttonExit);
        vBox.setAlignment(Pos.CENTER);
        VBox vBox2 = new VBox(360, label, vBox);
        vBox2.setAlignment(Pos.CENTER);

        StackPane spLayout = new StackPane(bgIv, vBox2);
        Scene scene = new Scene(spLayout, 400, 650);
        
        primaryStage.setScene(scene);
    }
}
