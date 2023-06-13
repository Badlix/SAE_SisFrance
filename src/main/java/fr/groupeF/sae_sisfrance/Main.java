package fr.groupeF.sae_sisfrance;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //LOAD OF ALL FXML FILES TO GET LOADERS AND CONTROLLER

        /* Upload Page */
        FXMLLoader uploadPageLoader = new FXMLLoader(Main.class.getResource("UploadPage.fxml"));
        uploadPageLoader.load();
        UploadPageController uploadPageController = uploadPageLoader.getController();
        /* Data Page */
        FXMLLoader dataPageLoader = new FXMLLoader(Main.class.getResource("DataPage.fxml"));
        dataPageLoader.load();
        DataPageController dataPageController = dataPageLoader.getController();
        /* Gaphics Page */
        FXMLLoader graphicsPageLoader = new FXMLLoader(Main.class.getResource("GraphicsPage.fxml"));
        graphicsPageLoader.load();
        GraphicsPageController graphicsPageController = graphicsPageLoader.getController();

        // CREATION OF ALL SCENES
        Scene uploadPageScene = new Scene(uploadPageLoader.getRoot(), 1200, 740);
        Scene dataPageScene = new Scene(dataPageLoader.getRoot(), 1200, 740);
        Scene graphicsPageScene = new Scene(graphicsPageLoader.getRoot(), 1200, 740);

        // LINK SCENES AND CONTROLLERS
        dataPageController.setGraphicsPageScene(graphicsPageScene);
        graphicsPageController.setDataPageScene(dataPageScene);
        uploadPageController.setDataPageScene(dataPageScene);

        // LINK DATA BETWEEN SCENES
        dataPageController.setDataEarthquakes(uploadPageController.getDataEarthquakes());
        graphicsPageController.setDataEarthquakes(uploadPageController.getDataEarthquakes());

        stage.setTitle("Sis France : Data Analysis");
        stage.setScene(uploadPageScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

