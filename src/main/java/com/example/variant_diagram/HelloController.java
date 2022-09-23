package com.example.variant_diagram;

import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import Shape.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Controller for hello-view.fxml
 */
public class HelloController implements Initializable {
    public Pane _pane;
    public ToggleButton _eraserButton;
    public ToggleButton _dottedLineButton = new ToggleButton();
    public ToggleButton _associationLineButton;
    shapes _from;
    shapes _to;
    shapes _shapes;
    shapeList _shapeList;
    arrowList _arrowList;

    /**
     * @param url on src
     * @param resourceBundle for initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _shapeList = new shapeList(this::drawArrows);
        _arrowList = new arrowList();
        _shapes = _shapeList.getState(shapeType.SCENE,new Point2D(0,0),"", _eraserButton, _pane);
        drawShape(_shapes);
    }

    /**
     * @param _shapes for drawing
     */
    private void drawShape(shapes _shapes) {
        _pane.getChildren().add(_shapes);
        _shapes.setOnMouseClicked(mouseEvent -> {
            if(_eraserButton.isSelected()) {
                _pane.getChildren().remove(_shapes);
                _shapeList._shapes.remove(_shapes);
            }
            if(_dottedLineButton.isSelected() || _associationLineButton.isSelected()){
                if(_from == null) {
                    _from = _shapes;
                    return;
                }
                if(_to == null){
                    _to = _shapes;
                    if(_dottedLineButton.isSelected())
                        drawArrow(_from, _to, "dotted");
                    if(_associationLineButton.isSelected())
                        drawArrow(_from, _to, "");
                    _from = null;
                    _to = null;
                    _dottedLineButton.setSelected(false);
                    _associationLineButton.setSelected(false);
                }
            }
        });
        _shapes.draw();
    }

    /**
     * ClassBlock create
     */
    public void BlockClick()
    {
        _shapes = _shapeList.getState(shapeType.ACTOR,new Point2D(30,30),"", _eraserButton, _pane);
        drawShape(_shapes);
    }

    /**
     * TextBlock create
     */
    public void BlockClickText()
    {
        _shapes = _shapeList.getState(shapeType.TEXT,new Point2D(30,30),"", _eraserButton, _pane);
        drawShape(_shapes);
    }

    /**
     * StateBlock create
     */
    public void BlockClickState()
    {
        _shapes = _shapeList.getState(shapeType.USECASE,new Point2D(30,30),"", _eraserButton, _pane);
        drawShape(_shapes);
    }

    /**
     * method for updating arrows
     */
    private void drawArrows() {
        _pane.autosize();

        List<paths> _temp = new ArrayList();
        for (Shape.arrow _arrow : _arrowList._arrowList) {
            _temp.add(new paths(_arrow._from, _arrow._to, _arrow._type));
            _pane.getChildren().remove(_arrow);
        }
        _arrowList._arrowList.clear();

        for (paths _paths : _temp) {
            drawArrow(_paths._arr[0], _paths._arr[1], _paths.get_type());
        }
    }

    /** create arrow
     * @param _from ParentBlock
     * @param _to ChildrenBlock
     */
    private void drawArrow(shapes _from, shapes _to, String _type) {
        var _points = getArrowPoints(_from, _to);
        Line _line = new Line(_points._item1.getX(), _points._item1.getY(), _points._item2.getX(), _points._item2.getY());

        Line _arrow1 = new Line(0, 0, 2, 2);
        Line _arrow2 = new Line(0, 0, 2, 2);

        arrow _arrow = new arrow(_line, _arrow1, _arrow2, _from, _to, _type);
        _pane.getChildren().add(_arrow);
        _arrow.setOnMouseClicked(mouseEvent -> {
            if (_eraserButton.isSelected()) {
                _pane.getChildren().remove(_arrow);
                _arrowList._arrowList.remove(_arrow);
            }
        });
        _arrowList._arrowList.add(_arrow);
    }

    /** method for calculation an optimal point
     * @param _from ParentBlock
     * @param _to ChildrenBlock
     * @return bundle of points
     */
    private pointLiner<Point2D, Point2D> getArrowPoints(shapes _from, shapes _to) {
        var _fromPoints = _from.getArrayOfMinMaxPoints();
        var _toPoints = _to.getArrayOfMinMaxPoints();

        Point2D _pointFromFinal = Point2D.ZERO;
        Point2D _pointToFinal = Point2D.ZERO;
        double _lowestDistance = Double.POSITIVE_INFINITY;

        for (Point2D _fromPoint : _fromPoints) {
            for (Point2D toPoint : _toPoints) {
                var newDistance = _fromPoint.distance(toPoint);
                if (newDistance < _lowestDistance) {
                    _pointFromFinal = _fromPoint;
                    _pointToFinal = toPoint;
                    _lowestDistance = newDistance;
                }
            }
        }

        return new pointLiner<>(_pointFromFinal, _pointToFinal);
    }

    /**
     * Button for opening a file
     */
    public void ClickOpenFile() {
        FileChooser _fileChooser = new FileChooser();
        _fileChooser.setTitle("Выбрать файл проекта");
        FileChooser.ExtensionFilter _filter = new FileChooser.ExtensionFilter("Схема", "*.ActDiagram");
        _fileChooser.getExtensionFilters().add(_filter);
        File _file = _fileChooser.showOpenDialog(_pane.getScene().getWindow());
        if (_file!=null)
            loadFile(_file);

    }

    /** Method for opening file
     * @param _file opened file
     */
    public void loadFile(File _file) {
        try {
            shapeList _tempstates;
            arrowList _temparrows;
            FileInputStream _fis = new FileInputStream(_file);
            ObjectInputStream _inputStream = new ObjectInputStream(_fis);
            _tempstates = (shapeList) _inputStream.readObject();
            _temparrows = (arrowList) _inputStream.readObject();
            _inputStream.close();
            while(!_shapeList._shapes.isEmpty()) {
                _pane.getChildren().remove(_shapeList._shapes.get(0));
                _shapeList._shapes.remove(0);
            }
            while(!_arrowList._arrowList.isEmpty()) {
                _pane.getChildren().remove(_arrowList._arrowList.get(0));
                _arrowList._arrowList.remove(0);
            }
            if(_tempstates!=null) {
                for (shapes st : _tempstates._shapes) {
                    shapes temp = _shapeList.getState(st._shapeType, new Point2D(st._pointX, st._pintY), st._text, _eraserButton, _pane);
                    drawShape(temp);
                }
            }
            if(_temparrows!=null) {
                for (Shape.arrow _arrow : _temparrows._arrowList) {
                    for (shapes _search : _shapeList._shapes) {
                        if (_arrow._from.Equals(_search))
                            _arrow._from = _search;
                        if (_arrow._to.Equals(_search))
                            _arrow._to = _search;
                    }
                    drawArrow(_arrow._from, _arrow._to, _arrow._type);
                }
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    /**
     * Button for save file
     */
    public void ClickSaveFile() {
        fileWork.saveFile(_pane, _shapeList, _arrowList);
    }

    /**
     * Export into image
     */
    public void ClickExpFile() {
        fileWork.exportFile(_pane);
    }

    /**
     * Deselect everything except association
     */
    public void associoationClick() {
        _eraserButton.setSelected(false);
        _dottedLineButton.setSelected(false);
    }

    /**
     * Deselect everything except dotted line
     */
    public void dottedClick() {
        _eraserButton.setSelected(false);
        _associationLineButton.setSelected(false);
    }

    /**
     * Deselect everything except eraser
     */
    public void eraserClick() {
        _associationLineButton.setSelected(false);
        _dottedLineButton.setSelected(false);
    }
}