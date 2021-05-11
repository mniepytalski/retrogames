package pl.cbr.games.snake.config;

import lombok.Data;
import pl.cbr.system.config.ConfigFile;
import pl.cbr.system.config.ConfigInterface;
import pl.cbr.system.config.ConfigKey;

@ConfigFile(value = "game.properties", prefix = "message.")
@Data
public class MessagesConfig implements ConfigInterface {

    @ConfigKey("title")
    String title;

    @ConfigKey("end-game")
    String endGame;
}
