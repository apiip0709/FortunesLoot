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
import javafx.scene.layout.GridPane;
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
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Membuat elemen-elemen UI
        // Label, TextField, Button
        Label nameLabel = new Label("Masukkan Nama Lengkap");
        TextField nameField = new TextField();

        Label ageLabel = new Label("Masukkan Umur");
        TextField ageField = new TextField();

        Label usernameLabel = new Label("Masukkan Username");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Masukkan Password");
        TextField passwordField = new TextField();

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        Label errorLabel = new Label();
        errorLabel.setVisible(false);

        // Menambahkan elemen-elemen ke dalam GridPane
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 0, 1);
        grid.add(ageLabel, 0, 2);
        grid.add(ageField, 0, 3);
        grid.add(usernameLabel, 0, 4);
        grid.add(usernameField, 0, 5);
        grid.add(passwordLabel, 0, 6);
        grid.add(passwordField, 0, 7);
        grid.add(errorLabel, 0, 8);
        grid.add(registerButton, 0, 9);
        grid.add(backButton, 0, 10);

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

        // scene
        Scene scene = new Scene(grid, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
        Registdb.closeConnection();
    }
}
