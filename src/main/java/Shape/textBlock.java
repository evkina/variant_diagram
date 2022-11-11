package Shape;

import com.example.variant_diagram.DragListener;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Класс фигуры Текст
 */
public class TextBlock extends Shapes {
    private static float _offset = 10f;
    private static float _lineWidth = 2f;
    public TextBlock(Point2D _point, String _text, DragListener _onDrag) {
        super(ShapeType.TEXT,_point,_text,_onDrag);
    }

    /**
     * Метод для отрисовки фигуры
     */
    public void draw()
    {
        _textField.applyCss();
        _textField.layout();

        getChildren().clear();

        var _textWidth = _textField.prefWidth(-1);
        var _textHeight = _textField.prefHeight(-1);

        Rectangle _clip = new Rectangle(
                _lineWidth,
                _lineWidth,
                _textWidth*1.3 + _offset - _lineWidth * 2,
                _textHeight*2 + _offset - _lineWidth * 2);

        _point = new Point2D(getTranslateX(), getTranslateY());
        _width = _clip.prefWidth(-1);
        _height = _clip.prefHeight(-1);


        _clip.setFill(Color.TRANSPARENT);
        _clip.setCursor(Cursor.MOVE);

        getChildren().add(_clip);
        getChildren().add(_textField);

        _textField.setTranslateX(_offset * 0.5f);
        _textField.setTranslateY(_offset * 0.5f);
    }
}
