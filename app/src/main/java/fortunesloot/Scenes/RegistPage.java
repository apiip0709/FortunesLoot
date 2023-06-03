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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegistPage {
    private Stage primaryStage;
    private ObservableList<DataRegist> listUser;
    private Registdb registDb;

    public RegistPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.listUser = FXCollections.observableArrayList();
    }

    public void show() {
        // Membuat GridPane sebagai layout
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Membuat elemen-elemen UI
        // Label, TextField, Button
        Label nameLabel = new Label("Masukkan Nama Lengkap");
        nameLabel.setStyle("-fx-font-size: 15; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;");
        TextField nameField = new TextField();
        nameField.setStyle("-fx-prompt-text-fill: gray; -fx-background-color: #F4F4F4; -fx-border-color: #A9A9A9; -fx-border-width: 1px; -fx-border-radius: 5px;");
        nameField.setFont(Font.font("Arial", 14));

        Label ageLabel = new Label("Masukkan Umur");
        ageLabel.setStyle("-fx-font-size: 15; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;");
        TextField ageField = new TextField();
        ageField.setStyle("-fx-prompt-text-fill: gray; -fx-background-color: #F4F4F4; -fx-border-color: #A9A9A9; -fx-border-width: 1px; -fx-border-radius: 5px;");
        ageField.setFont(Font.font("Arial", 14));

        Label usernameLabel = new Label("Masukkan Username");
        usernameLabel.setStyle("-fx-font-size: 15; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;");
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-prompt-text-fill: gray; -fx-background-color: #F4F4F4; -fx-border-color: #A9A9A9; -fx-border-width: 1px; -fx-border-radius: 5px;");
        usernameField.setFont(Font.font("Arial", 14));
        usernameField.setPrefWidth(320);

        Label passwordLabel = new Label("Masukkan Password");
        passwordLabel.setStyle("-fx-font-size: 15; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;");
        TextField passwordField = new TextField();
        passwordField.setStyle("-fx-prompt-text-fill: gray; -fx-background-color: #F4F4F4; -fx-border-color: #A9A9A9; -fx-border-width: 1px; -fx-border-radius: 5px;");
        passwordField.setFont(Font.font("Arial", 14));

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-font-size: 12; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #ff0000;");
        errorLabel.setVisible(false);

        // Menambahkan elemen-elemen ke dalam VBox
        VBox vBoxName = new VBox(5, nameLabel, nameField);
        VBox vBoxAge = new VBox(5, ageLabel, ageField);
        VBox vBoxUser = new VBox(5, usernameLabel, usernameField);
        VBox vBoxPass = new VBox(5, passwordLabel, passwordField, errorLabel);

        HBox hBoxBtn = new HBox(15, backButton, registerButton);

        // Menambahkan elemen-elemen ke dalam GridPane
        grid.add(vBoxName, 0, 0);
        grid.add(vBoxAge, 0, 1);
        grid.add(vBoxUser, 0, 2);
        grid.add(vBoxPass, 0, 3);
        grid.add(hBoxBtn, 0, 4);

        VBox vBoxAll = new VBox(grid);
        vBoxAll.setAlignment(Pos.BOTTOM_LEFT);
        vBoxAll.setPadding(new Insets(0, 0, 70, 0));

        // Menambahkan event handler untuk tombol daftar
        registerButton.setOnAction(v -> {
            String namaLengkap = nameField.getText();
            String umurStr = ageField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            // kondisi apabila ada textfield belum terisi
            if (namaLengkap.isEmpty()||umurStr.isEmpty()||username.isEmpty()||password.isEmpty()) {
                errorLabel.setText("Isi Terlebih Dahulu");
                errorLabel.setVisible(true);

                // Membuat PauseTransition untuk mengubah visibilitas Label setelah 3 detik
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> errorLabel.setVisible(false));
                pause.play();
            } else {
                // Mendapatkan list semua pengguna dari database
                int umur = Integer.parseInt(umurStr);
                registDb = new Registdb();
                try {
                    listUser.addAll(registDb.getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                // Mengecek apakah username sudah ada dalam listUser
                boolean confirm = false;
                for (DataRegist userLoop : listUser) {
                    if (userLoop.getUsername().equals(username)) {
                        confirm = true;
                        break;
                    }
                }

                if (confirm) {
                    // Jika username sudah ada, tampilkan pesan kesalahan
                    errorLabel.setText("Username sudah digunakan!");
                    errorLabel.setVisible(true);

                    // Membuat PauseTransition untuk mengubah visibilitas Label setelah 3 detik
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> errorLabel.setVisible(false));
                    pause.play();

                    usernameField.clear();
                    passwordField.clear();
                } else {
                    // Membuat objek RegistPage untuk menyimpan data pengguna yang terdaftar
                    DataRegist user = new DataRegist(namaLengkap, umur, username, password);

                    // Jika username belum ada, tambahkan pengguna ke dalam daftar pengguna
                    listUser.add(user);

                    // Menyimpan data ke dalam database
                    Registdb.saveData(namaLengkap, umur, username, password);

                    errorLabel.setText("Berhasil Register!");
                    errorLabel.setVisible(true);

                    // Membuat PauseTransition untuk mengubah visibilitas Label setelah 3 detik
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> errorLabel.setVisible(false));
                    pause.play();

                    nameField.clear();
                    ageField.clear();
                    usernameField.clear();
                    passwordField.clear();
                }
            }
        });
        
        backButton.setOnAction(v -> {
            LoginPage lp = new LoginPage(primaryStage);
            lp.show();
        });

        // menambahkan background
        Image bgImage = new Image("/images/bgRegist.png");
        ImageView bgIv = new ImageView(bgImage);

        bgIv.setOpacity(0.8);
        bgIv.setFitWidth(400);
        bgIv.setFitHeight(650);

        // scene
        StackPane sp = new StackPane(bgIv, vBoxAll);
        Scene scene = new Scene(sp, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
        Registdb.closeConnection();
    }
}
