package fortunesloot.Scenes;

import java.sql.SQLException;
import fortunesloot.models.DataRegist;
import fortunesloot.utils.Registdb;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginPage {
    private Stage primaryStage;
    private ObservableList<DataRegist> listUser;
    private Registdb registDb;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.listUser = FXCollections.observableArrayList();
    }

    public void show() {
        // membuat GridPane
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        
        // Membuat elemen-elemen UI
        // Label, TextField, PasswordField, Button
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 18; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;");
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-prompt-text-fill: gray; -fx-background-color: #F4F4F4; -fx-border-color: #A9A9A9; -fx-border-width: 1px; -fx-border-radius: 5px;");
        usernameField.setFont(Font.font("Arial", 14));
        usernameField.setPrefWidth(200);

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 18; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-prompt-text-fill: gray; -fx-background-color: #F4F4F4; -fx-border-color: #A9A9A9; -fx-border-width: 1px; -fx-border-radius: 5px;");
        passwordField.setFont(Font.font("Arial", 14));

        Button loginButton = new Button("Login");
        Button registButton = new Button("Regist");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-font-size: 12; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #ff0000;");
        errorLabel.setVisible(false);

        // Menambahkan elemen-elemen ke dalam VBox
        VBox vBoxUser = new VBox(5, usernameLabel, usernameField);
        VBox vBoxPass = new VBox(5, passwordLabel, passwordField, errorLabel);

        HBox hBoxBtn = new HBox(15, loginButton, registButton);

        // Menambahkan vBox ke dalam GridPane lalu simpan ke vBoxAll
        gridPane.add(vBoxUser, 0, 1);
        gridPane.add(vBoxPass, 0, 2);
        gridPane.add(hBoxBtn, 0, 3);
        
        VBox vBoxAll = new VBox(gridPane);
        vBoxAll.setAlignment(Pos.BOTTOM_CENTER);
        vBoxAll.setPadding(new Insets(0, 0, 130, 0));

        // Menambahkan event handler untuk tombol login
        loginButton.setOnAction(v -> {
            // mengambil teks dari tusername dan password field
            String username = usernameField.getText();
            String password = passwordField.getText();

            // kondisi kesalahan Apabila textfield kosong
            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Username dan Password Kosong!!");
                errorLabel.setVisible(true);

                // Membuat PauseTransition untuk mengubah visibilitas Label setelah 2 detik
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> errorLabel.setVisible(false));
                pause.play();
            } else {
                // method untuk membuat table apabila belum ada, sekaligus
                //Mendapatkan list semua pengguna dari database
                registDb = new Registdb();
                try {
                    listUser.addAll(registDb.getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // mengecek apakah dalam list sama dengan username dan password dalam listUser
                boolean confirm = false;
                for (DataRegist user : listUser) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        // Jika username dan password cocok, login berhasil
                        confirm = true;
                        break;
                    }
                }

                if (confirm) {
                    // Jika login berhasil, lanjut ke homePage
                    HomePage hp = new HomePage(primaryStage);
                    hp.show();
                } else {
                    // Jika login gagal, tampilkan pesan kesalahan
                    errorLabel.setText("Username atau password salah!");
                    errorLabel.setVisible(true);

                    // Membuat PauseTransition untuk mengubah visibilitas Label setelah 2 detik
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> errorLabel.setVisible(false));
                    pause.play();
                    usernameField.clear();
                    passwordField.clear();
                }
            }
        });

        // action button Regist
        registButton.setOnAction(v -> {
            RegistPage rp = new RegistPage(primaryStage);
            rp.show();
        });

        // menambahkan background
        Image bgImage = new Image("/images/bgLogin.png");
        ImageView bgIv = new ImageView(bgImage);

        bgIv.setOpacity(0.8);
        bgIv.setFitWidth(400);
        bgIv.setFitHeight(650);

        // scene
        StackPane sp = new StackPane(bgIv, vBoxAll);
        Scene scene = new Scene(sp, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
