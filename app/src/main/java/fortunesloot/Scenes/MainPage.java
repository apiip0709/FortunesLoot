package fortunesloot.Scenes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import fortunesloot.models.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainPage {
    private Stage primaryStage;
    private BorderPane mainLayout;
    public int totalPenghasilan;
    public int totalPengeluaran;
    private ObservableList<Data> listPenghasilan;
    private ObservableList<Data> listPengeluaran;

    public MainPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mainLayout = new BorderPane();
        this.totalPenghasilan = 0;
        this.totalPengeluaran = 0;   
        this.listPenghasilan = FXCollections.observableArrayList();
        this.listPengeluaran = FXCollections.observableArrayList();
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

        // set button Image Icon
        Image icon = new Image("/images/iconhome.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);
        exitButton.setGraphic(iconView);
        exitButton.setContentDisplay(ContentDisplay.LEFT);
        exitButton.setStyle(" -fx-background-color: #34495E; -fx-text-fill: white;");
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
        // Membuat Table View
        TableView<Data> tableData = new TableView<>();
        
        // Table Column
        TableColumn<Data, String> column1 = new TableColumn<>("Jenis");
        TableColumn<Data, Integer> column2 = new TableColumn<>("Jumlah");
        TableColumn<Data, String> column3 = new TableColumn<>("Tanggal");

        // Pasangkan
        column1.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        column2.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        column3.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        
        column1.setPrefWidth(150);
        column2.setPrefWidth(100);
        column3.setPrefWidth(140);

        // tambah colum ke table
        tableData.getColumns().addAll(column1, column2, column3);

        // beri nilai
        tableData.setItems(listPenghasilan);

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
            String jenis = tfJenis.getText();
            int jumlah = Integer.parseInt(tfJumlah.getText());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String tanggal = now.format(formatter);
            listPenghasilan.add(new Data(jenis, jumlah, tanggal));
            totalPenghasilan += jumlah;
            tfJenis.clear();
            tfJumlah.clear();
        });
        
        Button delete = new Button("Delete");
        delete.setOnAction(v -> {
            int index = tableData.getSelectionModel().getSelectedIndex();
            int jumlah = listPenghasilan.get(index).getJumlah();
            listPenghasilan.remove(index);
            totalPenghasilan -= jumlah;
        });

        HBox hBoxButton = new HBox(tambah, delete);
        hBoxButton.setSpacing(8);
        
        // membuat vBox
        VBox vBox = new VBox(label, hBoxInput, hBoxButton, tableData);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        
        // menambahkan Background
        Image bgImage = new Image("/images/background.jpg");
        ImageView bgIv = new ImageView(bgImage);

        bgIv.setOpacity(0.8);
        bgIv.setFitWidth(400);
        bgIv.setFitHeight(530);

        StackPane content = new StackPane(bgIv, vBox);

        // set button Image Icon
        Button buttonPenghasilan = new Button();
        Image icon = new Image("/images/iconHasil.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);
        buttonPenghasilan.setGraphic(iconView);
        buttonPenghasilan.setContentDisplay(ContentDisplay.LEFT);
        buttonPenghasilan.setStyle(" -fx-background-color: #34495E;");

        // action button
        buttonPenghasilan.setOnAction(e -> {
            // Hapus konten saat ini di mainLayout
            mainLayout.setCenter(null);

            // Tambahkan tableData ke konten
            mainLayout.setCenter(content);
        });

        buttonPenghasilan.setOnMouseEntered(e -> {
            buttonPenghasilan.setText("PENGHASILAN");
            buttonPenghasilan.setStyle(" -fx-background-color: #3b536b; -fx-text-fill: #00ff15e0; -fx-font-weight: bold; -fx-font-family: Verdana;");
        });

        buttonPenghasilan.setOnMouseExited(e -> {
            buttonPenghasilan.setText("");
            buttonPenghasilan.setStyle(" -fx-background-color: #34495E;");
        });

        return buttonPenghasilan;
    }

    private Button buttonPengeluaran() {
        // Membuat Table View
        TableView<Data> tableData = new TableView<>();
        
        // Table Column
        TableColumn<Data, String> column1 = new TableColumn<>("Jenis");
        TableColumn<Data, Integer> column2 = new TableColumn<>("Jumlah");
        TableColumn<Data, LocalDate> column3 = new TableColumn<>("Tanggal");

        // Pasangkan
        column1.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        column2.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        column3.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        
        column1.setPrefWidth(150);
        column2.setPrefWidth(100);
        column3.setPrefWidth(140);

        // tambah colum ke table
        tableData.getColumns().addAll(column1, column2, column3);

        // beri nilai
        tableData.setItems(listPengeluaran);

        // membuat label
        Label label = new Label("PENGELUARAN");
        label.setPadding(new Insets(5, 5, 5, 5));
        label.setFont(Font.font(20));
        label.setStyle(" -fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Verdana;");

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
            String jenis = tfJenis.getText();
            int jumlah = Integer.parseInt(tfJumlah.getText());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String tanggal = now.format(formatter);
            listPengeluaran.add(new Data(jenis, jumlah, tanggal));
            totalPengeluaran += jumlah;
            tfJenis.clear();
            tfJumlah.clear();
        });

        Button delete = new Button("Delete");
        delete.setOnAction(v -> {
            int index = tableData.getSelectionModel().getSelectedIndex();
            int jumlah = listPengeluaran.get(index).getJumlah();
            listPengeluaran.remove(index);
            totalPengeluaran -= jumlah;
        });
        
        HBox hBoxButton = new HBox(tambah, delete);
        hBoxButton.setSpacing(8);
        
        // membuat vBox
        VBox vBox = new VBox(label, hBoxInput, hBoxButton, tableData);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        // menambahkan Background
        Image bgImage = new Image("/images/background.jpg");
        ImageView bgIv = new ImageView(bgImage);

        bgIv.setOpacity(0.8);
        bgIv.setFitWidth(400);
        bgIv.setFitHeight(530);

        StackPane content = new StackPane(bgIv, vBox);
        
        // set button Image Icon
        Button buttonPengeluaran = new Button();
        Image icon = new Image("/images/iconKeluar.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);
        buttonPengeluaran.setGraphic(iconView);
        buttonPengeluaran.setContentDisplay(ContentDisplay.LEFT);
        buttonPengeluaran.setStyle(" -fx-background-color: #34495E;");

        // action button
        buttonPengeluaran.setOnAction(e -> {
            // Hapus konten saat ini di mainLayout
            mainLayout.setCenter(null);

            // Tambahkan tableData ke konten
            mainLayout.setCenter(content);
        });

        buttonPengeluaran.setOnMouseEntered(e -> {
            buttonPengeluaran.setText("PENGELUARAN");
            buttonPengeluaran.setStyle(" -fx-background-color: #3b536b; -fx-text-fill: red; -fx-font-weight: bold; -fx-font-family: Verdana;");
        }); 

        buttonPengeluaran.setOnMouseExited(e -> {
            buttonPengeluaran.setStyle(" -fx-background-color: #34495E;");
            buttonPengeluaran.setText("");
        });

        return buttonPengeluaran;
    }
}
