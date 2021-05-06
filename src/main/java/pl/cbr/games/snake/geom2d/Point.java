package pl.cbr.games.snake.geom2d;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
    private int x;
    private int y;

    public Point(Point point) {
        x = point.getX();
        y = point.getY();
    }

    public Point add(Point a) {
        return new Point(x + a.getX(), y + a.getY());
    }

    public Point minus(Point a) {
        return new Point(x - a.getX(), y - a.getY());
    }

    public void set(Point a) {
        x = a.getX();
        y = a.getY();
    }

    public void multiply(int value) {
        x *= value;
        y *= value;
    }

    public void division(int value) {
        x /= value;
        y /= value;
    }
}
