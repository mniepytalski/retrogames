package pl.cbr.games.snake;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;

import java.awt.*;

@Component
@Data
public class BoardGraphics {

    private final GameConfig gameConfig;
    private final MessagesConfig messages;
    private final GameResources gameResources;

    private static final String FONT_TYPE = "Courier";

    public void drawLattice(Graphics g) {
        g.setColor(Color.GRAY);
        for ( int x=gameConfig.getDotSize(); x<=gameConfig.getWidth(); x+=gameConfig.getDotSize()) {
            g.drawLine(x, 0, x, gameConfig.getHeight());
        }
        for ( int y=gameConfig.getDotSize(); y<=gameConfig.getHeight(); y+=gameConfig.getDotSize()) {
            g.drawLine(0, y, gameConfig.getWidth(), y);
        }
    }

    public void init(Board board) {
        board.addKeyListener(new BoardKeyAdapter(board));
        board.setBackground(Color.black);
        board.setFocusable(true);
        Dimension dimension = new Dimension(gameConfig.getWidth(), gameConfig.getHeight());
        board.setPreferredSize(dimension);
    }

    public void printBoard(GameStatus gameStatus, Graphics g, Board board) {
        if ( gameStatus == GameStatus.START_LOGO ) {
            printStartLogo(g,board);
        }
        if ( gameStatus == GameStatus.RUNNING ) {
            printRunningBoard(g,board);
        }
        if ( gameStatus == GameStatus.STOP ) {
            gameOver(g,board);
        }
        if ( gameStatus == GameStatus.PAUSED) {
            gamePaused(g,board);
        }
        if ( gameStatus == GameStatus.NEXT_LEVEL ) {
            nextLevel(g,board);
        }
    }

    private void printStartLogo(Graphics g, Board board) {
        g.drawImage(gameResources.getStartLogo(), 0, 0, null);
        printCenterText(g, board, Color.white, "Start game press R");
    }

    private void printRunningBoard(Graphics g, Board board) {
        board.getBoardModel().getObjects().forEach(objectToDraw -> objectToDraw.doDrawing(g));
        board.getBoardModel().getPlayers().forEach( objectToDraw -> objectToDraw.doDrawing(g));
        if ( gameConfig.isLattice()) {
            drawLattice(g);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Level "+(board.getLevelScenarios().getActualLevel()+1), 80, 14);
        g.drawString("All points "+(board.getLevelScenarios().getLevel().getPointsToFinish()), 140, 14);
    }

    private void gameOver(Graphics g, Board board) {
        printCenterText(g, board, Color.white, messages.getEndGame());
    }

    private void nextLevel(Graphics g, Board board) {
        printRunningBoard(g, board);
        String message = "You are finished level "+board.getLevelScenarios().getActualLevel()+", press R for next";
        printCenterText(g, board, Color.white, message);
    }

    private void gamePaused(Graphics g, Board board) {
        printRunningBoard(g, board);
        printCenterText(g, board, Color.cyan, "Paused");
    }

    private void printCenterText(Graphics g, Board board, Color color, String text) {
        Font small = new Font(FONT_TYPE, Font.BOLD, 24);
        FontMetrics fontMetrics = board.getFontMetrics(small);
        g.setColor(color);
        g.setFont(small);
        g.drawString(text,
                (gameConfig.getWidth() - fontMetrics.stringWidth(text)) / 2,
                gameConfig.getHeight() / 2);
    }
}
