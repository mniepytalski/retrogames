package pl.cbr.games.snake.player;

public class PlayerConfiguration {

    int leftKey;
    int rightKey;
    int upKey;
    int downKey;

    public PlayerConfiguration(int leftKey, int rightKey, int upKey, int downKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.downKey = downKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public int getUpKey() {
        return upKey;
    }

    public int getDownKey() {
        return downKey;
    }
}
