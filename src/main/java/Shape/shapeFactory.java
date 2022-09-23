package Shape;

import com.example.variant_diagram.DragListener;
import javafx.geometry.Point2D;

/**
 * FactoryClass
 */
public class shapeFactory {
    private DragListener onDrag;

    /**Method for creating a new Shape
     * @param _type of state
     * @param _point2D coordinates on scheme
     * @param _text on block
     * @param _onDrag listener
     * @return created state
     */
    public shapes createState(shapeType _type, Point2D _point2D, String _text, DragListener _onDrag) {
        this.onDrag = _onDrag;
        shapes _shapes = null;

        switch (_type) {
            case USECASE:
                _shapes = new useCaseBlock(_point2D,_text,_onDrag);
                break;
            case ACTOR:
                _shapes = new actorBlock(_point2D,_text,_onDrag);
                break;
            case TEXT:
                _shapes = new textBlock(_point2D,_text,_onDrag);
                break;
            case SCENE:
                _shapes = new sceneBlock(_point2D,_text,_onDrag);
                break;
        }

        return _shapes;
    }
}
