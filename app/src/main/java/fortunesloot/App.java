package fortunesloot;

import fortunesloot.Scenes.Home;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Home home = new Home(primaryStage);
        home.show();

        primaryStage.setTitle("Fortune's Loot");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
