package pl.cbr.games.snake.player;

import lombok.Data;
import java.awt.event.KeyEvent;

@Data
public class PlayerState {

    private MoveDirection direction;
    private boolean inGame = true;

    private final PlayerControlConfiguration playerControlConfiguration;

    public PlayerState(PlayerControlConfiguration playerControlConfiguration) {
        this.direction = MoveDirection.RIGHT;
        this.playerControlConfiguration = playerControlConfiguration;
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
