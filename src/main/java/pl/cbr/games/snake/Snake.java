package pl.cbr.games.snake;

import lombok.Getter;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;
import pl.cbr.system.ApplicationContext;

import java.awt.EventQueue;
import javax.swing.JFrame;

@Getter
public class Snake extends JFrame {

    public Snake(GameConfig gameConfig, MessagesConfig messagesConfig) {
        initUI(gameConfig, messagesConfig);
    }

    private void initUI(GameConfig gameConfig, MessagesConfig messagesConfig) {
        add(new Board(this, gameConfig, messagesConfig));

        setResizable(false);
        pack();

        setTitle(messagesConfig.getTitle());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getInstance("pl.cbr");
        GameConfig gameConfig = (GameConfig)applicationContext
                .getConfiguration("pl.cbr.games.snake.config.GameConfig");
        MessagesConfig messagesConfig = (MessagesConfig)applicationContext
                .getConfiguration("pl.cbr.games.snake.config.MessagesConfig");

        Snake app = new Snake(gameConfig, messagesConfig);

        EventQueue.invokeLater(() -> {
            JFrame ex = app;
            ex.setVisible(true);
        });
    }
}
