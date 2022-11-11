package Shape;

import com.example.variant_diagram.DragListener;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс фигуры Вариант
 */
public class UseCaseBlock extends Shapes {

    public UseCaseBlock(Point2D _point, String _text, DragListener _onDrag) {
        super(ShapeType.USECASE, _point, _text, _onDrag);
    }

    /**
     * Метод для отрисовки фигуры
     */
    public void draw() {

        _textField.applyCss();
        _textField.layout();
        _textField.setText("Название");

        steps = new ArrayList<>();
        steps.add("");
        steps.add("");
        steps.add("");

        getChildren().clear();

        var _textWidth = _textField.prefWidth(-1);
        var _textHeight = _textField.prefHeight(-1);

        Rectangle _rect = new Rectangle(0, 0, _textWidth * 1.25, _textHeight * 4);

        _rect.setArcWidth(150.0);
        _rect.setArcHeight(150.0);
        float _lineWidth = 2f;
        _rect.setStrokeWidth(_lineWidth);
        _rect.setStroke(Color.BLACK);
        _rect.setFill(Color.WHITE);
        _rect.setCursor(Cursor.MOVE);

        _point = new Point2D(getTranslateX(), getTranslateY());
        _width = _rect.prefWidth(-1);
        _height = _rect.prefHeight(-1);

        getChildren().add(_rect);

        _textField.setTranslateX(_textWidth/2 * 0.25);
        _textField.setTranslateY(_textHeight*1.5);
        getChildren().add(_textField);
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(String stepText, int i) {
        steps.set(i, stepText);
    }
}
