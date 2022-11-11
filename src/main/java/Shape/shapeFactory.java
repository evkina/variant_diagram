package Shape;

import com.example.variant_diagram.DragListener;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс фабрики
 */
public class ShapeFactory {
    private DragListener onDrag;

    //создает фигуру на основе класса Shapes в зависимости от переданного из ShapeList типа

    /** Метод создания фигуры
     * @param _type тип фигуры
     * @param _point2D координаты на схеме
     * @param _text текст
     * @param _onDrag listener
     * @return created state
     */
    public Shapes createShape(ShapeType _type, Point2D _point2D, String _text, DragListener _onDrag) {
        this.onDrag = _onDrag;
        Shapes _shapes = null;

        switch (_type) {
            case USECASE:
                _shapes = new UseCaseBlock(_point2D,_text,_onDrag);
                break;
            case ACTOR:
                _shapes = new ActorBlock(_point2D,_text,_onDrag);
                break;
            case TEXT:
                _shapes = new TextBlock(_point2D,_text,_onDrag);
                break;
            case SCENE:
                _shapes = new SceneBlock(_point2D,_text,_onDrag);
                break;
        }

        return _shapes;
    }
}
