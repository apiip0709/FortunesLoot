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
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;

public class LoginPage {
    private Stage primaryStage;
    private ObservableList<DataRegist> listUser;
    private Registdb registDb;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.listUser = FXCollections.observableArrayList();
    }

    public void show() {
        primaryStage.setTitle("Login Page");

        // Membuat GridPane sebagai layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Membuat elemen-elemen UI
        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button registButton = new Button("Regist");

        Label errorLabel = new Label();
        errorLabel.setVisible(false);

        // Menambahkan elemen-elemen ke dalam GridPane
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 0, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 0, 3);
        grid.add(loginButton, 0, 5);
        grid.add(registButton, 0, 6);
        grid.add(errorLabel, 0, 4, 2, 1);

        // Menambahkan event handler untuk tombol login
        loginButton.setOnAction(v -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Username dan Password Kosong!!");
                errorLabel.setVisible(true);

                // Membuat PauseTransition untuk mengubah visibilitas Label setelah 2 detik
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> errorLabel.setVisible(false));
                pause.play();
            } else {
                //Mendapatkan list semua pengguna dari database
                registDb = new Registdb();
                try {
                    listUser.addAll(registDb.getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

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

        registButton.setOnAction(v -> {
            RegistPage rp = new RegistPage(primaryStage);
            rp.show();
        });

        Scene scene = new Scene(grid, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
