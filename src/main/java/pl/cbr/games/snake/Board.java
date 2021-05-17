package pl.cbr.games.snake;

import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.Apple;
import pl.cbr.games.snake.objects.BoardObject;
import pl.cbr.games.snake.objects.Lemon;
import pl.cbr.games.snake.objects.Wall;
import pl.cbr.games.snake.player.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.swing.JPanel;
import javax.swing.Timer;

@Component
public class Board extends JPanel implements ActionListener, Drawing {

    private final transient GameConfig gameConfig;
    private final transient BoardGraphics boardGraphics;

    private Timer timer;

    private final transient List<BoardObject> objects;
    private final transient List<Player> players;
    private GameStatus gameStatus = GameStatus.RUNNING;

    private final List<Drawing> drawingList = new ArrayList<>();

    private final static int DELAY;

    static {
        DELAY = 200;
    }

    public Board(GameConfig gameConfig, GameResources gameResources, BoardGraphics boardGraphics) {
        this.gameConfig = gameConfig;
        this.boardGraphics = boardGraphics;
        players = new ArrayList<>();
        objects = new ArrayList<>();
        this.gameConfig.getPlayers().forEach(playerConfig -> players.add(new Player(playerConfig, gameConfig, gameResources)));
        drawingList.addAll(players);

        IntStream.rangeClosed(1,gameConfig.getApples()).forEach(n -> objects.add(new Apple(gameConfig, gameResources)));
        IntStream.rangeClosed(1,20).forEach(n -> objects.add(new Wall(gameConfig, gameResources)));
        objects.add(new Lemon(gameConfig, gameResources));
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        Dimension dimension = new Dimension(gameConfig.getWidth(), gameConfig.getHeight());
        setPreferredSize(dimension);
        initGame();
    }

    private void initGame() {
        players.forEach(Player::initGame);
        objects.forEach(BoardObject::setRandomPosition);
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        if ( gameStatus.equals(GameStatus.RUNNING)) {
            super.paintComponent(g);
            doDrawing(g);
        }
    }

    public void doDrawing(Graphics g) {
        if (players.stream().anyMatch(player -> !player.getPlayerState().isInGame())) {
            gameStatus = GameStatus.STOP;
        }
        if ( gameStatus.equals(GameStatus.RUNNING)) {
            drawingList.forEach( objectToDraw -> objectToDraw.doDrawing(g));
            objects.forEach(objectToDraw -> objectToDraw.doDrawing(g));
            if ( gameConfig.isLattice()) {
                boardGraphics.drawLattice(g);
            }
        }
        if ( gameStatus.equals(GameStatus.STOP)) {
            boardGraphics.gameOver(g,this);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private Optional<BoardObject> checkCollisions(Player player) {
        return objects.stream().filter(wall ->
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
            player.move();
        });
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            players.forEach(player -> player.keyPressed(e));
        }
    }
}
