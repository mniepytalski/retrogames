package pl.cbr.games.snake;

import lombok.Getter;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;
import pl.cbr.system.ApplicationContext;
import pl.cbr.system.config.SystemConfiguration;

import java.awt.EventQueue;
import javax.swing.JFrame;

@Getter
public class Snake extends JFrame {

    private final GameConfig gameConfig;
    private final MessagesConfig messagesConfig;

    public Snake(GameConfig gameConfig, MessagesConfig messagesConfig) {
        this.gameConfig = gameConfig;
        this.messagesConfig = messagesConfig;
        initUI();
    }

    private void initUI() {
        add(new Board(gameConfig, messagesConfig));

        setResizable(false);
        pack();

        setTitle(messagesConfig.getTitle());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();
        context.start();

        MessagesConfig messagesConfig = new MessagesConfig();
        GameConfig gameConfig = new GameConfig();
        SystemConfiguration systemConfiguration = new SystemConfiguration();
        systemConfiguration.loadConfiguration(messagesConfig);
        systemConfiguration.loadConfiguration(gameConfig);

        Snake app = new Snake(gameConfig, messagesConfig);

        EventQueue.invokeLater(() -> {
            JFrame ex = app;
            ex.setVisible(true);
        });
    }
}
