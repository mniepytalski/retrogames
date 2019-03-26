package pl.cbr.games.snake.player;

import pl.cbr.games.snake.Board;
import pl.cbr.games.snake.BoardConfiguration;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.MoveDirection;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    String name;
    int points;

    PlayerState state;
    PlayerOnBoard board;
    int startX;
    int startY;

    public Player(String name, int startX, int startY, PlayerConfiguration playerConfiguration) {
        this.name = name;
        state = new PlayerState(playerConfiguration);
        board = new PlayerOnBoard();
        this.startX = startX;
        this.startY = startY;
    }

    public String getName() {
        return name;
    }

    public void resetPlayer() {
        board.setDots(BoardConfiguration.DOTS_ON_START);
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public PlayerState getState() {
        return state;
    }

    public PlayerOnBoard getBoard() {
        return board;
    }

    public void initGame() {
        board.setDots(BoardConfiguration.DOTS_ON_START);

        for (int z = 0; z < board.getDots(); z++) {
            board.getX()[z] = startX - z * 10;
            board.getY()[z] = startY;
        }
    }

    public void move() {

        for (int z = getBoard().getDots(); z > 0; z--) {
            getBoard().getX()[z] = getBoard().getX(z - 1);
            getBoard().getY()[z] = getBoard().getY(z - 1);
        }

        if (getState().getDirection()== MoveDirection.LEFT) {
            getBoard().getX()[0] -= BoardConfiguration.DOT_SIZE;
        }

        if (getState().getDirection()==MoveDirection.RIGHT) {
            getBoard().getX()[0] += BoardConfiguration.DOT_SIZE;
        }

        if (getState().getDirection()==MoveDirection.UP) {
            getBoard().getY()[0] -= BoardConfiguration.DOT_SIZE;
        }

        if (getState().getDirection()==MoveDirection.DOWN) {
            getBoard().getY()[0] += BoardConfiguration.DOT_SIZE;
        }
    }

    public boolean checkCollision() {
        for (int z = getBoard().getDots(); z > 0; z--) {

            if ((z > 4) && (getBoard().getX(0) == getBoard().getX(z)) &&
                    (getBoard().getY(0) == getBoard().getY(z))) {
                getState().setInGame(false);
            }
        }

        if (getBoard().getY(0) >= BoardConfiguration.B_HEIGHT) {
            getState().setInGame(false);
        }

        if (getBoard().getY(0) < 0) {
            getState().setInGame(false);
        }

        if (getBoard().getX(0) >= BoardConfiguration.B_WIDTH) {
            getState().setInGame(false);
        }

        if (getBoard().getX(0) < 0) {
            getState().setInGame(false);
        }
        return getState().isInGame();
    }

    public void doDrawing(Graphics g, Board board) {
        for (int z = 0; z < getBoard().getDots(); z++) {
            if (z == 0) {
                g.drawImage(GameResources.getHead(),  getBoard().getX(z), getBoard().getY(z), board);
            } else {
                g.drawImage(GameResources.getBall(), getBoard().getX(z), getBoard().getY(z), board);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        getState().keyPressed(e);
    }
}
