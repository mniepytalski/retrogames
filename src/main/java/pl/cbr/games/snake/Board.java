package pl.cbr.games.snake;

import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;
import pl.cbr.games.snake.player.Player;
import pl.cbr.games.snake.player.PlayerConfiguration;

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

    private GameConfig gameConfig;
    private MessagesConfig messages;

    private Timer timer;

    private final Apple apple;
    private final List<Player> players;

    private final static int DELAY = 140;

    public Board(GameConfig gameConfig, MessagesConfig messages) {
        this.gameConfig = gameConfig;
        this.messages = messages;
        players = new ArrayList<>();
        apple = new Apple(0,0, gameConfig);

        PlayerConfiguration playerConfiguration1 = new PlayerConfiguration(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT
                , KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        Player player1 = new Player(gameConfig.getPlayer1Name(), gameConfig.getPlayer1PositionX(),
                gameConfig.getPlayer1PositionY(), playerConfiguration1, gameConfig);

        PlayerConfiguration playerConfiguration2 = new PlayerConfiguration(KeyEvent.VK_A, KeyEvent.VK_D
                , KeyEvent.VK_W, KeyEvent.VK_S);
        Player player2 = new Player(gameConfig.getPlayer2Name(), gameConfig.getPlayer2PositionX(),
                gameConfig.getPlayer2PositionY(), playerConfiguration2, gameConfig);

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
        apple.locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        for ( Player player : players) {
            if (player.getState().isInGame()) {
                g.drawImage(GameResources.getApple(), apple.getX(), apple.getY(), this);
                player.doDrawing(g, this);
                Toolkit.getDefaultToolkit().sync();
            } else {
                gameOver(g);
            }
        }
    }

    private void gameOver(Graphics g) {
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(messages.getEndGame(),
                (gameConfig.getWidth() - metr.stringWidth(messages.getEndGame())) / 2,
                gameConfig.getHeight() / 2);

        String pointsTable = players.get(0).getPoints() + " : " + players.get(1).getPoints();
        g.drawString(pointsTable, 40, 40);
    }

    private void checkApple(Player player) {
        if ((player.getBoard().getX(0) == apple.getX()) && (player.getBoard().getY(0) == apple.getY())) {
            player.getBoard().incDots();
            apple.locateApple();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        players.stream().filter(player -> player.getState().isInGame()).forEach(player -> {
            checkApple(player);
            if (!player.checkCollision() ) {
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
