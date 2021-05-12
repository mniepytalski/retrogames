package pl.cbr.games.snake;

import lombok.Data;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;

import java.awt.*;

@Data
public class Apple implements Drawing {
    private final Point position;
    private final BoardModel boardModel;
    private final GameConfig gameConfig;

    public Apple(GameConfig gameConfig) {
        this.boardModel = new BoardModel(gameConfig);
        this.gameConfig = gameConfig;
        this.position = new Point(0,0);
    }

    public void setRandomPosition() {
        position.setX((int) (Math.random() * boardModel.getBoard().getRightBottom().getX()) );
        position.setY((int) (Math.random() * boardModel.getBoard().getRightBottom().getY()) );
    }

    @Override
    public void doDrawing(Graphics g) {
        Point applePosition = getPosition().multiply(gameConfig.getDotSize());
        g.drawImage(GameResources.getApple(), applePosition.getX(), applePosition.getY(), null);
    }
}
