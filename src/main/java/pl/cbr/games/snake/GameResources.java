package pl.cbr.games.snake;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameResources {

    private static volatile Map<String, Image> resources = null;

    private GameResources() {}

    public static Map<String, Image> getResources() {
        if (resources == null) {
            synchronized(GameResources.class) {
                if (resources == null) {
                    resources = new HashMap<>();
                    loadImages();
                }
            }
        }
        return resources;
    }

    private static void loadImages() {

        Class cls = null;
        try {
            cls = Class.forName("pl.cbr.games.snake.GameResources");
            ClassLoader cLoader = cls.getClassLoader();

            ImageIcon iid = new ImageIcon(cLoader.getResource("dot.png"));
            getResources().put("ball", iid.getImage());

            ImageIcon iia = new ImageIcon(cLoader.getResource("apple.png"));
            getResources().put("apple", iia.getImage());

            ImageIcon iih = new ImageIcon(cLoader.getResource("head.png"));
            getResources().put("head", iih.getImage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Image getBall() {
        return getResources().get("ball");
    }

    public static Image getApple() {
        return getResources().get("apple");
    }

    public static Image getHead() {
        return getResources().get("head");
    }
}
