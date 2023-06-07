package fr.groupeF.sae_sisfrance;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UploadPage.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("DataPage.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}