package fr.groupeF.sae_sisfrance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class UploadPageController extends HBox {
    @FXML
    Button uploadButton;
    @FXML
    Button changingFXMLButton;
    @FXML
    Label fileReadableLabel;
    private String filePath;
    @FXML
    public void upload(){
        // Ouvrir une boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        fileChooser.setTitle("Sélectionner un fichier");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier csv", "*.csv"),
                new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        // Obtenir le fichier sélectionné
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            fileReadableLabel.setText("file uploaded");
            fileReadableLabel.setStyle("-fx-text-fill: green");
            filePath = selectedFile.getPath();
        }else{
            fileReadableLabel.setText("file not uploaded");
            fileReadableLabel.setStyle("-fx-text-fill: red");
        }
    };
    @FXML
    public void changingFXML(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DataPage.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 500);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFile() {
        return filePath;
    }
}
