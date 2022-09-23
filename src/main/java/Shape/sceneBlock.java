package Shape;

import com.example.variant_diagram.DragListener;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class for sceneBlock
 */
public class sceneBlock extends shapes {

    private static float lineWidth = 3f;
    public sceneBlock(Point2D _point, String _text, DragListener _onDrag) {
        super(shapeType.SCENE,_point,_text,_onDrag);
    }

    /**
     * drawing on scene
     */
    @Override public void draw() {
        _textField.applyCss();
        _textField.layout();
        _textField.setPrefWidth(225);

        getChildren().clear();

        Rectangle _scene = new Rectangle(0, 0, 550, 550);
        _scene.setFill(Color.WHITE);
        _scene.setStroke(Color.BLACK);
        _scene.setStrokeWidth(lineWidth);

        Rectangle _nameArea = new Rectangle(300, 15, 245, 30);
        _nameArea.setFill(Color.WHITE);
        _nameArea.setStroke(Color.BLACK);
        _nameArea.setStrokeWidth(lineWidth);

        _point = new Point2D(getTranslateX(), getTranslateY());
        _width = _scene.prefWidth(-1);
        _height = _scene.prefHeight(-1);

        _scene.setTranslateX(150);
        _scene.setTranslateY(30);

        _scene.setDisable(true);
        _nameArea.setDisable(true);

        getChildren().add(_scene);
        getChildren().add(_nameArea);
        getChildren().add(_textField);

        _textField.setTranslateX(310);
        _textField.setTranslateY(19);
    }
}
