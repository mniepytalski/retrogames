package pl.cbr.games.snake.player;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlayerConfiguration {
    private int leftKey;
    private int rightKey;
    private int upKey;
    private int downKey;
}
