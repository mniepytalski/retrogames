package pl.cbr.games.snake.player;

import lombok.Data;
import pl.cbr.games.snake.config.ControlConfig;
import pl.cbr.games.snake.config.control.PlayerConfigMapper;
import pl.cbr.games.snake.config.control.PlayerControlConfiguration;

import java.awt.event.KeyEvent;

@Data
public class PlayerState {

    private MoveDirection direction;
    private boolean inGame = true;

    private PlayerControlConfiguration playerControlConfiguration;

    public PlayerState(ControlConfig controlConfig) {
        this.direction = MoveDirection.RIGHT;

        PlayerConfigMapper playerConfigMapper = new PlayerConfigMapper();
        this.playerControlConfiguration = playerConfigMapper.map(controlConfig);
    }

    void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == playerControlConfiguration.getLeftKey()) && (getDirection()!=MoveDirection.RIGHT)) {
            direction = MoveDirection.LEFT;
        }

        if ((key == playerControlConfiguration.getRightKey()) && (getDirection()!=MoveDirection.LEFT)) {
            direction = MoveDirection.RIGHT;
        }

        if ((key == playerControlConfiguration.getUpKey()) && (getDirection()!=MoveDirection.DOWN)) {
            direction = MoveDirection.UP;
        }

        if ((key == playerControlConfiguration.getDownKey()) && (getDirection()!=MoveDirection.UP)) {
            direction = MoveDirection.DOWN;
        }
    }
}
