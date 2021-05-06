package pl.cbr.games.snake;

import lombok.Data;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;

@Data
public class Apple {
    private final Point position;
    private final GameConfig gameConfig;

    public Apple(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        this.position = new Point(0,0);
    }

    public void setRandomPosition() {
        int r = (int) (Math.random() * gameConfig.getRandomPosition());
        position.setX(r * gameConfig.getDotSize());

        r = (int) (Math.random() * gameConfig.getRandomPosition());
        position.setY(r * gameConfig.getDotSize());
    }
}
