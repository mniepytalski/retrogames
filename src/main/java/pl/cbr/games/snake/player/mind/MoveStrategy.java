package pl.cbr.games.snake.player.mind;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.objects.BoardObject;
import pl.cbr.games.snake.player.BotPlayer;

import java.util.Optional;

public class MoveStrategy extends MoveStrategyBase {

    public MoveStrategy(BotPlayer player, BoardModel boardModel) {
        super(player, boardModel);
    }

    public void calculateMove() {
        if (avoidingObstacles() ) {
            return;
        }
        if ( canMove() ) {
            turnLeft();
            if (boardModel.isOutsideBoard(calcNextPosition()) ) {
                opositeDirection();
            }
        }
    }
}
