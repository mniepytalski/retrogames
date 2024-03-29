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
            resources.put("ball0", getImage(cLoader, resourcesConfig.getBall0()));
            resources.put("ball1", getImage(cLoader, resourcesConfig.getBall1()));
            resources.put("apple", getImage(cLoader, resourcesConfig.getApple()));
            resources.put("head", getImage(cLoader,resourcesConfig.getHead()));
            resources.put("wall",getImage(cLoader,resourcesConfig.getWall()));
            resources.put("lemon",getImage(cLoader,resourcesConfig.getLemon()));
            resources.put("startLogo", getBufferedImage(cLoader,resourcesConfig.getStartLogo()));
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

    private Image getImage(ClassLoader cLoader, String resourceName) {
        return (new ImageIcon(Objects.requireNonNull(
                cLoader.getResource(IMAGES_DIR+resourceName)))).getImage();
    }

    private BufferedImage getBufferedImage(ClassLoader cLoader, String resourceImage) throws ClassNotFoundException, IOException {
        return ImageIO.read(new File(Objects.requireNonNull(
                cLoader.getResource(IMAGES_DIR + resourceImage)).getFile()));
    }
}
