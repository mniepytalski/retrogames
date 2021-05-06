package pl.cbr.games.snake.geom2d;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
    private int x;
    private int y;

    public void add(Point a) {
        x += a.getX();
        y += a.getY();
    }

    public void minus(Point a) {
        x -= a.getX();
        y -= a.getY();
    }

    public void set(Point a) {
        x = a.getX();
        y = a.getY();
    }
}
