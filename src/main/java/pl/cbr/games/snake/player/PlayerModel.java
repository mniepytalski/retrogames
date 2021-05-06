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
        initPlayerView(startPosition);
    }

    private void initPlayerView(Point startPosition) {
        for (int z = 0; z < getLength(); z++) {
            view.add((new Point(startPosition.getX() - z * gameConfig.getDotSize(), startPosition.getY())));
        }
    }

    public void move(MoveDirection direction) {
        if (direction == MoveDirection.LEFT) {
            view.add(0, get(0).minus(new Point(gameConfig.getDotSize(),0)));
        }
        if (direction==MoveDirection.RIGHT) {
            view.add(0, get(0).add(new Point(gameConfig.getDotSize(),0)));
        }
        if (direction==MoveDirection.UP) {
            view.add(0, get(0).minus(new Point(0,gameConfig.getDotSize())));
        }
        if (direction==MoveDirection.DOWN) {
            view.add(0, get(0).add(new Point(0,gameConfig.getDotSize())));
        }
        limitLength();
    }

    private void limitLength() {
        if ( getLength() < view.size() ) {
            view.remove(view.size() - 1);
        }
    }

    public int getViewSize() {
        return view.size();
    }

    public boolean checkOurselfCollision() {
        for (int z = getViewSize()-1; z > 0; z--) {
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
