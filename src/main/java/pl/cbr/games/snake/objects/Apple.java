package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;

import java.awt.*;

public class Apple extends BoardObject implements Drawing {

    public Apple(GameConfig gameConfig, GameResources gameResources) {
        super(gameConfig, gameResources);
    }

    @Override
    public void doDrawing(Graphics g) {
        Point applePosition = getPosition().multiply(gameConfig.getDotSize());
        g.drawImage(gameResources.getApple(), applePosition.getX(), applePosition.getY(), null);
    }
}
