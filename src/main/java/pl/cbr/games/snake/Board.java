package pl.cbr.games.snake;

import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;
import pl.cbr.games.snake.objects.Apple;
import pl.cbr.games.snake.objects.BoardObject;
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

    private final GameConfig gameConfig;
    private final BoardGraphics boardGraphics;

    private Timer timer;

    private final transient List<Apple> apples;
    private final transient List<Player> players;
    private final transient List<Wall> walls;
    private GameStatus gameStatus = GameStatus.RUNNING;

    private List<Drawing> drawingList = new ArrayList<>();

    private final static int DELAY = 200;

    public Board(GameConfig gameConfig, MessagesConfig messages, GameResources gameResources, BoardGraphics boardGraphics) {
        this.gameConfig = gameConfig;
        this.boardGraphics = boardGraphics;
        players = new ArrayList<>();
        apples = new ArrayList<>();
        walls = new ArrayList<>();
        this.gameConfig.getPlayers().forEach(playerConfig -> players.add(new Player(playerConfig, gameConfig, gameResources)));
        players.forEach(player -> drawingList.add(player));
        IntStream.rangeClosed(1,gameConfig.getApples()).forEach(n -> apples.add(new Apple(gameConfig, gameResources)));
        apples.forEach(apple -> drawingList.add(apple));

        IntStream.rangeClosed(1,20).forEach(n -> walls.add(new Wall(gameConfig, gameResources)));
        walls.forEach(wall -> drawingList.add(wall));

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
        apples.forEach(Apple::setRandomPosition);
        walls.forEach(Wall::setRandomPosition);
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
        if (players.stream().filter(player -> !player.getPlayerState().isInGame()).findFirst().isPresent() ) {
            gameStatus = GameStatus.STOP;
        }

        if ( gameStatus.equals(GameStatus.RUNNING)) {
            drawingList.forEach( objectToDraw -> objectToDraw.doDrawing(g));
            if ( gameConfig.isLattice()) {
                boardGraphics.drawLattice(g);
            }
        }
        if ( gameStatus.equals(GameStatus.STOP)) {
            boardGraphics.gameOver(g,this);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void checkApple(Player player) {
        apples.forEach(apple -> {
                    if (player.getPlayerModel().get(0).equals(apple.getPosition())) {
                        player.getPlayerModel().addLength(5);
                        apple.setRandomPosition();
                    }
                }
        );
    }

    private boolean checkCollisions(Player player) {
        Optional<Wall> wallOptional = walls.stream().filter(wall ->
            player.getPlayerModel().getHead().equals(wall.getPosition())
        ).findFirst();
        return wallOptional.isPresent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        players.stream().filter(player -> player.getPlayerState().isInGame()).forEach(player -> {
            checkApple(player);
            if (player.checkCollision() || checkCollisions(player)) {
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
