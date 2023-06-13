package fr.groupeF.sae_sisfrance;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomMapLayer extends MapLayer {
    private ObservableList<MapPoint> mapPoints; // list of MapPoint who contains coordinate
    private ObservableList<Rectangle> rectangles; // list of shapes who are going to appear on the map

    public CustomMapLayer() {
        this.mapPoints = FXCollections.observableArrayList();
        this.rectangles = FXCollections.observableArrayList();
    }

    public void addMapPoint(MapPoint mapPoint, float intensity) {
        this.mapPoints.add(mapPoint);
        Rectangle pin = new Rectangle(10, 10, Color.TRANSPARENT);

        // The color of the rectangle depends on the earthquake's intensity
        if (intensity <= 2.5) {
            pin.setStroke(Color.LAVENDER);
        } else if (intensity <= 3.5) {
            pin.setStroke(Color.CYAN);
        } else if (intensity <= 4.5) {
            pin.setStroke(Color.LIMEGREEN);
        } else if (intensity <= 5.5) {
            pin.setStroke(Color.YELLOW);
        } else if (intensity <= 6.5) {
            pin.setStroke(Color.ORANGE);
        } else if (intensity <= 7.5) {
            pin.setStroke(Color.RED);
        } else if (intensity <= 8.5) {
            pin.setStroke(Color.DEEPPINK);
        } else {
            pin.setStroke(Color.PURPLE);
        }
        this.rectangles.add(pin);
        this.getChildren().setAll(rectangles);
    }

    public void clear() {
        this.mapPoints = FXCollections.observableArrayList();
        this.rectangles = FXCollections.observableArrayList();
    }

    public void update() {
        this.layoutLayer();
    }

    /* This function is called every time the map is refreshed */
    @Override
    protected void layoutLayer() {
        Point2D point2d;
        for (int i = 0; i < mapPoints.size(); i++) {
            /* Convert the MapPoint in a Point2D */
            point2d = getMapPoint(mapPoints.get(i).getLatitude(), mapPoints.get(i).getLongitude());

            /* Move the shape depending on the MapPoint's coordinates */
            rectangles.get(i).setTranslateX(point2d.getX());
            rectangles.get(i).setTranslateY(point2d.getY());
        }
    }
}
