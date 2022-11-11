package Shape;

import com.example.variant_diagram.DragListener;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import javafx.geometry.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для хранения списка созданных фигур
 */
public class ShapeList implements Serializable {
    transient private final ShapeFactory _shapeFactory;

    public List<Shapes> _shapes;
    transient DragListener _onDrag;

    //метод ShapeList вызывается при инициализации в HelloController и принимает метод updateArrows находящийся в HelloController
    /**
     * @param _onDrag переменная интерфейса DragListener
     */
    public ShapeList(DragListener _onDrag)
    {
        this._onDrag = _onDrag;
        this._shapeFactory = new ShapeFactory();
        _shapes = new ArrayList<>();
    }

    //вызывает shapeFactory в котором создается фигура в соответствии с переданным типом, а так же задает эвент нажатия мышкой по объекту. Вызывается из HelloController
    /** Создание фигур
     * @param _type тип фигуры
     * @param _point2D координаты создания на схеме
     * @param _text текст
     * @param _eraser ластик
     * @param _pane сцена
     * @return созданная фигура
     */
    public Shapes getShape(ShapeType _type, Point2D _point2D, String _text, ToggleButton _eraser, Pane _pane) {
        Shapes _shapes = _shapeFactory.createShape(_type,_point2D,_text, _onDrag);
        _shapes.setOnMouseClicked(mouseEvent -> {
            if(_eraser.isSelected()) {
                _pane.getChildren().remove(_shapes);
                this._shapes.remove(_shapes);
            }
        });
        this._shapes.add(_shapes);
        return _shapes;
    }
}
