package pl.cbr.games.snake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.ResourcesConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
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
            Class<?> cls = Class.forName("pl.cbr.games.snake.GameResources");
            var cLoader = cls.getClassLoader();

            resources.put("ball0", (new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getBall0())))).getImage());

            resources.put("ball1", (new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getBall1())))).getImage());

            resources.put("apple", (new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getApple())))).getImage());

            resources.put("head", (new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getHead())))).getImage());

            resources.put("wall",(new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getWall())))).getImage());

            resources.put("lemon",(new ImageIcon(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR+resourcesConfig.getLemon())))).getImage());

            BufferedImage img = ImageIO.read(new File(Objects.requireNonNull(
                    cLoader.getResource(IMAGES_DIR + resourcesConfig.getStartLogo())).getFile()));
                resources.put("startLogo", img);

        } catch (ClassNotFoundException | IOException  e) {
            log.error("",e);
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

    public Image getStartLogo() {
        return resources.get("startLogo");
    }
}
