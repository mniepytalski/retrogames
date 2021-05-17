package pl.cbr.games.snake;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;

import javax.swing.*;
import java.awt.*;

@Component
@Data
public class BoardGraphics {

    private final GameConfig gameConfig;
    private final MessagesConfig messages;

    public void drawLattice(Graphics g) {
        g.setColor(Color.GRAY);
        for ( int x=gameConfig.getDotSize(); x<=gameConfig.getWidth(); x+=gameConfig.getDotSize()) {
            g.drawLine(x, 0, x, gameConfig.getHeight());
        }
        for ( int y=gameConfig.getDotSize(); y<=gameConfig.getHeight(); y+=gameConfig.getDotSize()) {
            g.drawLine(0, y, gameConfig.getWidth(), y);
        }
    }

    public void gameOver(Graphics g, JPanel jPanel) {
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = jPanel.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(messages.getEndGame(),
                (gameConfig.getWidth() - fontMetrics.stringWidth(messages.getEndGame())) / 2,
                gameConfig.getHeight() / 2);

        g.drawString(messages.getEndGame(), 40, 40);
    }
}
