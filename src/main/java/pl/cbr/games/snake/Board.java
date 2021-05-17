package pl.cbr.games.snake;

import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.player.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.JPanel;
import javax.swing.Timer;

@Component
public class Board extends JPanel implements ActionListener, Drawing {

    private final GameConfig gameConfig;
    private final MessagesConfig messages;

    private Timer timer;

    private final transient List<Apple> apples;
    private final transient List<Player> players;
    private GameStatus gameStatus = GameStatus.RUNNING;

    private List<Drawing> drawingList = new ArrayList<>();

    private final static int DELAY = 200;

    public Board(GameConfig gameConfig, MessagesConfig messages, GameResources gameResources) {
        this.gameConfig = gameConfig;
        this.messages = messages;
        players = new ArrayList<>();
        this.gameConfig.getPlayers().forEach(playerConfig -> players.add(new Player(playerConfig, gameConfig, gameResources)));
        players.forEach(player -> drawingList.add(player));
        apples = new ArrayList<>();
        IntStream.iterate(1, i -> i++).limit(gameConfig.getApples()).forEach(n -> apples.add(new Apple(gameConfig, gameResources)));
        apples.forEach(apple -> drawingList.add(apple));
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
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void stopGame() {
        gameStatus = GameStatus.STOP;
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
                drawLattice(g);
            }
        }
        if ( gameStatus.equals(GameStatus.STOP)) {
            gameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawLattice(Graphics g) {
        g.setColor(Color.GRAY);
        for ( int x=gameConfig.getDotSize(); x<=gameConfig.getWidth(); x+=gameConfig.getDotSize()) {
            g.drawLine(x, 0, x, gameConfig.getHeight());
        }
        for ( int y=gameConfig.getDotSize(); y<=gameConfig.getHeight(); y+=gameConfig.getDotSize()) {
            g.drawLine(0, y, gameConfig.getWidth(), y);
        }
    }

    private void gameOver(Graphics g) {
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(messages.getEndGame(),
                (gameConfig.getWidth() - fontMetrics.stringWidth(messages.getEndGame())) / 2,
                gameConfig.getHeight() / 2);

        g.drawString(messages.getEndGame(), 40, 40);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        players.stream().filter(player -> player.getPlayerState().isInGame()).forEach(player -> {
            checkApple(player);
            if (!player.checkCollision() ) {
                stopGame();
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
