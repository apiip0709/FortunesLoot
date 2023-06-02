package fortunesloot.Scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class LoginPage {
    private Stage primaryStage;
    private TextField username;
    private PasswordField password;

    BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, new CornerRadii(5), null);
    Background background = new Background(backgroundFill);

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        Label label = new Label("Silahkan masukkan username dan password");

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(150);
        username.setMaxHeight(50);
        username.setAlignment(Pos.CENTER);
        
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(150);
        password.setMaxHeight(50);
        password.setAlignment(Pos.CENTER);

        Button login = new Button("Login");
        login.setBackground(background);
        login.setPrefHeight(10);
        login.setPrefWidth(75);
        login.setStyle("-fx-font-size: 15px; -fx-text-fill: white;");
        login.setOnAction(v -> {
            String user = username.getText();
            String pass = password.getText();
            String[] nameParts = pass.split(" ");

            if (nameParts.length <= 8 || nameParts.length >= 16) {
                HomePage homePage = new HomePage(primaryStage);
                homePage.show();
            } else {
                System.out.println("Login Failed. Try Again");
            }

        });

        VBox vBox = new VBox(10, label);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(username);
        vBox.getChildren().add(password);

        vBox.getChildren().add(login);

        Scene scene = new Scene(vBox, 400, 650);
        primaryStage.setScene(scene);
    }

}
