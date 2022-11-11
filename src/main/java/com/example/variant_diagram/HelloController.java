package com.example.variant_diagram;

import IteratorDirectory.DiagramCollection;
import IteratorDirectory.Iterator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import Shape.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Контроллер для hello-view.fxml
 */
public class HelloController implements Initializable {
    public Pane _pane;
    public ToggleButton _eraserButton;
    public ToggleButton _dottedLineButton = new ToggleButton();
    public ToggleButton _associationLineButton;
    public TextField stepOneTextField;
    public TextField stepTwoTextField;
    public TextField stepThreeTextField;
    public VBox stepPane;

    Shapes _from;
    Shapes _to;
    Shapes _sceneShape;
    Shapes _shape;
    Shapes selectedShape;
    ShapeList _shapeList;
    ArrowList _arrowList;

    public List<File> list = new ArrayList<>();
    public DiagramCollection diagr = new DiagramCollection(list);
    public Iterator iter_main = diagr.getIterator();
    private List<String> steps = new ArrayList<>();

    /**
     * @param url url
     * @param resourceBundle пакет ресурсов для инициализации
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //инициализируем переменную класса ShapeList и передаем в него метод updateArrows
        _shapeList = new ShapeList(this::updateArrows);
        _arrowList = new ArrowList();
        //создаем фигуру сцены
        _sceneShape = _shapeList.getShape(ShapeType.SCENE,new Point2D(0,0),"", _eraserButton, _pane);
        drawShape(_sceneShape);

        stepPane.setVisible(false);

        stepOneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            selectedShape.setSteps(newValue, 0);
            System.out.println(selectedShape.getSteps());
        });
        stepTwoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            selectedShape.setSteps(newValue, 1);
            System.out.println(selectedShape.getSteps());
        });
        stepThreeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            selectedShape.setSteps(newValue, 2);
            System.out.println(selectedShape.getSteps());
        });
    }

    /**
     * @param _shapes создаваемая фигура
     */
    private void drawShape(Shapes _shapes) {
        _pane.getChildren().add(_shapes);

        //добавление обработчика нажатия на фигуру и проверка выбранного инструмента при нажатии на нее
        _shapes.setOnMouseClicked(mouseEvent -> {
            if(_eraserButton.isSelected()) {
                //выбран ластик - удаляет фигуру
                _pane.getChildren().remove(_shapes);
                _shapeList._shapes.remove(_shapes);
            }
            if(_dottedLineButton.isSelected() || _associationLineButton.isSelected()){
                //выбран один из видов стрелок
                if(_from == null) {
                    //если не существует объекта от которого будет идти стрелка, то задаем его
                    _from = _shapes;
                    return;
                }
                if(_to == null){
                    //если не существует объекта к которому будет идти стрелка, то задаем его
                    _to = _shapes;

                    //в зависимости от выбранной стрелки передаем в метод drawArrow разные данные для отрисовки
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

            if(_shapes._shapeType == ShapeType.USECASE){

                selectedShape = _shapes;

                stepPane.setVisible(true);

                stepOneTextField.setText(selectedShape.getSteps().get(0));
                stepTwoTextField.setText(selectedShape.getSteps().get(1));
                stepThreeTextField.setText(selectedShape.getSteps().get(2));

            } else {
                stepPane.setVisible(false);
            }
        });
        //рисуем фигуру
        _shapes.draw();
    }

    /**
     * нажатие на кнопку создание фигуры Участник
     */
    public void actorClick()
    {
        stepPane.setVisible(false);
        _shape = _shapeList.getShape(ShapeType.ACTOR,new Point2D(30,30),"", _eraserButton, _pane);
        drawShape(_shape);
    }

    /**
     * Нажатие на кнопку создания фигуры Текст
     */
    public void textClick()
    {
        stepPane.setVisible(false);
        _shape = _shapeList.getShape(ShapeType.TEXT,new Point2D(30,30),"", _eraserButton, _pane);
        drawShape(_shape);
    }

    /**
     * Нажатие на кнопку создания фигуры Вариант
     */
    public void useCaseClick()
    {
        stepPane.setVisible(false);
        _shape = _shapeList.getShape(ShapeType.USECASE, new Point2D(30,30), "", _eraserButton, _pane);
        drawShape(_shape);
    }

    /**
     * Метод обновления стрелок при перемещении объектов
     */
    private void updateArrows() {
        _pane.autosize();

        List<Paths> _temp = new ArrayList<>();

        for (Arrow _arrow : _arrowList._arrowList) {
            _temp.add(new Paths(_arrow._from, _arrow._to, _arrow._type));
            _pane.getChildren().remove(_arrow);
        }
        _arrowList._arrowList.clear();

        for (Paths _paths : _temp) {
            drawArrow(_paths._arr[0], _paths._arr[1], _paths.get_type());
        }
    }

    /** Метод создания стрелок
     * @param _from фигура от которой будет идти стрелка
     * @param _to фигура к которой будет идти стрелка
     */
    private void drawArrow(Shapes _from, Shapes _to, String _type) {
        var _points = getArrowPoints(_from, _to);
        Line _line = new Line(_points._item1.getX(), _points._item1.getY(), _points._item2.getX(), _points._item2.getY());

        Line _arrow1 = new Line(0, 0, 2, 2);
        Line _arrow2 = new Line(0, 0, 2, 2);

        //вызывем класс Arrow где произойдет создание стрелки
        Arrow _arrow = new Arrow(_line, _arrow1, _arrow2, _from, _to, _type);
        _pane.getChildren().add(_arrow);

        //добавление обработчика нажатия на стрелку
        _arrow.setOnMouseClicked(mouseEvent -> {
            if (_eraserButton.isSelected()) {
                _pane.getChildren().remove(_arrow);
                _arrowList._arrowList.remove(_arrow);
            }
        });
        _arrowList._arrowList.add(_arrow);
    }

    /** Метод для расчета оптимальной точки крепления стрелки
     * @param _from фигура от которой будет идти стрелка
     * @param _to фигура к которой будет идти стрелка
     * @return пакет точек крепления
     */
    private PointLiner<Point2D, Point2D> getArrowPoints(Shapes _from, Shapes _to) {
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

        return new PointLiner<>(_pointFromFinal, _pointToFinal);
    }

    /**
     * Обработчик нажатия на кнопку Импорт
     */
    public void ClickOpenFile() {
        FileChooser _fileChooser = new FileChooser();
        _fileChooser.setTitle("Выбрать файл проекта");
        FileChooser.ExtensionFilter _filter = new FileChooser.ExtensionFilter("Схема", "*.ActDiagram");
        _fileChooser.getExtensionFilters().add(_filter);
        File file = _fileChooser.showOpenDialog(_pane.getScene().getWindow());

        list.clear();
        list.add(file);
        //передача списка диаграмм в класс DiagramCollection
        diagr = new DiagramCollection(list);
        try{
            //вызов метода вывода диаграмм на экран
            loadFile(iter_main.next());
        } catch (Exception ignored){}

    }

    /**
     * Обработчик нажатия на кнопку Открыть директорию
     */
    public void ClickOpenDir() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Выбор папки");
        File defaultDirectory = new File("c:/");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(_pane.getScene().getWindow());

        //очистка списка файлов диаграмм и заполнение его заново из новой указанной папки
        list.clear();
        for(File file: Objects.requireNonNull(selectedDirectory.listFiles())){
            if(file.isFile() && file.getAbsolutePath().endsWith(".ActDiagram")){
                list.add(file);
            }
        }

        //передача списка диаграмм в класс DiagramCollection
        diagr = new DiagramCollection(list);
        try{
            //вызов метода вывода диаграмм на экран
            loadFile(iter_main.next());
        } catch (Exception ignored){}
    }

    /** Метод открытия файла
     * @param _file opened file
     */
    public void loadFile(File _file) {
        try {
            ShapeList _tempstates;
            ArrowList _temparrows;
            FileInputStream _fis = new FileInputStream(_file);
            ObjectInputStream _inputStream = new ObjectInputStream(_fis);
            _tempstates = (ShapeList) _inputStream.readObject();
            _temparrows = (ArrowList) _inputStream.readObject();
            _inputStream.close();
            //чтение фигур из файла
            while(!_shapeList._shapes.isEmpty()) {
                _pane.getChildren().remove(_shapeList._shapes.get(0));
                _shapeList._shapes.remove(0);
            }
            //чтение стрелок из файла
            while(!_arrowList._arrowList.isEmpty()) {
                _pane.getChildren().remove(_arrowList._arrowList.get(0));
                _arrowList._arrowList.remove(0);
            }
            //добавление считанных фигур из файла на сцену
            if(_tempstates!=null) {
                for (Shapes st : _tempstates._shapes) {
                    Shapes temp = _shapeList.getShape(st._shapeType, new Point2D(st._pointX, st._pintY), st._text, _eraserButton, _pane);
                    drawShape(temp);
                }
            }
            //добавление считанных стрелок из файла на сцену
            if(_temparrows!=null) {
                for (Arrow _arrow : _temparrows._arrowList) {
                    for (Shapes _search : _shapeList._shapes) {
                        if (_arrow._from.Equals(_search))
                            _arrow._from = _search;
                        if (_arrow._to.Equals(_search))
                            _arrow._to = _search;
                    }
                    drawArrow(_arrow._from, _arrow._to, _arrow._type);
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    /**
     * Предыдущая диаграма
     */
    public void previousButton_Click() {
        //если список диаграм не закончился вызываем метод вывода диаграммы на экран
        stepPane.setVisible(false);
        if(iter_main.hasNext()){
            loadFile(iter_main.previous());
        }
    }

    /**
     * Следующая диаграма
     */
    public void nextButton_Click() {
        //если список диаграм не закончился вызываем метод вывода диаграммы на экран
        stepPane.setVisible(false);
        if(iter_main.hasNext()){
            loadFile(iter_main.next());
        }
    }

    /**
     * Обработчик нажатия на кнопку Экспорт
     */
    public void ClickSaveFile() {
        //Вызываем метод сохранения в файл из класса FileWork
        FileWork.saveFile(_pane, _shapeList, _arrowList, _sceneShape);
    }

    /**
     * Обработчик нажатия на кнопку Сохранить в Png
     */
    public void ClickExpFile() {
        //Вызываем метод сохранения в png из класса FileWork
        FileWork.savePNG(_pane, _sceneShape);
    }

    /**
     * Обработчик нажатия на кнопку Ассоциация
     */
    public void associoationClick() {
        stepPane.setVisible(false);
        _eraserButton.setSelected(false);
        _dottedLineButton.setSelected(false);
    }

    /**
     * Обработчик нажатия на кнопку Зависимость
     */
    public void dottedClick() {
        stepPane.setVisible(false);
        _eraserButton.setSelected(false);
        _associationLineButton.setSelected(false);
    }

    /**
     * Обработчик нажатия на кнопку Ластик
     */
    public void eraserClick() {
        stepPane.setVisible(false);
        _associationLineButton.setSelected(false);
        _dottedLineButton.setSelected(false);
    }
}