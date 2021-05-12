package pl.cbr.games.snake;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.awt.EventQueue;
import javax.swing.JFrame;

@SpringBootApplication
public class Snake extends JFrame {

    private final Board board;

    public Snake(Board board) {
        this.board = board;
        initUI();
    }

    private void initUI() {
        add(board);
        setResizable(false);
        pack();
        setTitle("Title");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        var ctx = new SpringApplicationBuilder(Snake.class)
                .headless(false).run(args);
        EventQueue.invokeLater(() -> {
            var ex = ctx.getBean(Snake.class);
            ex.setVisible(true);
        });
    }
}
