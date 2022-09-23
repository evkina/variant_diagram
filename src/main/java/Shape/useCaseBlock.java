package Shape;

import com.example.variant_diagram.DragListener;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class forUseCaseBlock
 */
public class useCaseBlock extends shapes {
    private static float _lineWidth = 2f;
    public useCaseBlock(Point2D _point, String _text, DragListener _onDrag) {
        super(shapeType.USECASE,_point,_text,_onDrag);
    }

    /**
     * comparison
     */
    public void draw() {
        _textField.applyCss();
        _textField.layout();

        getChildren().clear();

        var _textWidth = _textField.prefWidth(-1);
        var _textHeight = _textField.prefHeight(-1);

        Rectangle _rect = new Rectangle(
                0,
                0,
                _textWidth * 1,
                _textHeight * 3);

        _rect.setArcWidth(150.0);
        _rect.setArcHeight(150.0);
        _rect.setStrokeWidth(_lineWidth);
        _rect.setStroke(Color.BLACK);
        _rect.setFill(Color.WHITE);
        _rect.setCursor(Cursor.MOVE);

        _point = new Point2D(getTranslateX(), getTranslateY());
        _width = _rect.prefWidth(-1);
        _height = _rect.prefHeight(-1);

        getChildren().add(_rect);
        getChildren().add(_textField);
        _textField.setTranslateY(_textHeight * 1);
    }
}
