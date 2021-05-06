package pl.cbr.games.snake.player;

import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
    private int length;
    private final List<Point> view;
    private final GameConfig gameConfig;

    public PlayerModel(GameConfig gameConfig) {
        view = new ArrayList<>();
        this.gameConfig = gameConfig;
    }

    public void initPlayer(Point startPosition) {
        view.clear();
        this.length = gameConfig.getStartLength();
        for ( int i=0; i<gameConfig.getAllDots(); i++) {
            view.add(new Point(0,0));
        }
        initPlayerView(startPosition);
    }

    private void initPlayerView(Point startPosition) {
        for (int z = 0; z < getLength(); z++) {
            set(z, new Point(startPosition.getX() - z * 10, startPosition.getY()));
        }
    }

    public void move(MoveDirection direction) {
        moveModel();
        if (direction == MoveDirection.LEFT) {
            get(0).minus(new Point(gameConfig.getDotSize(),0));
        }
        if (direction==MoveDirection.RIGHT) {
            get(0).add(new Point(gameConfig.getDotSize(),0));
        }
        if (direction==MoveDirection.UP) {
            get(0).minus(new Point(0,gameConfig.getDotSize()));
        }
        if (direction==MoveDirection.DOWN) {
            get(0).add(new Point(0,gameConfig.getDotSize()));
        }
    }

    private void moveModel() {
        for (int z = getLength(); z > 0; z--) {
            set(z, get(z-1));
        }
    }

    public boolean checkOurselfCollision() {
        for (int z = getLength(); z > 0; z--) {
            if ((z > 4) && get(0).equals(get(z))) {
                return true;
            }
        }
        return false;
    }

    public boolean isOutside(Rectangle boardModel) {
        return boardModel.isOutside(get(0));
    }

    public void incLength() {
        length++;
    }

    public int getLength() {
        return length;
    }

    public Point get(int z) {
        return view.get(z);
    }

    public void set(int z, Point point) {
        view.get(z).set(point);
    }

}
