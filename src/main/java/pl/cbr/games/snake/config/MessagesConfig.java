package pl.cbr.games.snake.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "messages")
public class MessagesConfig {
    String title;
    String endGame;
    String pausedGame;
    String startGame;
    String nextLevel;
    String levelInfo;
    String allPointsToFinish;
}
