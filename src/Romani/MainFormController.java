package Romani;

////////////////////////////////////////////////////////////////////////////////
// MainFormController.java
// ============
// Controller class for MainForm.fxml
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 7-Apr-2018
// UPDATED: 20-Apr-2018
////////////////////////////////////////////////////////////////////////////////
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MainFormController implements EventListener, Initializable {

    // constants
    private static final int PADDING = 10;      // pixels
    private static final int UNIT_COUNT = 10;   // # of units only on positive side

    // member vars
    private int width;                  // width of drawing area
    private int height;                 // height of drawing area
    private int centerX;                // center X in screen space
    private int centerY;                // center y in screen space
    private double mouseX;              // screen coordinate x
    private double mouseY;              // screen coordinate y
    private double coordRatio;          // map screen coord to logical coord, s/l
    private double coordX;              // logical coordinate x
    private double coordY;              // logical coordinate y
    private Line[] hLines;              // horizontal grid lines
    private Line[] vLines;              // vertical grid lines

    // JavaFX controls
    private Rectangle rectClip;         // clipping rectangle
    @FXML
    private Pane paneView;
    @FXML
    private Pane paneControl;
    @FXML
    private Label labelCoord;
    @FXML
    private Line line1a;
    @FXML
    private Line line1b;
    @FXML
    private Line line1c;
    @FXML
    private Line line2a;
    @FXML
    private Line line2b;
    @FXML
    private Line line2c;
    @FXML
    private Circle point1a;
    @FXML
    private Circle point1b;
    @FXML
    private Circle point2a;
    @FXML
    private Circle point2b;
    @FXML
    private Circle pointIntersect;
    @FXML
    private Slider sliderL1X1;
    @FXML
    private Slider sliderL1Y1;
    @FXML
    private Label labelL1X1;
    @FXML
    private Label labelL1Y1;
    @FXML
    private Slider sliderL1X2;
    @FXML
    private Slider sliderL1Y2;
    @FXML
    private Label labelL1X2;
    @FXML
    private Slider sliderL2X1;
    @FXML
    private Slider sliderL2Y1;
    @FXML
    private Slider sliderL2X2;
    @FXML
    private Slider sliderL2Y2;
    @FXML
    private Label labelL2X1;
    @FXML
    private Label labelL2Y1;
    @FXML
    private Label labelL2X2;
    @FXML
    private Label labelL1Y2;
    @FXML
    private Label labelL2Y2;
    @FXML
    private Label intersectionLabel;
    @FXML
    private Label labelLine1;
    @FXML
    private Label labelLine2;

    private MainModel model;

    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // init line arrays
        initGrid();

        // set clip region for drawing area
        rectClip = new Rectangle(500, 500);
        paneView.setClip(rectClip);

        // update width and height of drawing area
        ChangeListener resizeListener = (ov, oldV, newV) -> handleViewResized();
        paneView.widthProperty().addListener(resizeListener);
        paneView.heightProperty().addListener(resizeListener);

        //binding the label to the slider value for all sliders
        labelL1X1.textProperty().bind(sliderL1X1.valueProperty().asString("%.1f"));
        labelL1Y1.textProperty().bind(sliderL1Y1.valueProperty().asString("%.1f"));
        labelL1X2.textProperty().bind(sliderL1X2.valueProperty().asString("%.1f"));
        labelL1Y2.textProperty().bind(sliderL1Y2.valueProperty().asString("%.1f"));

        labelL2X1.textProperty().bind(sliderL2X1.valueProperty().asString("%.1f"));
        labelL2Y1.textProperty().bind(sliderL2Y1.valueProperty().asString("%.1f"));
        labelL2X2.textProperty().bind(sliderL2X2.valueProperty().asString("%.1f"));
        labelL2Y2.textProperty().bind(sliderL2Y2.valueProperty().asString("%.1f"));

        //initiate main model constructor
        model = new MainModel();

        //changeLister for sliders
        ChangeListener listener = (ov, oldValue, newValue) -> updateLines();

        //adding the changelistener to the sliders
        sliderL1X1.valueProperty().addListener(listener);
        sliderL1Y1.valueProperty().addListener(listener);
        sliderL1X2.valueProperty().addListener(listener);
        sliderL1Y2.valueProperty().addListener(listener);

        sliderL2X1.valueProperty().addListener(listener);
        sliderL2Y1.valueProperty().addListener(listener);
        sliderL2X2.valueProperty().addListener(listener);
        sliderL2Y2.valueProperty().addListener(listener);

        //get default colours for the points
        Color blue = (Color) point1a.getFill();
        Color red = (Color) point2a.getFill();

        //implement mouse drag, mouse entered and mouse exited properties
        ///
        //Point1a
        point1a.setOnMouseDragged((MouseEvent m) -> {
            sliderL1Y1.setValue(coordY);
            sliderL1X1.setValue(coordX);
        });
        point1a.setOnMouseEntered((MouseEvent m) -> {
            point1a.setFill(Color.ORANGE);
        });
        point1a.setOnMouseExited((MouseEvent m) -> {
            point1a.setFill(blue);
        });

        ///
        //Point1b
        point1b.setOnMouseDragged((MouseEvent m) -> {
            sliderL1Y2.setValue(coordY);
            sliderL1X2.setValue(coordX);
        });
        point1b.setOnMouseEntered((MouseEvent m) -> {
            point1b.setFill(Color.ORANGE);
        });
        point1b.setOnMouseExited((MouseEvent m) -> {
            point1b.setFill(blue);
        });

        ///
        //Point2a
        point2a.setOnMouseDragged((MouseEvent m) -> {
            sliderL2Y1.setValue(coordY);
            sliderL2X1.setValue(coordX);
        });
        point2a.setOnMouseEntered((MouseEvent m) -> {
            point2a.setFill(Color.ORANGE);
        });
        point2a.setOnMouseExited((MouseEvent m) -> {
            point2a.setFill(red);
        });
        

        ///
        //Point2b
        point2b.setOnMouseDragged((MouseEvent m) -> {
            sliderL2Y2.setValue(coordY);
            sliderL2X2.setValue(coordX);
        });
        point2b.setOnMouseEntered((MouseEvent m) -> {
            point2b.setFill(Color.ORANGE);
        });
        point2b.setOnMouseExited((MouseEvent m) -> {
            point2b.setFill(red);
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMouseMoved(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        coordX = (mouseX - centerX) / coordRatio;
        coordY = (height - mouseY - centerY) / coordRatio;
        labelCoord.setText(String.format("(%.1f, %.1f)", coordX, coordY));
    }

    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMouseDragged(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        coordX = (mouseX - centerX) / coordRatio;
        coordY = (height - mouseY - centerY) / coordRatio;
        labelCoord.setText(String.format("(%.1f, %.1f)", coordX, coordY));

    }

    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMousePressed(MouseEvent event) {
    }

    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMouseReleased(MouseEvent event) {
    }

    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleMouseExited(MouseEvent event) {
        labelCoord.setText("");
    }

    ///////////////////////////////////////////////////////////////////////////
    @FXML
    private void handleReset(ActionEvent event) {
        sliderL1X1.adjustValue(-5);
        sliderL1Y1.adjustValue(5);
        sliderL1X2.adjustValue(5);
        sliderL1Y2.adjustValue(-5);

        sliderL2X1.adjustValue(-5);
        sliderL2Y1.adjustValue(-5);
        sliderL2X2.adjustValue(5);
        sliderL2Y2.adjustValue(5);

        updateLines();

    }

    ///////////////////////////////////////////////////////////////////////////
    private void initGrid() {
        int lineCount = UNIT_COUNT * 2 + 1; // both side plus 1 at enter
        hLines = new Line[lineCount];
        vLines = new Line[lineCount];

        // create line objects
        for (int i = 0; i < lineCount; ++i) {
            hLines[i] = new Line();
            hLines[i].setStrokeWidth(0.2);
            hLines[i].setStroke(Color.GRAY);
            paneView.getChildren().add(hLines[i]);
            hLines[i].toBack();

            vLines[i] = new Line();
            vLines[i].setStrokeWidth(0.2);
            vLines[i].setStroke(Color.GRAY);
            paneView.getChildren().add(vLines[i]);
            vLines[i].toBack();
        }

        // for center line
        hLines[lineCount / 2].setStroke(Color.BLACK);
        hLines[lineCount / 2].setStrokeWidth(0.4);
        vLines[lineCount / 2].setStroke(Color.BLACK);
        vLines[lineCount / 2].setStrokeWidth(0.4);

        // layout grid lines
        updateGrid();
    }

    ///////////////////////////////////////////////////////////////////////////
    private void handleViewResized() {
        width = (int) paneView.getWidth();
        height = (int) paneView.getHeight();

        // compute the ratio of scrren to virtual = s / v
        double dim = Math.min(width, height) - (PADDING * 2);
        coordRatio = dim / (UNIT_COUNT * 2.0);

        centerX = (int) (width * 0.5 + 0.5);
        centerY = (int) (height * 0.5 + 0.5);
        //System.out.printf("center: (%d, %d)\n", centerX, centerY);

        // update clipping region
        rectClip.setWidth(width);
        rectClip.setHeight(height);

        updateGrid();
        updateLines();
    }

    ///////////////////////////////////////////////////////////////////////////
    private void updateGrid() {
        int dim;    // square dimension
        int xGap, yGap;

        if (width > height) {
            dim = height - (PADDING * 2);
            xGap = (int) ((width - dim) * 0.5 + 0.5);
            yGap = PADDING;
        } else {
            dim = width - (PADDING * 2);
            xGap = PADDING;
            yGap = (int) ((height - dim) * 0.5 + 0.5);
        }
        double step = dim / (UNIT_COUNT * 2.0);

        for (int i = 0; i < hLines.length; ++i) {
            hLines[i].setStartX(xGap);
            hLines[i].setStartY(yGap + (int) (step * i + 0.5));
            hLines[i].setEndX(width - xGap);
            hLines[i].setEndY(yGap + (int) (step * i + 0.5));

            vLines[i].setStartX(xGap + (int) (step * i + 0.5));
            vLines[i].setStartY(yGap);
            vLines[i].setEndX(xGap + (int) (step * i + 0.5));
            vLines[i].setEndY(height - yGap);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    private void updateLines() {

        //gets the slider information for Line1
        float l1x1 = (float) sliderL1X1.getValue();
        float l1y1 = (float) sliderL1Y1.getValue();
        float l1x2 = (float) sliderL1X2.getValue();
        float l1y2 = (float) sliderL1Y2.getValue();

        //sets the position of the 2 points
        point1a.setCenterX(l1x1 * coordRatio + centerX);
        point1a.setCenterY(-l1y1 * coordRatio + centerY);
        point1b.setCenterX(l1x2 * coordRatio + centerX);
        point1b.setCenterY(-l1y2 * coordRatio + centerY);

        //sets the position of the first line between the points.
        line1a.setStartX(point1a.getCenterX());
        line1a.setStartY(point1a.getCenterY());
        line1a.setEndX(point1b.getCenterX());
        line1a.setEndY(point1b.getCenterY());

        //create vector with the same slope as the line
        Vector2 v = new Vector2();
        v.x = l1x2 - l1x1;
        v.y = l1y2 - l1y1;

        //normalize vector
        v.normalize();

        //Creates another vector with the second point coordinates
        Vector2 p = new Vector2(l1x2, l1y2);

        //add a cloned normalized vector scaled by 50 to the p vector
        p.add(v.clone().scale(50));

        //draws extension line from the second point onward
        line1b.setStartX(point1b.getCenterX());
        line1b.setStartY(point1b.getCenterY());
        line1b.setEndX(p.x * coordRatio + centerX);
        line1b.setEndY(-p.y * coordRatio + centerY);

        //sets the point to the first point
        p.set(l1x1, l1y1);

        //add a cloned normalized vector scaled by 50 to the p vector
        p.add(v.clone().scale(-50));

        //draws extension line from the first point onward
        line1c.setStartX(point1a.getCenterX());
        line1c.setStartY(point1a.getCenterY());
        line1c.setEndX(p.x * coordRatio + centerX);
        line1c.setEndY(-p.y * coordRatio + centerY);

        ////////////////////////////////////////////////////////////////////////
        //gets the slider information for Line2
        float l2x1 = (float) sliderL2X1.getValue();
        float l2y1 = (float) sliderL2Y1.getValue();
        float l2x2 = (float) sliderL2X2.getValue();
        float l2y2 = (float) sliderL2Y2.getValue();

        //sets the position of the 2 points
        point2a.setCenterX(l2x1 * coordRatio + centerX);
        point2a.setCenterY(-l2y1 * coordRatio + centerY);
        point2b.setCenterX(l2x2 * coordRatio + centerX);
        point2b.setCenterY(-l2y2 * coordRatio + centerY);

        //sets the position of the second line between the points.
        line2a.setStartX(point2a.getCenterX());
        line2a.setStartY(point2a.getCenterY());
        line2a.setEndX(point2b.getCenterX());
        line2a.setEndY(point2b.getCenterY());

        //create vector with the same slope as the line
        Vector2 v2 = new Vector2();
        v.x = l2x2 - l2x1;
        v.y = l2y2 - l2y1;

        //normalize vector
        v2.normalize();

        //Creates another vector with the second point coordinates
        Vector2 p2 = new Vector2(l2x2, l2y2);

        //add a cloned normalized vector scaled by 50 to the p vector
        p2.add(v.clone().scale(50));

        //draws extension line from the second point onward
        line2b.setStartX(point2b.getCenterX());
        line2b.setStartY(point2b.getCenterY());
        line2b.setEndX(p2.x * coordRatio + centerX);
        line2b.setEndY(-p2.y * coordRatio + centerY);

        //sets the point to the first point
        p2.set(l2x1, l2y1);

        //add a cloned normalized vector scaled by 50 to the p vector
        p2.add(v.clone().scale(-50));

        //draws extension line from the first point onward
        line2c.setStartX(point2a.getCenterX());
        line2c.setStartY(point2a.getCenterY());
        line2c.setEndX(p2.x * coordRatio + centerX);
        line2c.setEndY(-p2.y * coordRatio + centerY);

        ///////////////////////////////////////////////////////////////////////
        //uses the model object to calculate the intersect point
        model.setLine1(l1x1, l1y1, l1x2, l1y2);
        model.setLine2(l2x1, l2y1, l2x2, l2y2);
        Vector2 intersection = model.getIntersectPoint();

        //sets the intersect point to the returned intersect point, converting to frame location
        pointIntersect.setCenterX((float) intersection.getX() * coordRatio + centerX);
        pointIntersect.setCenterY((float) -intersection.getY() * coordRatio + centerY);

        //updates the intersection label
        if (Float.isNaN(intersection.x)) {
            intersectionLabel.setText(String.format("(%s, %s)", "None", "None"));
        } else {
            intersectionLabel.setText(String.format("(%.3f, %.3f)", intersection.x, intersection.y));
        }

        updateEquations();
    }
//////////////////////////////////////////////////////////////////////////////////////////////
    //method is in charge of creating formula for lines and displaying

    private void updateEquations() {
        //Line 1 formula

        //gets the slider values
        float l1x1 = (float) sliderL1X1.getValue();
        float l1y1 = (float) sliderL1Y1.getValue();
        float l1x2 = (float) sliderL1X2.getValue();
        float l1y2 = (float) sliderL1Y2.getValue();

        //creates a new model that is used to calculate the Y-intercept of Line1
        MainModel modelLine1 = new MainModel();

        //sets Line1 as the current line, and line 2 as the Y axis at 0
        modelLine1.setLine1(l1x1, l1y1, l1x2, l1y2);
        modelLine1.setLine2(0, 0, 0, 1);

        //gets the b value, which is the Y-intercept value
        float b1 = modelLine1.getIntersectPoint().y;

        //formula for calculating slope of the line
        float slopeL1 = ((l1y2 - l1y1) / (l1x2 - l1x1));

        //selection statement to format if b is positive or negative and outputs as y=mx+b
        if (b1 >= 0) {
            labelLine1.setText(String.format("%s%.3f%s%.3f", "y = ", slopeL1, "x + ", b1));
        } else if (Float.isNaN(b1)) {
            labelLine1.setText("No equation");
        } else {
            labelLine1.setText(String.format("%s%.3f%s%.3f", "y = ", slopeL1, "x ", b1));
        }

        /////////////////////////////////////////////////////////////////////////////////////
        //Line 2 formula
        //gets the slider values
        float l2x1 = (float) sliderL2X1.getValue();
        float l2y1 = (float) sliderL2Y1.getValue();
        float l2x2 = (float) sliderL2X2.getValue();
        float l2y2 = (float) sliderL2Y2.getValue();

        //creates a new model that is used to calculate the Y-intercept of Line2
        MainModel modelLine2 = new MainModel();

        //sets Line2 as the current line, and line 2 as the Y axis at 0
        modelLine2.setLine1(l2x1, l2y1, l2x2, l2y2);
        modelLine2.setLine2(0, 0, 0, 1);

        //gets the b value, which is the Y-intercept value
        float b2 = modelLine2.getIntersectPoint().y;

        //formula for calculating slope of the line
        float slopeL2 = ((l2y2 - l2y1) / (l2x2 - l2x1));

        //selection statement to format if b is positive or negative and outputs as y=mx+b
        if (b2 >= 0) {
            labelLine2.setText(String.format("%s%.3f%s%.3f", "y = ", slopeL2, "x + ", b2));
        } else if (Float.isNaN(b2)) {
            labelLine2.setText("No equation");
        } else {
            labelLine2.setText(String.format("%s%.3f%s%.3f", "y = ", slopeL2, "x ", b2));
        }

    }
}
