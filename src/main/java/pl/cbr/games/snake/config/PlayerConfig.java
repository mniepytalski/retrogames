package pl.cbr.games.snake.config;

import lombok.Data;

@Data
public class PlayerConfig {
    private String name;
    private PositionConfig position = new PositionConfig();
    private ControlConfig control = new ControlConfig();
}
