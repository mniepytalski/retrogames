package pl.cbr.games.snake;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.cbr.games.snake.config.GameConfig;

@Data
@AllArgsConstructor
public class Apple {
    private int x;
    private int y;
    private final GameConfig gameConfig;

    public void locateApple() {
        int r = (int) (Math.random() * gameConfig.getRandomPosition());
        x = r * gameConfig.getDotSize();

        r = (int) (Math.random() * gameConfig.getRandomPosition());
        y = r * gameConfig.getDotSize();
    }
}
