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
        System.setProperty("http.agent", "Gluon Mobile/1.0.3");
        FXMLLoader uploadPageLoader = new FXMLLoader(Main.class.getResource("UploadPage.fxml"));
        uploadPageLoader.load();
        UploadPageController uploadPageController = uploadPageLoader.getController();

        FXMLLoader dataPageLoader = new FXMLLoader(Main.class.getResource("DataPage.fxml"));
        dataPageLoader.load();
        DataPageController dataPageController = dataPageLoader.getController();

        FXMLLoader graphicsPageLoader = new FXMLLoader(Main.class.getResource("GraphicsPage.fxml"));
        graphicsPageLoader.load();
        GraphicsPageController graphicsPageController = graphicsPageLoader.getController();

        uploadPageController.setDataPageLoad(dataPageLoader);
        uploadPageController.setGraphicsPageLoad(graphicsPageLoader);
        dataPageController.setUploadPageLoad(uploadPageLoader);
        dataPageController.setGraphicsPageLoad(graphicsPageLoader);
        graphicsPageController.setUploadPageLoad(uploadPageLoader);
        graphicsPageController.setDataPageLoad(dataPageLoader);

        dataPageController.getEarthquakes().setItems(uploadPageController.getEarthquakes());
        graphicsPageController.getEarthquakes().setItems(uploadPageController.getEarthquakes());
        Bindings.bindContentBidirectional(dataPageController.getFilteredEarthquakes(), uploadPageController.getFilteredEarthquakes());
        Bindings.bindContentBidirectional(graphicsPageController.getFilteredEarthquakes(), uploadPageController.getFilteredEarthquakes());
        Bindings.bindContentBidirectional(dataPageController.getFiltersList(), uploadPageController.getFiltersList());
        Bindings.bindContentBidirectional(graphicsPageController.getFiltersList(), uploadPageController.getFiltersList());

        Scene scene = new Scene(uploadPageLoader.getRoot(), 1000, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


