package pl.cbr.games.snake;

import lombok.Getter;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;

@Getter
public class BoardModel {
    Rectangle board;

    public BoardModel(GameConfig gameConfig) {
        board = new Rectangle(new Point(0,0),
                (new Point(gameConfig.getWidth(),gameConfig.getHeight())).division(gameConfig.getDotSize()));
    }
}
