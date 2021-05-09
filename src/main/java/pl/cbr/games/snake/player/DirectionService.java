package pl.cbr.games.snake.player;

import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;

import java.util.HashMap;
import java.util.Map;

public class DirectionService {
    private final Map<MoveDirection, Point> directions;
    private final GameConfig gameConfig;

    public DirectionService(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        directions = new HashMap<>();
        prepareDirectionMap();
    }

    private void prepareDirectionMap() {
        directions.clear();
        directions.put(MoveDirection.LEFT, new Point(-gameConfig.getDotSize(),0));
        directions.put(MoveDirection.RIGHT, new Point(gameConfig.getDotSize(),0));
        directions.put(MoveDirection.UP, new Point(0,-gameConfig.getDotSize()));
        directions.put(MoveDirection.DOWN, new Point(0,gameConfig.getDotSize()));
    }

    public Point getVector(MoveDirection direction) {
        if ( directions.containsKey(direction) ) {
            return directions.get(direction);
        } else {
            return new Point(0,0);
        }
    }
}
