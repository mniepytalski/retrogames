package pl.cbr.games.snake.player;

import pl.cbr.games.snake.BoardConfiguration;

public class PlayerOnBoard {

    private int dots;

    private final int x[] = new int[BoardConfiguration.ALL_DOTS];
    private final int y[] = new int[BoardConfiguration.ALL_DOTS];

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public int getX(int z) {
        return x[z];
    }

    public int getY(int z) {
        return y[z];
    }

    public int getDots() {
        return dots;
    }

    public void setDots(int dots) {
        this.dots = dots;
    }

    public void incDots() {
        dots++;
    }
}
