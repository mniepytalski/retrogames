package pl.cbr.games.snake.config;

import lombok.Data;
import pl.cbr.system.config.ConfigFile;
import pl.cbr.system.config.ConfigKey;

@ConfigFile("game.properties")
@Data
public class GameConfig {

    @ConfigKey("board.all-dots")
    int allDots;

    @ConfigKey("board.width")
    int width;

    @ConfigKey("board.height")
    int height;

    @ConfigKey("board.dot-size")
    int dotSize;

    @ConfigKey("board.dots-on-start")
    int dotsOnStart;

    @ConfigKey("board.random-position")
    int randomPosition;

    @ConfigKey("players.1.name")
    String player1Name;

    @ConfigKey("players.1.position.x")
    int player1PositionX;

    @ConfigKey("players.1.position.y")
    int player1PositionY;

    @ConfigKey("players.2.name")
    String player2Name;

    @ConfigKey("players.2.position.x")
    int player2PositionX;

    @ConfigKey("players.2.position.y")
    int player2PositionY;
}
