package pl.cbr.games.snake;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameResources {

    private static volatile Map<String, Image> resources = null;

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

        Class cls;
        try {
            cls = Class.forName("pl.cbr.games.snake.GameResources");
            ClassLoader cLoader = cls.getClassLoader();

            ImageIcon iid0 = new ImageIcon(Objects.requireNonNull(cLoader.getResource("images/dot0.png")));
            getResources().put("ball0", iid0.getImage());

            ImageIcon iid1 = new ImageIcon(Objects.requireNonNull(cLoader.getResource("images/dot1.png")));
            getResources().put("ball1", iid1.getImage());

            ImageIcon iia = new ImageIcon(Objects.requireNonNull(cLoader.getResource("images/apple.png")));
            getResources().put("apple", iia.getImage());

            ImageIcon iih = new ImageIcon(Objects.requireNonNull(cLoader.getResource("images/head.png")));
            getResources().put("head", iih.getImage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Image getBall(int i) {
        return getResources().get("ball"+i);
    }

    public static Image getApple() {
        return getResources().get("apple");
    }

    public static Image getHead() {
        return getResources().get("head");
    }
}
