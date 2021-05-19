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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
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

    private final transient GameConfig gameConfig;
    private final transient BoardGraphics boardGraphics;
    private final transient GameResources gameResources;
    private final transient BoardModel boardModel;

    private final transient List<Player> players;
    private final transient LevelScenarios levelScenarios;

    private final static int DELAY;

    static {
        DELAY = 200;
    }

    public Board(GameConfig gameConfig, GameResources gameResources, BoardGraphics boardGraphics, BoardModel boardModel, LevelScenarios levelScenarios) {
        this.gameConfig = gameConfig;
        this.boardGraphics = boardGraphics;
        this.boardModel = boardModel;
        this.levelScenarios = levelScenarios;
        this.gameResources = gameResources;
        players = new ArrayList<>();
        this.gameConfig.getPlayers().forEach(playerConfig -> players.add(new Player(playerConfig, gameConfig, gameResources)));
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter(this));
        setBackground(Color.black);
        setFocusable(true);
        Dimension dimension = new Dimension(gameConfig.getWidth(), gameConfig.getHeight());
        setPreferredSize(dimension);
        timer = new Timer(DELAY, this);
        timer.start();
        initGame();
    }

    private void initGame() {
        initLevel();
        players.forEach(Player::initPlayer);
        boardModel.getObjects().forEach(BoardObject::setRandomPosition);
        if (!timer.isRunning()) {
            timer.start();
        }
        gameStatus = GameStatus.RUNNING;
    }

    private void initLevel() {
        boardModel.init(levelScenarios.getLevel());
        levelScenarios.setNextLevel();
    }

    @Override
    public void paintComponent(Graphics g) {
        if ( debug ) {
            log.info("paintComponent, gameStatus:{}", gameStatus);
        }
        if ( GameStatus.RUNNING == gameStatus || GameStatus.PAUSED == gameStatus ) {
            super.paintComponent(g);
            doDrawing(g);
        }
    }

    public void doDrawing(Graphics g) {
        if ( debug ) {
            log.info("doDrawing, gameStatus:{}", gameStatus);
        }
        if (players.stream().anyMatch(player -> !player.getPlayerState().isInGame())) {
            gameStatus = GameStatus.STOP;
        }
        if ( gameStatus == GameStatus.RUNNING ) {
            players.forEach( objectToDraw -> objectToDraw.doDrawing(g));
            boardModel.getObjects().forEach(objectToDraw -> objectToDraw.doDrawing(g));
            if ( gameConfig.isLattice()) {
                boardGraphics.drawLattice(g);
            }
        }
        if ( gameStatus == GameStatus.STOP ) {
            boardGraphics.gameOver(g,this);
        }
        if ( gameStatus == GameStatus.PAUSED) {
            boardGraphics.gamePaused(g,this);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private Optional<BoardObject> checkCollisions(Player player) {
        return boardModel.getObjects().stream().filter(wall ->
                player.getPlayerModel().getHead().equals(wall.getPosition())
        ).findFirst();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        players.stream().filter(player -> player.getPlayerState().isInGame()).forEach(player -> {
            Optional<BoardObject> optionalObject = checkCollisions(player);
            if ( optionalObject.isPresent()) {
                if ( optionalObject.get().isEndGame() ) {
                    gameStatus = GameStatus.STOP;
                    timer.stop();
                } else {
                    optionalObject.get().action(player.getPlayerModel());
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

    private class TAdapter extends KeyAdapter {
        Board board;
        public TAdapter(Board board) {
            this.board = board;
        }
        @Override
        public void keyPressed(KeyEvent e) {
            players.forEach(player -> player.keyPressed(e));
            if ( e.getKeyCode() == KeyEvent.VK_R ) initGame();
            if ( e.getKeyCode() == KeyEvent.VK_P ) pauseLogic();
            if ( e.getKeyCode() == KeyEvent.VK_B ) debug = !debug;
            log.info("{} key, debug: {}, running:{}",(new StringBuffer()).append(e.getKeyChar()), debug, board.getTimer().isRunning());
        }
        private void pauseLogic() {
            if ( GameStatus.PAUSED == gameStatus) {
                gameStatus = GameStatus.RUNNING;
            } else {
                if (GameStatus.RUNNING == gameStatus) {
                    gameStatus = GameStatus.PAUSED;
                }
            }
        }
    }
}