package fortunesloot.Scenes;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import fortunesloot.models.DataUser;
import fortunesloot.utils.Datadb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MainPage extends DataUser {
    private Stage primaryStage;
    private BorderPane mainLayout;
    public int totalPenghasilan;
    public int totalPengeluaran;
    private ObservableList<DataUser> listPenghasilan;
    private ObservableList<DataUser> listPengeluaran;

    BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, new CornerRadii(5), null);
    Background background = new Background(backgroundFill);

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

        // Connect to DataBase
        Datadb.connection();
        Datadb.setupTable();

        // mengambil keseluruhan data dari isi database 
        List<DataUser> dataPenghasilan = Datadb.getAll("dataPenghasilan");
        List<DataUser> dataPengeluaran = Datadb.getAll("dataPengeluaran");

        listPenghasilan.addAll(dataPenghasilan);
        listPengeluaran.addAll(dataPengeluaran); 

        // Menghitung total yang ada dalam list 
        totalPenghasilan = hitungTotalPenghasilan(dataPenghasilan);
        totalPengeluaran = hitungTotalPengeluaran(dataPengeluaran);

        // Content
        Label contentLabel = new Label("Welcome to Financial App!");
        contentLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        contentLabel.setAlignment(Pos.TOP_CENTER);
        // contentLabel.setPadding(new Insets(50));

        String desk = "Deskripsi : Aplikasi ini bisa membantu dalam hal mengatur keuanganmu. Dengan aplikasi ini, kamu bisa dengan mudah memasukkan biaya pemasukan dan pengeluaranmu. Memudahkan para pengguna dalam mengatur keuangan mereka. ";

        Label apl = new Label(desk);
        apl.setWrapText(true);
        apl.setPadding(new Insets(0, 25, 0, 25));
        apl.setTextAlignment(TextAlignment.JUSTIFY);
        apl.setAlignment(Pos.CENTER);
        apl.setStyle("-fx-font-size: 11px; -fx-text-fill: black;");
        Image logoImage = new Image("/images/MainLogo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setPreserveRatio(true);
        logoImageView.setFitWidth(500);
        

        VBox content = new VBox(contentLabel, apl);
        content.setAlignment(Pos.CENTER);
        content.setSpacing(300);


        // Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setBottom(sidebar);
        mainLayout.setCenter(content);
        this.mainLayout = mainLayout;

        StackPane logo = new StackPane(logoImageView, mainLayout);


        // Scene
        Scene scene = new Scene(logo, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox header() {
        // membuat header
        Label titleLabel = new Label("Fortune's Loot");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        // layout header
        HBox header = new HBox(titleLabel);
        header.setStyle("-fx-background-color: #2C3E50;");
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER);

        return header;
    }

    private HBox sideBar() {
        // membuat sidebar
        Button exitButton = new Button("");
        Button buttonPenghasilan = buttonPenghasilan();
        Button buttonPengeluaran = buttonPengeluaran();
        Button buttonDompet = dompet();

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
            exitButton.setStyle(
                    " -fx-background-color: #3b536b; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Verdana;");
        });

        exitButton.setOnMouseExited(e -> {
            exitButton.setText("");
            exitButton.setStyle(" -fx-background-color: #34495E;");
        });

        // layout sidebar
        HBox sidebar = new HBox(exitButton, buttonPenghasilan, buttonPengeluaran, buttonDompet);
        sidebar.setStyle("-fx-background-color: #34495E;");
        sidebar.setSpacing(10);
        sidebar.setPadding(new Insets(5));
        sidebar.setAlignment(Pos.TOP_CENTER);

        return sidebar;
    }

    private Button buttonPenghasilan() {
        // Membuat Table View
        TableView<DataUser> tableData = new TableView<>();

        // Table Column
        TableColumn<DataUser, String> column1 = new TableColumn<>("Jenis");
        TableColumn<DataUser, Integer> column2 = new TableColumn<>("Jumlah");
        TableColumn<DataUser, String> column3 = new TableColumn<>("Tanggal");

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
        label.setStyle(
                " -fx-background-color: #00ff15e0; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Verdana;");

        // membuat textfield lalu disimpan di hBox
        TextField tfJenis = new TextField();
        tfJenis.setPromptText("Jenis Penghasilan");
        TextField tfJumlah = new TextField();
        tfJumlah.setPromptText("Berapa Banyak");
        HBox hBoxInput = new HBox(tfJenis, tfJumlah);
        hBoxInput.setSpacing(8);
<<<<<<< HEAD

        // membuat button lalu disimpan di hBox
=======
        
        // membuat button dan action button
>>>>>>> 2363516fb25be39fad1010806643589f392de631
        Button tambah = new Button("Tambah");
        tambah.setOnAction(v -> {
            String jenis = tfJenis.getText();
            int jumlah = Integer.parseInt(tfJumlah.getText());
            String tanggal = tanggalWaktuNow();
            listPenghasilan.add(new DataUser(jenis, jumlah, tanggal));
            totalPenghasilan += jumlah;
            tfJenis.clear();
            tfJumlah.clear();

            // Simpan data di database
            Datadb.saveData("dataPenghasilan", jenis, jumlah, tanggal);
        });

        Button delete = new Button("Delete");
        delete.setOnAction(v -> {
            int index = tableData.getSelectionModel().getSelectedIndex();
            DataUser selectedData = listPenghasilan.get(index);
            int jumlah = listPenghasilan.get(index).getJumlah();
            listPenghasilan.remove(index);
            totalPenghasilan -= jumlah;

            // Delete data dari database
            Datadb.deleteData("dataPenghasilan", selectedData);
        });

        // button disimpan dalam hBox
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

        // set button penghasilan Image Icon
        Button buttonPenghasilan = new Button();
        Image icon = new Image("/images/iconHasil.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);
        buttonPenghasilan.setGraphic(iconView);
        buttonPenghasilan.setContentDisplay(ContentDisplay.LEFT);
        buttonPenghasilan.setStyle(" -fx-background-color: #34495E;");

        // action button Penghasilan
        buttonPenghasilan.setOnAction(e -> {
            // Hapus konten saat ini di mainLayout
            mainLayout.setCenter(null);

            // Tambahkan content ke mainLayout
            mainLayout.setCenter(content);
        });

        buttonPenghasilan.setOnMouseEntered(e -> {
            buttonPenghasilan.setText("PENGHASILAN");
            buttonPenghasilan.setStyle(
                    " -fx-background-color: #3b536b; -fx-text-fill: #00ff15e0; -fx-font-weight: bold; -fx-font-family: Verdana;");
        });

        buttonPenghasilan.setOnMouseExited(e -> {
            buttonPenghasilan.setText("");
            buttonPenghasilan.setStyle(" -fx-background-color: #34495E;");
        });

        return buttonPenghasilan;
    }

    private Button buttonPengeluaran() {
        // Membuat Table View
        TableView<DataUser> tableData = new TableView<>();

        // Table Column
        TableColumn<DataUser, String> column1 = new TableColumn<>("Jenis");
        TableColumn<DataUser, Integer> column2 = new TableColumn<>("Jumlah");
        TableColumn<DataUser, LocalDate> column3 = new TableColumn<>("Tanggal");

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
        label.setStyle(
                " -fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Verdana;");

        // membuat textfield lalu disimpan di hBox
        TextField tfJenis = new TextField();
        tfJenis.setPromptText("Jenis Pengeluaran");
        TextField tfJumlah = new TextField();
        tfJumlah.setPromptText("Berapa Banyak");
        HBox hBoxInput = new HBox(tfJenis, tfJumlah);
        hBoxInput.setSpacing(8);
<<<<<<< HEAD

        // membuat button lalu disimpan di hBox
=======
        
        // membuat button dan action button
>>>>>>> 2363516fb25be39fad1010806643589f392de631
        Button tambah = new Button("Tambah");
        tambah.setOnAction(v -> {
            String jenis = tfJenis.getText();
            int jumlah = Integer.parseInt(tfJumlah.getText());
            String tanggal = tanggalWaktuNow();
            listPengeluaran.add(new DataUser(jenis, jumlah, tanggal));
            totalPengeluaran += jumlah;
            tfJenis.clear();
            tfJumlah.clear();

            // simpan data di database
            Datadb.saveData("dataPengeluaran", jenis, jumlah, tanggal);
        });

        Button delete = new Button("Delete");
        delete.setOnAction(v -> {
            int index = tableData.getSelectionModel().getSelectedIndex();
            DataUser selectedData = listPengeluaran.get(index);
            int jumlah = listPengeluaran.get(index).getJumlah();
            listPengeluaran.remove(index);
            totalPengeluaran -= jumlah;

            // Delete data dari database
            Datadb.deleteData("dataPengeluaran", selectedData);
        });
<<<<<<< HEAD

=======
        
        // button disimpan dalam hBox
>>>>>>> 2363516fb25be39fad1010806643589f392de631
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
<<<<<<< HEAD

        // set button Image Icon
=======
        
        // set button pengeluaran Image Icon
>>>>>>> 2363516fb25be39fad1010806643589f392de631
        Button buttonPengeluaran = new Button();
        Image icon = new Image("/images/iconKeluar.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);
        buttonPengeluaran.setGraphic(iconView);
        buttonPengeluaran.setContentDisplay(ContentDisplay.LEFT);
        buttonPengeluaran.setStyle(" -fx-background-color: #34495E;");

        // action button pengeluaran
        buttonPengeluaran.setOnAction(e -> {
            // Hapus konten saat ini di mainLayout
            mainLayout.setCenter(null);

            // Tambahkan conten ke mainLayout
            mainLayout.setCenter(content);
        });

        buttonPengeluaran.setOnMouseEntered(e -> {
            buttonPengeluaran.setText("PENGELUARAN");
            buttonPengeluaran.setStyle(
                    " -fx-background-color: #3b536b; -fx-text-fill: red; -fx-font-weight: bold; -fx-font-family: Verdana;");
        });

        buttonPengeluaran.setOnMouseExited(e -> {
            buttonPengeluaran.setStyle(" -fx-background-color: #34495E;");
            buttonPengeluaran.setText("");
        });

        return buttonPengeluaran;
    }

    private Button dompet() {
        // membuat button
        Button dompet = new Button();

        dompet.setOnAction(v -> {
            // Hapus konten saat ini di mainLayout
            mainLayout.setCenter(null);
<<<<<<< HEAD

            // Membuat objek NumberFormat dengan Locale Indonesia
=======
            
            // Membuat objek NumberFormat dengan Locale English
>>>>>>> 2363516fb25be39fad1010806643589f392de631
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);

            // Mengatur format ribuan
            format.setGroupingUsed(true);

            // Mengubah angka total penghasilan menjadi format ribuan
            String strPenghasilan = format.format(totalPenghasilan);
            String strPengeluaran = format.format(totalPengeluaran);
            String strSaldo = format.format(totalPenghasilan - totalPengeluaran);

            // Tampilkan informasi dompet
            Label labelPenghasilan = new Label("TOTAL PENGHASILAN\n\t  Rp " + strPenghasilan);
            Label labelPengeluaran = new Label("TOTAL PENGELUARAN\n\t -Rp " + strPengeluaran);
            Label labelSaldo = new Label("SALDO DOMPET\n     Rp " + strSaldo);

            labelPenghasilan.setStyle("-fx-font-weight: bold; -fx-font-family: Times New Roman;");
            labelPengeluaran.setStyle("-fx-font-weight: bold; -fx-font-family: Times New Roman;");
            labelSaldo.setStyle("-fx-font-weight: bold; -fx-font-family: Times New Roman;");

            // Membuat garis
            Line lineH = new Line(50, 50, 350, 50);
            lineH.setStroke(Color.BLACK);
            lineH.setStrokeWidth(2);
            lineH.setStrokeLineCap(StrokeLineCap.BUTT);
            lineH.setStrokeLineJoin(StrokeLineJoin.MITER);

            Line lineV = new Line(100, 180, 100, 250);
            lineV.setStroke(Color.BLACK);
            lineV.setStrokeWidth(2);
            lineV.setStrokeLineCap(StrokeLineCap.BUTT);
            lineV.setStrokeLineJoin(StrokeLineJoin.MITER);

            // layout dompet
            HBox hBox = new HBox(20, labelPenghasilan, lineV, labelPengeluaran);
            hBox.setAlignment(Pos.CENTER);

            VBox vBox = new VBox(hBox);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(60, 0, 0, 0));

            VBox vBox2 = new VBox(5, lineH, labelSaldo);
            vBox2.setAlignment(Pos.CENTER);

            VBox vBoxGabung = new VBox(vBox, vBox2);
            vBoxGabung.setAlignment(Pos.CENTER);

            // Menambahkan dompet card
            Image image = new Image("/images/dompetCard.png");
            ImageView bgCard = new ImageView(image);

            bgCard.setFitWidth(400);
            bgCard.setFitHeight(214);

            // menambahkan Background
            Image bgImage = new Image("/images/background.jpg");
            ImageView bgIv = new ImageView(bgImage);

            bgIv.setOpacity(0.5);
            bgIv.setFitWidth(400);
            bgIv.setFitHeight(530);

            StackPane content = new StackPane(bgIv, bgCard, vBoxGabung);
            content.setAlignment(Pos.CENTER);

            // Tambahkan content ke mainLayout
            mainLayout.setCenter(content);
        });

        // set button Image Icon
        Image icon = new Image("/images/iconDompet.png");
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);
        dompet.setGraphic(iconView);
        dompet.setContentDisplay(ContentDisplay.LEFT);
        dompet.setStyle(" -fx-background-color: #34495E;");

        // action efek button
        dompet.setOnMouseEntered(e -> {
            dompet.setText("DOMPET");
            dompet.setStyle(
                    " -fx-background-color: #3b536b; -fx-font-weight: bold; -fx-font-family: Verdana; -fx-text-fill: white;");
        });

        dompet.setOnMouseExited(e -> {
            dompet.setStyle(" -fx-background-color: #34495E;");
            dompet.setText("");
        });

        return dompet;
    }
<<<<<<< HEAD

    private int hitungTotalPenghasilan(List<DataUser> dataPenghasilan) {
        int totalPenghasilan = 0;
        for (DataUser data : dataPenghasilan) {
            totalPenghasilan += data.getJumlah();
        }
        return totalPenghasilan;
    }

    private int hitungTotalPengeluaran(List<DataUser> dataPengeluaran) {
        int totalPengeluaran = 0;
        for (DataUser data : dataPengeluaran) {
            totalPengeluaran += data.getJumlah();
        }
        return totalPengeluaran;
    }
=======
>>>>>>> 2363516fb25be39fad1010806643589f392de631
}
