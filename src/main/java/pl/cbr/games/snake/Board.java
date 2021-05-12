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
import javax.swing.JPanel;
import javax.swing.Timer;

@Component
public class Board extends JPanel implements ActionListener, Drawing {

    private final GameConfig gameConfig;
    private final MessagesConfig messages;

    private Timer timer;

    private final transient Apple apple;
    private final transient List<Player> players;
    private GameStatus gameStatus = GameStatus.RUNNING;

    private List<Drawing> drawingList = new ArrayList<>();

    private final static int DELAY = 200;

    public Board(GameConfig gameConfig, MessagesConfig messages ) {
        this.gameConfig = gameConfig;
        this.messages = messages;
        players = new ArrayList<>();
        for (PlayerConfig playerConfig: this.gameConfig.getPlayers()) {
            Player player = new Player(playerConfig, gameConfig);
            players.add(player);
            drawingList.add(player);
        }
        apple = new Apple(gameConfig);;
        drawingList.add(apple);
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
        }
        if ( gameStatus.equals(GameStatus.STOP)) {
            gameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
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
