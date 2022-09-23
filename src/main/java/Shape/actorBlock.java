package Shape;

import com.example.variant_diagram.DragListener;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * actorBlock Class
 */
public class actorBlock extends shapes {
    private static float _lineWidth = 2f;

    public actorBlock(Point2D _point, String _text, DragListener _onDrag) {
        super(shapeType.ACTOR,_point,_text,_onDrag);
    }

    /**
     * drawing on scene
     */
    public void draw()
    {
        _textField.applyCss();
        _textField.layout();

        getChildren().clear();

        var _textWidth = _textField.prefWidth(-1);
        var _textHeight = _textField.prefHeight(-1);

        Circle _head = new Circle(_textWidth * 0.5, 0, _textWidth * 0.1);

        Path _body = new Path();

        _body.getElements().add(new MoveTo(_textWidth * 0.25, _textHeight * 0.7));
        _body.getElements().add(new LineTo(_textWidth * 0.75, _textHeight  * 0.7));
        _body.getElements().add(new MoveTo(_textWidth * 0.5, _textHeight * 0.7));
        _body.getElements().add(new LineTo(_textWidth * 0.5, _textHeight * 2.5));
        _body.getElements().add(new LineTo(_textWidth * 0.3, _textHeight * 4));
        _body.getElements().add(new MoveTo(_textWidth * 0.5, _textHeight * 2.5));
        _body.getElements().add(new LineTo(_textWidth * 0.7, _textHeight * 4));

        Rectangle _background = new Rectangle(
                0,
                0,
                _textWidth,
                _textHeight * 4);

        _background.setFill(Color.TRANSPARENT);

        _point = new Point2D(getTranslateX(), getTranslateY());
        _width = _body.prefWidth(-1);
        _height = _body.prefHeight(-1);

        _head.setFill(Color.WHITE);
        _head.setStroke(Color.BLACK);
        _head.setStrokeWidth(_lineWidth);
        _body.setStrokeWidth(_lineWidth);
        _background.setCursor(Cursor.MOVE);

        getChildren().add(_body);
        getChildren().add(_head);
        getChildren().add(_background);
        getChildren().add(_textField);

        _textField.setTranslateY(_textHeight * 4.5);
    }
}
