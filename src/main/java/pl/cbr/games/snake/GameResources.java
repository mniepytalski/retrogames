package pl.cbr.games.snake;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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



        String resourcePath = "C:\\Roche\\git\\snake\\src\\main\\resources\\";
        File file = new File(resourcePath);
        System.out.println(file.getAbsoluteFile());

        ImageIcon iid = new ImageIcon(resourcePath + "dot.png");
        getResources().put("ball", iid.getImage());

        ImageIcon iia = new ImageIcon(resourcePath + "apple.png");
        getResources().put("apple", iia.getImage());

        ImageIcon iih = new ImageIcon(resourcePath + "head.png");
        getResources().put("head", iih.getImage());
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
