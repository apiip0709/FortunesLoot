package fortunesloot.Scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Home {
    private Stage primaryStage;

    public Home(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        Button buttonMain = new Button("Jelajahi");
        buttonMain.setOnAction(v -> {
            MainPage mainPage = new MainPage(primaryStage);
            mainPage.show();
        });

        VBox vBox = new VBox(buttonMain);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 400, 650);
        
        primaryStage.setScene(scene);
    }
}
