package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.player.PlayerModel;

import java.awt.*;

public class Wall extends BoardObject implements Drawing {

    public Wall(GameConfig gameConfig, GameResources gameResources, BoardModel boardModel) {
        super(gameConfig, gameResources, boardModel);
    }

    @Override
    public void doDrawing(Graphics g) {
        Point applePosition = getPosition().multiply(gameConfig.getDotSize());
        g.drawImage(gameResources.getWall(), applePosition.getX(), applePosition.getY(), null);
    }

    @Override
    public boolean isEndGame() {
        return true;
    }

    @Override
    public void action(PlayerModel playerModel) {

    }
}
