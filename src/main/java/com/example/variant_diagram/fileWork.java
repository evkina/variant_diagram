package com.example.variant_diagram;

import Shape.arrowList;
import Shape.shapeList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;


/**
 * Class for work with file
 * Author Evkina
 */
public class fileWork {
    /** Method for saving file
     * @param _pane pane for drawing
     * @param _shapeList list of blocks on scheme
     * @param _arrowList list of arrows on scheme
     */
    public static void saveFile(Pane _pane, shapeList _shapeList, arrowList _arrowList) {
        FileChooser _fileChooser = new FileChooser();
        _fileChooser.setTitle("Выберите папку для сохранения...");
        _fileChooser.setInitialFileName("MyProject");
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

    /** Method for export image
     * @param _pane pane for drawing
     */
    public static void exportFile(Pane _pane){
        FileChooser _fileChooser = new FileChooser();
        _fileChooser.getExtensionFilters().clear();
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
