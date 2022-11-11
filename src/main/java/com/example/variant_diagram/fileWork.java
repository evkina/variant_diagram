package com.example.variant_diagram;

import Shape.ArrowList;
import Shape.ShapeList;
import Shape.Shapes;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;


/**
 * Класс для работы с файлом
 * Автор Евкина
 */
public class FileWork {
    /** Метод сохранения в файл
     * @param _pane панель для рисования
     * @param _shapeList список объектов на схеме
     * @param _arrowList список стрелок на схеме
     */
    public static void saveFile(Pane _pane, ShapeList _shapeList, ArrowList _arrowList, Shapes _sceneShape) {
        FileChooser _fileChooser = new FileChooser();
        _fileChooser.setTitle("Выберите папку для сохранения...");
        _fileChooser.setInitialFileName(_sceneShape.get_textField().getText());
        _fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл", "*.ActDiagram"));
        File _file = _fileChooser.showSaveDialog(_pane.getScene().getWindow());
        try {
            FileOutputStream _fos = new FileOutputStream(_file);
            ObjectOutputStream _outStream = new ObjectOutputStream(_fos);

            _outStream.writeObject(_shapeList);
            _outStream.writeObject(_arrowList);

            _outStream.flush();
            _outStream.close();
            System.out.println("Сохранено!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error " + e.getMessage());
        }
    }

    /** Метод сохранения в png
     * @param _pane панель для рисования
     */
    public static void savePNG(Pane _pane, Shapes _sceneShape){
        FileChooser _fileChooser = new FileChooser();
        _fileChooser.getExtensionFilters().clear();
        _fileChooser.setInitialFileName(_sceneShape.get_textField().getText());
        _fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        File _file = _fileChooser.showSaveDialog(null);

        if(_file != null){
            try {
                WritableImage snapShot = _pane.snapshot(new SnapshotParameters(), null);

                RenderedImage renderedImage = SwingFXUtils.fromFXImage(snapShot, null);

                ImageIO.write(renderedImage, "png", _file);
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }
}
