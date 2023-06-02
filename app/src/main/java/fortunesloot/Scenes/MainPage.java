package fortunesloot.Scenes;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainPage {
    private Stage primaryStage;
    private BorderPane mainLayout;
    private int totalPenghasilan;
    private int totalPengeluaran;

    public MainPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mainLayout = new BorderPane();
        this.totalPenghasilan = 0;
        this.totalPengeluaran = 0;       
    }

    public void show() {
        // Tampilan scene ditambah header dan sideBar
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
        this.mainLayout = mainLayout;

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
        Button buttonPenghasilan = buttonPenghasilan();
        Button buttonPengeluaran = buttonPengeluaran();

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

        HBox sidebar = new HBox(exitButton, buttonPenghasilan, buttonPengeluaran);
        sidebar.setStyle("-fx-background-color: #34495E;");
        sidebar.setSpacing(10);
        sidebar.setPadding(new Insets(5));
        sidebar.setAlignment(Pos.TOP_CENTER);
        
        return sidebar;
    }

    private Button buttonPenghasilan() {
        // membuat label
        Label label = new Label("PENGHASILAN");
        label.setPadding(new Insets(5, 5, 5, 5));
        label.setFont(Font.font(20));
        label.setStyle(" -fx-background-color: #00ff15e0; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Verdana;");

        // membuat textfield lalu disimpan di hBox
        TextField tfJenis = new TextField();
        tfJenis.setPromptText("Jenis Penghasilan");
        TextField tfJumlah = new TextField();
        tfJumlah.setPromptText("Berapa Banyak");
        HBox hBoxInput = new HBox(tfJenis, tfJumlah);
        hBoxInput.setSpacing(8);
        
        // membuat button lalu disimpan di hBox
        Button tambah = new Button("Tambah");
        tambah.setOnAction(v -> {
        });
        
        Button delete = new Button("Delete");
        delete.setOnAction(v -> {
        });

        HBox hBoxButton = new HBox(tambah, delete);
        hBoxButton.setSpacing(8);
        
        // membuat vBox
        VBox vBox = new VBox(label, hBoxInput, hBoxButton);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        StackPane content = new StackPane(vBox);

        // membuat button
        Button buttonPenghasilan = new Button();

        // action button
        buttonPenghasilan.setOnAction(e -> {
            // Hapus konten saat ini di mainLayout
            mainLayout.setCenter(null);

            // Tambahkan tableData ke konten
            mainLayout.setCenter(content);
        });

        buttonPenghasilan.setOnMouseEntered(e -> {
            buttonPenghasilan.setText("PENGHASILAN");
        });

        buttonPenghasilan.setOnMouseExited(e -> {
        });

        return buttonPenghasilan;
    }

    private Button buttonPengeluaran() {
        // membuat label
        Label label = new Label("PENGELUARAN");
        label.setPadding(new Insets(5, 5, 5, 5));
        label.setFont(Font.font(20));
        label.setStyle(" -fx-background-color: #00ff15e0; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Verdana;");

        // membuat textfield lalu disimpan di hBox
        TextField tfJenis = new TextField();
        tfJenis.setPromptText("Jenis Pengeluaran");
        TextField tfJumlah = new TextField();
        tfJumlah.setPromptText("Berapa Banyak");
        HBox hBoxInput = new HBox(tfJenis, tfJumlah);
        hBoxInput.setSpacing(8);
        
        // membuat button lalu disimpan di hBox
        Button tambah = new Button("Tambah");
        tambah.setOnAction(v -> {
        });
        
        Button delete = new Button("Delete");
        delete.setOnAction(v -> {
        });

        HBox hBoxButton = new HBox(tambah, delete);
        hBoxButton.setSpacing(8);
        
        // membuat vBox
        VBox vBox = new VBox(label, hBoxInput, hBoxButton);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        StackPane content = new StackPane(vBox);

        // membuat button
        Button buttonPengeluaran = new Button();

        // action button
        buttonPengeluaran.setOnAction(e -> {
            // Hapus konten saat ini di mainLayout
            mainLayout.setCenter(null);

            // Tambahkan tableData ke konten
            mainLayout.setCenter(content);
        });

        buttonPengeluaran.setOnMouseEntered(e -> {
            buttonPengeluaran.setText("PENGELUARAN");
        });

        buttonPengeluaran.setOnMouseExited(e -> {
        });

        return buttonPengeluaran;
    }
}
