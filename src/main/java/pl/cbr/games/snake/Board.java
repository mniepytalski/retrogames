package pl.cbr.games.snake;

import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.player.Player;
import pl.cbr.games.snake.player.PlayerControlConfiguration;

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
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final GameConfig gameConfig;
    private final MessagesConfig messages;
    Snake snake;

    private Timer timer;

    private final Apple apple;
    private final List<Player> players;
    private GameStatus gameStatus = GameStatus.RUNNING;

    private final static int DELAY = 140;

    public Board(Snake snake, GameConfig gameConfig, MessagesConfig messages) {
        this.gameConfig = gameConfig;
        this.messages = messages;
        players = new ArrayList<>();
        apple = new Apple(gameConfig);
        this.snake = snake;

        PlayerControlConfiguration playerControlConfiguration1 = new PlayerControlConfiguration(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT
                , KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        Player player1 = new Player(gameConfig.getPlayer1Name(), new Point(gameConfig.getPlayer1PositionX(),
                gameConfig.getPlayer1PositionY()), playerControlConfiguration1, gameConfig);

        PlayerControlConfiguration playerControlConfiguration2 = new PlayerControlConfiguration(KeyEvent.VK_A, KeyEvent.VK_D
                , KeyEvent.VK_W, KeyEvent.VK_S);
        Player player2 = new Player(gameConfig.getPlayer2Name(), new Point(gameConfig.getPlayer2PositionX(),
                gameConfig.getPlayer2PositionY()), playerControlConfiguration2, gameConfig);

        players.add(player1);
        players.add(player2);

        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(gameConfig.getWidth(), gameConfig.getHeight()));
        initGame();
    }

    private void initGame() {
        players.forEach(Player::initGame);
        apple.setRandomPosition();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void stopGame() {
        gameStatus = GameStatus.STOP;
        timer.stop();
        snake.setTitle("XoXoXoX");
    }

    @Override
    public void paintComponent(Graphics g) {
        if ( gameStatus.equals(GameStatus.RUNNING)) {
            super.paintComponent(g);
            doDrawing(g);
        }
    }

    private void doDrawing(Graphics g) {
        if ( gameStatus.equals(GameStatus.RUNNING)) {
            for (Player player : players) {
                if (player.getPlayerState().isInGame()) {
                    g.drawImage(GameResources.getApple(), apple.getPosition().getX(), apple.getPosition().getY(), this);
                    player.doDrawing(g, this);
                    Toolkit.getDefaultToolkit().sync();
                } else {
                    gameOver(g);
                }
            }
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

        String pointsTable = "Nie ma punktow, koniec gry";
        g.drawString(pointsTable, 40, 40);
    }

    private void checkApple(Player player) {
        if ( player.getPlayerModel().get(0).equals(apple.getPosition())) {
            player.getPlayerModel().addLength(5);
            apple.setRandomPosition();
        }
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
