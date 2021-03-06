package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.player.PlayerModel;

@Data
public abstract class BoardObject implements Drawing {

    private Point position;
    final BoardModel boardModel;
    final GameConfig gameConfig;
    final GameResources gameResources;

    protected BoardObject(GameConfig gameConfig, GameResources gameResources, BoardModel boardModel) {
        this.boardModel = boardModel;
        this.gameConfig = gameConfig;
        this.gameResources = gameResources;
        this.position = new Point(0,0);
    }

    public void setRandomPosition() {
        position.setX((int) (Math.random() * boardModel.getBoard().getRightBottom().getX()) );
        position.setY((int) (Math.random() * boardModel.getBoard().getRightBottom().getY()) );
    }

    public abstract boolean isEndGame();

    public void action(PlayerModel playerModel) {
        for ( int i=0; i<100; i++ ) {
            setRandomPosition();
            if (!boardModel.collisionWithPoint()) {
                break;
            }
        }
    }
}
