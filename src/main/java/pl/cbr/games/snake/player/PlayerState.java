package pl.cbr.games.snake.player;

import pl.cbr.games.snake.MoveDirection;
import java.awt.event.KeyEvent;

public class PlayerState {

    private MoveDirection direction;
    private boolean inGame = true;

    private final PlayerConfiguration playerConfiguration;

    public PlayerState(PlayerConfiguration playerConfiguration) {
        this.direction = MoveDirection.RIGHT;
        this.playerConfiguration = playerConfiguration;
    }

    void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == playerConfiguration.getLeftKey()) && (getDirection()!=MoveDirection.RIGHT)) {
            direction = MoveDirection.LEFT;
        }

        if ((key == playerConfiguration.getRightKey()) && (getDirection()!=MoveDirection.LEFT)) {
            direction = MoveDirection.RIGHT;
        }

        if ((key == playerConfiguration.getUpKey()) && (getDirection()!=MoveDirection.DOWN)) {
            direction = MoveDirection.UP;
        }

        if ((key == playerConfiguration.getDownKey()) && (getDirection()!=MoveDirection.UP)) {
            direction = MoveDirection.DOWN;
        }
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
