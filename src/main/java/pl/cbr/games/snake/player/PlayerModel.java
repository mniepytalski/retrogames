package pl.cbr.games.snake.player;

import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Collision;
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
            addHaed(getHead().minus(new Point(gameConfig.getDotSize(),0)));
        }
        if (direction==MoveDirection.RIGHT) {
            addHaed(getHead().add(new Point(gameConfig.getDotSize(),0)));
        }
        if (direction==MoveDirection.UP) {
            addHaed(getHead().minus(new Point(0,gameConfig.getDotSize())));
        }
        if (direction==MoveDirection.DOWN) {
            addHaed(getHead().add(new Point(0,gameConfig.getDotSize())));
        }
        limitTail();
    }

    private void limitTail() {
        if ( getLength() < view.size() ) {
            view.remove(view.size() - 1);
        }
    }

    public int getViewSize() {
        return view.size();
    }

    public boolean checkOurselfCollision() {
        Collision collision = new Collision();
        return collision.check(view);
    }

    public boolean isOutside(Rectangle boardModel) {
        return boardModel.isOutside(getHead());
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

    public Point getHead() {
        return view.get(0);
    }

    public void set(int z, Point point) {
        view.get(z).set(point);
    }

    private void addHaed(Point point) {
        view.add(0,point);
    }

}
