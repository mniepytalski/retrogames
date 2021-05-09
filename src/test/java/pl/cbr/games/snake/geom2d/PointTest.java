package pl.cbr.games.snake.geom2d;

import org.junit.Assert;
import org.junit.Test;

public class PointTest {

    @Test
    public void testAddition() {
        // given
        Point pointA = new Point(10,10);
        Point pointB = new Point(20,30);

        //
        Point pointC = pointA.add(pointB);

        //
        Assert.assertEquals(30, pointC.getX());
        Assert.assertEquals(40, pointC.getY());
    }

    @Test
    public void testMinus() {
        // given
        Point pointA = new Point(20,30);
        Point pointB = new Point(10,10);

        //
        Point pointC = pointA.minus(pointB);

        //
        Assert.assertEquals(10, pointC.getX());
        Assert.assertEquals(20, pointC.getY());
    }

    @Test
    public void testSet() {
        // given
        Point pointA = new Point(20,30);
        Point pointB = new Point(10,10);

        //
        pointA.set(pointB);

        //
        Assert.assertEquals(10, pointA.getX());
        Assert.assertEquals(10, pointA.getY());
    }

    @Test
    public void testConstructor() {
        // given
        Point pointA = new Point(20,30);

        //
        Point pointB = new Point(pointA);

        //
        Assert.assertEquals(20, pointB.getX());
        Assert.assertEquals(30, pointB.getY());
    }

    @Test
    public void testMultiply() {
        // given
        Point pointA = new Point(20,30);
        int value = 10;

        //
        pointA.multiply(value);

        //
        Assert.assertEquals(200, pointA.getX());
        Assert.assertEquals(300, pointA.getY());
    }

    public void testDivision() {
        // given
        Point pointA = new Point(200,300);
        int value = 10;

        //
        pointA.division(value);

        //
        Assert.assertEquals(20, pointA.getX());
        Assert.assertEquals(30, pointA.getY());
    }
}
