package pl.cbr.games.snake.levels;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class LevelScenarios {
    private final List<Level> levels;
    private int actualLevel;

    public LevelScenarios() {
        actualLevel = 0;
        this.levels = new ArrayList<>();
        levels.add((new Level(100,1,0,0)));
        levels.add((new Level(100,4,1,0)));
        levels.add((new Level(100,5,5,0)));
        levels.add((new Level(100,5,10,1)));
        levels.add((new Level(100,5,20,2)));

        levels.add((new Level(100,10,20,3)));
        levels.add((new Level(100,15,20,4)));
        levels.add((new Level(100,20,20,5)));
        levels.add((new Level(100,25,20,6)));
        levels.add((new Level(100,20,20,7)));
        levels.add((new Level(100,35,20,8)));

        levels.add((new Level(100,250,0,5)));

        levels.add((new Level(100,5,100,15)));
    }

    public void setNextLevel() {
        actualLevel++;
    }

    public Level getLevel() {
        if (actualLevel>=levels.size()) {
            actualLevel = levels.size()-1;
        }
        return levels.get(actualLevel);
    }
}
