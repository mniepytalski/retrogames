package pl.cbr.games.snake.config;

import lombok.Data;
import pl.cbr.system.config.ConfigFile;
import pl.cbr.system.config.ConfigKey;

@ConfigFile("messages.properties")
@Data
public class MessagesConfig {

    @ConfigKey("title")
    String title;

    @ConfigKey("end-game")
    String endGame;
}
