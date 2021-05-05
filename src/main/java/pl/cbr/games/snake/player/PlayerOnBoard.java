package pl.cbr.games.snake.player;

import lombok.Data;
import pl.cbr.games.snake.config.GameConfig;

@Data
public class PlayerOnBoard {
    private int dots;
    private final int[] x;
    private final int[] y;

    private final GameConfig gameConfig;

    public PlayerOnBoard(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        x = new int[gameConfig.getAllDots()];
        y = new int[gameConfig.getAllDots()];
    }

    public int getX(int z) {
        return x[z];
    }

    public int getY(int z) {
        return y[z];
    }

    public void incDots() {
        dots++;
    }
}
