package pl.cbr.games.snake;

import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.ResourcesConfig;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class GameResources {

    private final Map<String, Image> resources;
    private final ResourcesConfig resourcesConfig;

    private static final String IMAGES_DIR = "images/";

    public GameResources(ResourcesConfig resourcesConfig) {
        this.resourcesConfig = resourcesConfig;
        resources = new HashMap<>();
        loadImages();
    }

    private void loadImages() {

        try {
            Class cls = Class.forName("pl.cbr.games.snake.GameResources");
            ClassLoader cLoader = cls.getClassLoader();

            ImageIcon iid0 = new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getBall0())));
            resources.put("ball0", iid0.getImage());

            ImageIcon iid1 = new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getBall1())));
            resources.put("ball1", iid1.getImage());

            ImageIcon iia = new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getApple())));
            resources.put("apple", iia.getImage());

            ImageIcon iih = new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getHead())));
            resources.put("head", iih.getImage());

            resources.put("wall",(new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getWall())))).getImage());

            resources.put("lemon",(new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getLemon())))).getImage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getBall(int i) {
        return resources.get("ball"+i);
    }

    public Image getApple() {
        return resources.get("apple");
    }

    public Image getHead() {
        return resources.get("head");
    }

    public Image getWall() {
        return resources.get("wall");
    }

    public Image getLemon() {
        return resources.get("lemon");
    }
}
