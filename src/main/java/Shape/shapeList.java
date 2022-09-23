package Shape;

import com.example.variant_diagram.DragListener;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

import javafx.geometry.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * list of Shapes
 */
public class shapeList implements Serializable {
    transient private final shapeFactory _shapeFactory;

    public List<shapes> _shapes;
    transient DragListener _onDrag;
    public shapeList(DragListener _onDrag)
    {
        this._onDrag = _onDrag;
        this._shapeFactory = new shapeFactory();
        _shapes = new ArrayList<>();
    }

    /** Create Block
     * @param _type of block
     * @param _point2D coordinates on scheme
     * @param _text on block
     * @param _eraser position of eraser
     * @param _pane scene
     * @return created Block
     */
    public shapes getState(shapeType _type, Point2D _point2D, String _text, ToggleButton _eraser, Pane _pane) {
        shapes shapes = _shapeFactory.createState(_type,_point2D,_text, _onDrag);
        shapes.setOnMouseClicked(mouseEvent -> {
            if(_eraser.isSelected()) {
                _pane.getChildren().remove(shapes);
                this._shapes.remove(shapes);
            }
        });
        this._shapes.add(shapes);
        return shapes;
    }
}
