package pl.cbr.games.snake;

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

    private final int DELAY = 140;

    Apple apple = new Apple();
    List<Player> players = new ArrayList<>();

    private Timer timer;

    public Board() {
        PlayerConfiguration playerConfiguration1 = new PlayerConfiguration(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT
                , KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        Player player1 = new Player(1, "mario1", 50, 50, playerConfiguration1);

        PlayerConfiguration playerConfiguration2 = new PlayerConfiguration(KeyEvent.VK_A, KeyEvent.VK_D
                , KeyEvent.VK_W, KeyEvent.VK_S);
        Player player2 = new Player(2, "michal", 200, 200, playerConfiguration2);

        players.add(player1);
        players.add(player2);

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(BoardConfiguration.B_WIDTH, BoardConfiguration.B_HEIGHT));
        initGame();
    }

    private void initGame() {
        players.stream().forEach(Player::initGame);
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

        String msg = "Koniec Gry";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (BoardConfiguration.B_WIDTH - metr.stringWidth(msg)) / 2, BoardConfiguration.B_HEIGHT / 2);

        
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
            players.stream().forEach(player -> player.keyPressed(e));
        }
    }
}
