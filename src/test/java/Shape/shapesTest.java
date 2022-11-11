package Shape;

import javafx.geometry.Point2D;
import org.testng.Assert;
import org.testng.annotations.Test;

public class shapesTest {

    public static Shapes _usecaseShapes;

    public static Shapes _sceneShapes;

    public static Shapes _actorShapes;

    public void braw(){

    }

    @Test
    public void testMaxY() {
        _usecaseShapes = new Shapes(ShapeType.USECASE, new Point2D(30,30), "", this::braw);
        Assert.assertEquals(_usecaseShapes.maxY(),30,0.001);
    }

    @Test
    public void testMaxX() {
        _usecaseShapes = new Shapes(ShapeType.USECASE, new Point2D(30,30), "", this::braw);
        Assert.assertEquals(_usecaseShapes.maxX(),30,0.001);
    }

    @Test
    public void testEquals() {
        _usecaseShapes = new Shapes(ShapeType.USECASE, new Point2D(30,30), "", this::braw);
        _sceneShapes = new Shapes(ShapeType.SCENE, new Point2D(30,30), "", this::braw);
        _actorShapes = new Shapes(ShapeType.ACTOR, new Point2D(30,30), "", this::braw);
        Assert.assertTrue(_usecaseShapes.Equals(_sceneShapes));
        Assert.assertFalse(_usecaseShapes.Equals(_actorShapes));
    }
}