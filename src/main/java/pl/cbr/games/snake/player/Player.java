package pl.cbr.games.snake.player;

import lombok.Data;
import pl.cbr.games.snake.Board;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;

@Data
public class Player {

	private int id;
    private String name;
    private final Point startPosition;
    private PlayerControlConfiguration playerControlConfiguration;
    private PlayerState playerState;
    private PlayerModel playerModel;
    private final GameConfig gameConfig;

    private int points;

    private static int idGenerator = 1;

    public Player(String name, Point startPosition, PlayerControlConfiguration playerControlConfiguration, GameConfig gameConfig) {
        this.id = idGenerator++;
    	this.name = name;
        this.startPosition = startPosition;
        this.gameConfig = gameConfig;
        this.playerControlConfiguration = playerControlConfiguration;
        playerState = new PlayerState(playerControlConfiguration);
        playerModel = new PlayerModel(gameConfig);
    }

    public void initGame() {
        playerModel.initPlayer(startPosition);
    }

    public void move() {
        getPlayerModel().move(getPlayerState().getDirection());
    }

    public boolean checkCollision() {
        getPlayerState().setInGame(!getPlayerModel().checkOurselfCollision());

        Rectangle boardRectangle = new Rectangle(new Point(0,0), new Point(gameConfig.getWidth(),gameConfig.getHeight()));
        getPlayerState().setInGame(!getPlayerModel().isOutside(boardRectangle));

        return getPlayerState().isInGame();
    }

    public void doDrawing(Graphics g, Board board) {
        for (int z = 0; z < getPlayerModel().getLength(); z++) {
            if (z == 0) {
                g.drawImage(GameResources.getHead(),  getPlayerModel().get(z).getX(), getPlayerModel().get(z).getY(), board);
            } else {
                g.drawImage(GameResources.getBall(getId()%2), getPlayerModel().get(z).getX(), getPlayerModel().get(z).getY(), board);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }
}
