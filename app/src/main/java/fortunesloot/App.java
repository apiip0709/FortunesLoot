package fortunesloot;

import fortunesloot.Scenes.LoginPage;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginPage home = new LoginPage(primaryStage);
        home.show();

        primaryStage.setTitle("Fortune's Loot");
        Image logo = new Image("/images/logoapp.png");
        primaryStage.getIcons().add(logo);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
