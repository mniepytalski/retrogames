package pl.cbr.games.snake.config;

import lombok.Data;
import pl.cbr.games.snake.geom2d.Point;

@Data
public class PositionConfig {
    private int x;
    private int y;

    public Point getPoint() {
        return new Point(x,y);
    }
}
