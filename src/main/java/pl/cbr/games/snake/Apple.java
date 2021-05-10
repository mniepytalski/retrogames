package pl.cbr.games.snake;

import lombok.Data;
import pl.cbr.games.snake.geom2d.Point;

@Data
public class Apple {
    private final Point position;
    private final BoardModel boardModel;

    public Apple(BoardModel boardModel) {
        this.boardModel = boardModel;
        this.position = new Point(0,0);
    }

    public void setRandomPosition() {
        position.setX((int) (Math.random() * boardModel.getBoard().getRightBottom().getX()) );
        position.setY((int) (Math.random() * boardModel.getBoard().getRightBottom().getY()) );
    }
}
