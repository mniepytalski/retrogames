package pl.cbr.games.snake;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;
import pl.cbr.games.snake.player.PlayerModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class BoardModel {
    private final Rectangle board;

    private final List<PlayerModel> playerModels;
    private final List<Point> applesPosition;
    private final List<Point> walls;

    public BoardModel(GameConfig gameConfig) {
        playerModels = new ArrayList<>();
        applesPosition = new ArrayList<>();
        walls = new ArrayList<>();

        board = new Rectangle(new Point(0,0),
                (new Point(gameConfig.getWidth(),gameConfig.getHeight())).division(gameConfig.getDotSize()));
    }
}
