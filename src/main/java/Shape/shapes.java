package Shape;

import com.example.variant_diagram.DragListener;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Родительский класс для всех создаваемых фигур
 */
public class Shapes extends Group implements Serializable {
    transient public ArrayList<DragListener> _dragListeners = new ArrayList<>();

    public TextField get_textField() {
        return _textField;
    }

    transient protected TextField _textField;
    public String _text;
    protected double _width;
    protected double _height;
    transient protected Point2D _point;
    public double _pointX;
    public double _pintY;
    public ShapeType _shapeType;
    private double _mouseAnchorX;
    private double _mouseAnchorY;
    transient protected ArrayList<String> steps;

    //основной класс для создания фигур различных типов, от него они все наследуются. Вызывается в shapeFactory
    public Shapes(ShapeType _shapeType, Point2D _point, String _text, DragListener onDrag)
    {
        this._text = _text;
        _pointX = _point.getX();
        _pintY = _point.getY();
        this._shapeType = _shapeType;
        setTranslateX(_point.getX());
        setTranslateY(_point.getY());

        steps = new ArrayList<>();

        _textField = new TextField(_text);
        _textField.setOnKeyTyped(e -> this._text = _textField.getText());
        _textField.setOnKeyReleased(e -> this._text = _textField.getText());
        _textField.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        _textField.setFont(Font.font("Verdana", 8));
        _textField.setAlignment(Pos.BASELINE_CENTER);

        this._point = _point;
        _pointX = this._point.getX();
        _pintY = this._point.getY();
        this._width = computePrefWidth(-1);
        this._height = computePrefHeight(-1);

        getChildren().add(_textField);

        float finalDiv = 0;
        setOnMousePressed(mouseEvent -> {
            _mouseAnchorX = mouseEvent.getX();
            _mouseAnchorY = mouseEvent.getY();
        });
        _dragListeners.add(onDrag);

        //добавление обработчика перетаскивания мышкой
        setOnMouseDragged(mouseEvent -> {
            setTranslateX(Math.max(getTranslateX() + mouseEvent.getX() - _mouseAnchorX, 0));
            setTranslateY(Math.max(getTranslateY() + mouseEvent.getY() - _mouseAnchorY, 0));

            this._point = new Point2D(getTranslateX()-finalDiv, getTranslateY()-finalDiv);
            _pointX =this._point.getX();
            _pintY =this._point.getY();

            //к каждому созданному объекту помимо стандартного перемещения мышкой прикрепляется метод обновления стрелок updateArrow из HelloControllera для того что бы при перемещении объекта изменялось положение стрелок в след за ним
            for (int i = 0; i < _dragListeners.size(); i++) {
                _dragListeners.get(i).onDrag();
            }

        });
    }

    /**
     * Метод для отрисовки фигур на сцене
     */
    public void draw() {

    }

    public ArrayList<String> getSteps() { return null; }

    public void setSteps(String stepText, int i) {}

    public double get_height() {
        return _height;
    }

    public double get_width() {
        return _width;
    }

    public Point2D getPosition() {
        return _point;
    }
    public double minY() {
        return getPosition().getY();
    }

    public double minX() {
        return getPosition().getX();
    }

    public double maxY() {
        return getPosition().getY() + get_height();
    }

    public double maxX() {
        return getPosition().getX() + get_width();
    }

    /**getting list of point for connecting
     * @return list of points
     */
    public ArrayList<Point2D> getArrayOfMinMaxPoints() {
        ArrayList<Point2D> _fromPoints = new ArrayList<>();
        _fromPoints.add(new Point2D(maxX(), maxY() - get_height() * 0.5));
        _fromPoints.add(new Point2D(maxX() - get_width() * 0.5, minY()));
        _fromPoints.add(new Point2D(maxX() - get_width() * 0.5, maxY()));
        _fromPoints.add(new Point2D(minX(), maxY() - get_height() * 0.5));

        return _fromPoints;
    }

    /** comparison of states
     * @param _shapes for comparison
     * @return res of operation
     */
    public boolean Equals(Shapes _shapes)
    {
        return this._text.equals(_shapes._text) && this._pointX == _shapes._pointX && this._pintY == _shapes._pintY && this._shapeType == _shapes._shapeType;
    }

}

