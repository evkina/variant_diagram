package Shape;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.io.Serializable;
import java.util.Objects;

/**
 * Arrow class
 */
public class arrow extends Group implements Serializable {
    transient private final Line _line;
    public shapes _from;
    public shapes _to;
    public String _type;
    private static final double _arrowLength = 20;
    private static final double _arrowWidth = 7;

    public arrow(Line _line, Line _arrow1, Line _arrow2, shapes _from, shapes _to, String _type) {
        super(_line, _arrow1, _arrow2);
        this._from = _from;
        this._to = _to;
        this._line = _line;
        this._type = _type;


        if(Objects.equals(_type, "dotted"))
            _line.getStrokeDashArray().addAll(25d, 10d);

        InvalidationListener updater = o -> {
            double _endX = getEndX();
            double _endY = getEndY();
            double _startX = getStartX();
            double _startY = getStartY();

            _arrow1.setEndX(_endX);
            _arrow1.setEndY(_endY);
            _arrow2.setEndX(_endX);
            _arrow2.setEndY(_endY);

            if (_endX == _startX && _endY == _startY) {
                // arrow parts of length 0

                _arrow1.setStartX(_endX);
                _arrow1.setStartY(_endY);
                _arrow2.setStartX(_endX);
                _arrow2.setStartY(_endY);
            } else {
                double _hypot = Math.hypot(_startX - _endX, _startY - _endY);
                double _factor = _arrowLength / _hypot;
                double _factorO = _arrowWidth / _hypot;

                // part in direction of main line
                double _directoryX = (_startX - _endX) * _factor;
                double _directoryY = (_startY - _endY) * _factor;

                double ox = (_startX - _endX) * _factorO;
                double oy = (_startY - _endY) * _factorO;

                _arrow1.setStartX(_endX + _directoryX - oy);
                _arrow1.setStartY(_endY + _directoryY + ox);
                _arrow2.setStartX(_endX + _directoryX + oy);
                _arrow2.setStartY(_endY + _directoryY - ox);

            }
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    // start/end properties

    /** getting a start on X
     * @return StartX
     */
    public final double getStartX() {
        return _line.getStartX();
    }

    /** getting a startProperty on X
     * @return StartXProperty
     */
    public final DoubleProperty startXProperty() {
        return _line.startXProperty();
    }

    /** getting a start on Y
     * @return StartY
     */
    public final double getStartY() {
        return _line.getStartY();
    }

    /** getting a startProperty on X
     * @return StartYProperty
     */
    public final DoubleProperty startYProperty() {
        return _line.startYProperty();
    }

    /** getting end on X
     * @return EndX
     */
    public final double getEndX() {
        return _line.getEndX();
    }

    /** getting endProperty on X
     * @return EndXProperty
     */
    public final DoubleProperty endXProperty() {
        return _line.endXProperty();
    }

    /** Getting end on Y
     * @return end on Y
     */
    public final double getEndY() {
        return _line.getEndY();
    }

    /** Getting endYProperty
     * @return endProperty on Y
     */
    public final DoubleProperty endYProperty() {
        return _line.endYProperty();
    }

}