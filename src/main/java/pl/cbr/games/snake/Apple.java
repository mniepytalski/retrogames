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
    private final GameResources gameResources;

    public Apple(GameConfig gameConfig, GameResources gameResources) {
        this.boardModel = new BoardModel(gameConfig);
        this.gameConfig = gameConfig;
        this.gameResources = gameResources;
        this.position = new Point(0,0);
    }

    public void setRandomPosition() {
        position.setX((int) (Math.random() * boardModel.getBoard().getRightBottom().getX()) );
        position.setY((int) (Math.random() * boardModel.getBoard().getRightBottom().getY()) );
    }

    @Override
    public void doDrawing(Graphics g) {
        Point applePosition = getPosition().multiply(gameConfig.getDotSize());
        g.drawImage(gameResources.getApple(), applePosition.getX(), applePosition.getY(), null);
    }
}
