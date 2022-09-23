package Shape;

import javafx.geometry.Point2D;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class shapesTest {

    public static shapes _usecaseShapes;

    public static shapes _sceneShapes;

    public static shapes _actorShapes;

    public void braw(){

    }

    @Test
    public void testMaxY() {
        _usecaseShapes = new shapes(shapeType.USECASE, new Point2D(30,30), "", this::braw);
        Assert.assertEquals(_usecaseShapes.maxY(),30,0.001);
    }

    @Test
    public void testMaxX() {
        _usecaseShapes = new shapes(shapeType.USECASE, new Point2D(30,30), "", this::braw);
        Assert.assertEquals(_usecaseShapes.maxX(),30,0.001);
    }

    @Test
    public void testEquals() {
        _usecaseShapes = new shapes(shapeType.USECASE, new Point2D(30,30), "", this::braw);
        _sceneShapes = new shapes(shapeType.SCENE, new Point2D(30,30), "", this::braw);
        _actorShapes = new shapes(shapeType.ACTOR, new Point2D(30,30), "", this::braw);
        Assert.assertTrue(_usecaseShapes.Equals(_sceneShapes));
        Assert.assertFalse(_usecaseShapes.Equals(_actorShapes));
    }
}