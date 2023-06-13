package fr.groupeF.sae_sisfrance;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * CustomMapLayer is a class that extends MapLayer and represents a custom map layer.
 * It contains a list of MapPoints representing coordinates and a list of Rectangles representing shapes
 * that will appear on the map.
 */
public class CustomMapLayer extends MapLayer {
    private ObservableList<MapPoint> mapPoints; // list of MapPoint who contains coordinate
    private ObservableList<Rectangle> rectangles; // list of shapes who are going to appear on the map

    /**
     * Constructs a new CustomMapLayer object.
     * Initializes the mapPoints and rectangles lists as empty ObservableLists.
     */
    public CustomMapLayer() {
        this.mapPoints = FXCollections.observableArrayList();
        this.rectangles = FXCollections.observableArrayList();
    }

    /**
     * Adds a MapPoint to the mapPoints list with the specified intensity.
     * Creates a Rectangle shape based on the intensity and adds it to the rectangles list.
     * The color of the rectangle depends on the earthquake's intensity.
     *
     * @param mapPoint  The MapPoint to be added.
     * @param intensity The intensity of the earthquake associated with the MapPoint.
     */
    public void addMapPoint(MapPoint mapPoint, float intensity) {
        this.mapPoints.add(mapPoint);
        Rectangle pin = new Rectangle(10, 10, Color.TRANSPARENT);

        // The color of the rectangle depends on the eartquake's intensity
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

    /**
     * Clears the mapPoints and rectangles lists, removing all MapPoints and Rectangles.
     */
    public void clear() {
        this.mapPoints = FXCollections.observableArrayList();
        this.rectangles = FXCollections.observableArrayList();
    }

    /**
     * Updates the CustomMapLayer, when the user click on the zoom buttons which do not call layoutLayer by default.
     * Calls the layoutLayer method to reposition the shapes on the map.
     */
    public void update() {
        this.layoutLayer();
    }


    /**
     * This method is called every time the map is refreshed to lay out the shapes.
     * It converts each MapPoint to a Point2D and moves the corresponding shape
     * based on the MapPoint's coordinates.
     */
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
