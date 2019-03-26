package pl.cbr.games.snake;

public class Apple {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void locateApple() {
        int r = (int) (Math.random() * BoardConfiguration.RAND_POS);
        x = ((r * BoardConfiguration.DOT_SIZE));

        r = (int) (Math.random() * BoardConfiguration.RAND_POS);
        y = ((r * BoardConfiguration.DOT_SIZE));
    }
}
