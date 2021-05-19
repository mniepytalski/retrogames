package pl.cbr.games.snake;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;
import pl.cbr.games.snake.levels.Level;
import pl.cbr.games.snake.objects.Apple;
import pl.cbr.games.snake.objects.BoardObject;
import pl.cbr.games.snake.objects.Lemon;
import pl.cbr.games.snake.objects.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@Component
public class BoardModel {

    private final GameConfig gameConfig;
    private final GameResources gameResources;
    private final Rectangle board;

    private final transient List<BoardObject> objects;

    public BoardModel(GameConfig gameConfig, GameResources gameResources) {
        this.gameConfig = gameConfig;
        this.gameResources = gameResources;
        objects = new ArrayList<>();

        board = new Rectangle(new Point(0,0),
                (new Point(gameConfig.getWidth(),gameConfig.getHeight())).division(gameConfig.getDotSize()));
    }

    public void init(Level level) {
        objects.clear();
        IntStream.rangeClosed(1, level.getApples()).forEach(n -> getObjects().add(new Apple(gameConfig, gameResources)));
        IntStream.rangeClosed(1, level.getWalls()).forEach(n -> getObjects().add(new Wall(gameConfig, gameResources)));
        IntStream.rangeClosed(1, level.getLemons()).forEach(n -> getObjects().add(new Lemon(gameConfig, gameResources)));
    }
}
