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
        PlayerConfigMapper playerConfigMapper = new PlayerConfigMapper();
        this.playerControlConfiguration = playerConfigMapper.map(controlConfig);
        initState();
    }

    public void initState() {
        direction = MoveDirection.RIGHT;
        inGame = true;
    }

    void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == playerControlConfiguration.getLeftKey()) && (getDirection()!=MoveDirection.RIGHT)) {
            direction = MoveDirection.LEFT;
        }
        if ((e.getKeyCode() == playerControlConfiguration.getRightKey()) && (getDirection()!=MoveDirection.LEFT)) {
            direction = MoveDirection.RIGHT;
        }
        if ((e.getKeyCode() == playerControlConfiguration.getUpKey()) && (getDirection()!=MoveDirection.DOWN)) {
            direction = MoveDirection.UP;
        }
        if ((e.getKeyCode() == playerControlConfiguration.getDownKey()) && (getDirection()!=MoveDirection.UP)) {
            direction = MoveDirection.DOWN;
        }
    }
}
