package pl.cbr.games.snake.player;

import lombok.Data;
import pl.cbr.games.snake.Board;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.MoveDirection;
import pl.cbr.games.snake.config.GameConfig;

import java.awt.*;
import java.awt.event.KeyEvent;

@Data
public class Player {

	private int id;
    private String name;
    private int startX;
    private int startY;
    private PlayerConfiguration playerConfiguration;
    private PlayerState state;
    private PlayerOnBoard board;
    private final GameConfig gameConfig;

    private int points;

    private static int idGenerator = 1;

    public Player(String name, int startX, int startY, PlayerConfiguration playerConfiguration, GameConfig gameConfig) {
        this.id = idGenerator++;
    	this.name = name;
        this.startX = startX;
        this.startY = startY;
        this.gameConfig = gameConfig;
        this.playerConfiguration = playerConfiguration;
        state = new PlayerState(playerConfiguration);
        board = new PlayerOnBoard(gameConfig);
    }

    public void initGame() {
        board.setDots(gameConfig.getDotsOnStart());

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
            getBoard().getX()[0] -= gameConfig.getDotSize();
        }

        if (getState().getDirection()==MoveDirection.RIGHT) {
            getBoard().getX()[0] += gameConfig.getDotSize();
        }

        if (getState().getDirection()==MoveDirection.UP) {
            getBoard().getY()[0] -= gameConfig.getDotSize();
        }

        if (getState().getDirection()==MoveDirection.DOWN) {
            getBoard().getY()[0] += gameConfig.getDotSize();
        }
    }

    public boolean checkCollision() {
        for (int z = getBoard().getDots(); z > 0; z--) {

            if ((z > 4) && (getBoard().getX(0) == getBoard().getX(z)) &&
                    (getBoard().getY(0) == getBoard().getY(z))) {
                getState().setInGame(false);
            }
        }

        if (getBoard().getY(0) >= gameConfig.getHeight()) {
            getState().setInGame(false);
        }

        if (getBoard().getY(0) < 0) {
            getState().setInGame(false);
        }

        if (getBoard().getX(0) >= gameConfig.getWidth()) {
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
                g.drawImage(GameResources.getBall(getId()%2), getBoard().getX(z), getBoard().getY(z), board);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        getState().keyPressed(e);
    }
}
