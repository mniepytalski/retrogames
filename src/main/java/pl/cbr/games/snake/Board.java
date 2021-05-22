package pl.cbr.games.snake;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.levels.LevelScenarios;
import pl.cbr.games.snake.objects.BoardObject;
import pl.cbr.games.snake.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.*;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Component
public class Board extends JPanel implements ActionListener, Drawing {

    private Timer timer;
    private boolean debug = false;
    private GameStatus gameStatus = GameStatus.RUNNING;

    private final static int DELAY;

    private final transient GameConfig gameConfig;
    private final transient BoardGraphics boardGraphics;
    private final transient GameResources gameResources;
    private final transient BoardModel boardModel;
    private final transient LevelScenarios levelScenarios;

    static {
        DELAY = 200;
    }

    public Board(GameConfig gameConfig, GameResources gameResources, BoardGraphics boardGraphics, BoardModel boardModel, LevelScenarios levelScenarios) {
        this.gameConfig = gameConfig;
        this.boardGraphics = boardGraphics;
        this.boardModel = boardModel;
        this.levelScenarios = levelScenarios;
        this.gameResources = gameResources;
        this.gameConfig.getPlayers().forEach(playerConfig -> boardModel.addPlayer(new Player(playerConfig, gameConfig, gameResources)));
        initBoard();
    }

    private void initBoard() {
        boardGraphics.init(this);
        timer = new Timer(DELAY, this);
        timer.start();
        initGame();
    }

    public void initGame() {
        initLevel();
        boardModel.getPlayers().forEach(Player::initPlayer);
        if (!timer.isRunning()) {
            timer.start();
        }
        gameStatus = GameStatus.RUNNING;
    }

    private void initLevel() {
        boardModel.init(levelScenarios.getLevel());
    }

    @Override
    public void paintComponent(Graphics g) {
        if ( debug ) {
            log.info("paintComponent, gameStatus:{}", gameStatus);
        }
        if ( GameStatus.RUNNING == gameStatus
                || GameStatus.PAUSED == gameStatus
                || GameStatus.NEXT_LEVEL == gameStatus) {
            super.paintComponent(g);
            doDrawing(g);
        }
    }

    public void doDrawing(Graphics g) {
        if (gameStatus != GameStatus.NEXT_LEVEL && boardModel.getPlayers().stream().noneMatch(player -> player.getPlayerState().isInGame())) {
            gameStatus = GameStatus.STOP;
        }
        boardGraphics.printBoard(gameStatus,g,this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boardModel.getPlayers().stream().filter(player -> player.getPlayerState().isInGame()).forEach(player -> {
            Optional<BoardObject> optionalObject = boardModel.checkCollisions(player);
            if ( optionalObject.isPresent()) {
                if ( optionalObject.get().isEndGame() ) {
                    gameStatus = GameStatus.STOP;
                    timer.stop();
                } else {
                    optionalObject.get().action(player.getPlayerModel());
                    if ( player.getPlayerModel().getPoints()>=levelScenarios.getLevel().getPointsToFinish() ) {
                        levelScenarios.setNextLevel();
                        gameStatus = GameStatus.NEXT_LEVEL;
                    }
                }
            }
            if (player.checkCollision() ) {
                gameStatus = GameStatus.STOP;
                timer.stop();
            }
            if ( GameStatus.RUNNING == gameStatus ) {
                player.move();
            }
        });
        repaint();
    }
}