package pl.cbr.games.snake;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;
import pl.cbr.games.snake.levels.Level;
import pl.cbr.games.snake.objects.Apple;
import pl.cbr.games.snake.objects.BoardObject;
import pl.cbr.games.snake.objects.Lemon;
import pl.cbr.games.snake.objects.Wall;
import pl.cbr.games.snake.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Data
@Component
public class BoardModel {

    private final GameConfig gameConfig;
    private final GameResources gameResources;
    private final Rectangle board;

    private final List<BoardObject> objects;
    private final List<Player> players;

    public BoardModel(GameConfig gameConfig, GameResources gameResources) {
        this.gameConfig = gameConfig;
        this.gameResources = gameResources;
        objects = new ArrayList<>();
        players = new ArrayList<>();

        board = new Rectangle(new Point(0,0),
                (new Point(gameConfig.getWidth(),gameConfig.getHeight())).division(gameConfig.getDotSize()));
    }

    public void init(Level level) {
        objects.clear();
        IntStream.rangeClosed(1, level.getApples()).forEach(n -> getObjects().add(new Apple(gameConfig, gameResources,this)));
        IntStream.rangeClosed(1, level.getWalls()).forEach(n -> getObjects().add(new Wall(gameConfig, gameResources, this)));
        IntStream.rangeClosed(1, level.getLemons()).forEach(n -> getObjects().add(new Lemon(gameConfig, gameResources, this)));

        objects.forEach(BoardObject::setRandomPosition);
        tryingToChangeDuplicatePosition();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean collisionWithPoint() {
        List<Point> positions = objects.stream().map(BoardObject::getPosition).collect(Collectors.toList());
        players.forEach(player -> positions.addAll(player.getPlayerModel().getView()));
        Set<Point> duplicates = positions.stream().filter(i -> Collections.frequency(positions, i) > 1)
                .collect(Collectors.toSet());
        return !duplicates.isEmpty();
    }

    private void tryingToChangeDuplicatePosition() {
        int attempt = 1;
        int duplicates = getDuplicatesAndChangePosition();
        for ( int x=0; x<200; x++ ) {
            if (duplicates > 0) {
                getDuplicatesAndChangePosition();
                ++attempt;
            } else {
                break;
            }
        }
        if ( duplicates>0 ) {
            log.info("{} init objects:{}, duplicates:{}", attempt, objects.size(), duplicates);
        }
    }

    private int getDuplicatesAndChangePosition() {
        List<List<BoardObject>> duplicates = detectDuplicates();
        for (List<BoardObject> dup : duplicates ) {
            int x = 0;
            for ( BoardObject object: dup ) {
                if ( x++ > 0 ) {
                    object.setRandomPosition();
                }
            }
        }
        return duplicates.size();
    }

    private List<List<BoardObject>> detectDuplicates() {
        Map<Point, List<BoardObject>> maps = objects.stream().collect(Collectors.groupingBy(BoardObject::getPosition));
        return maps.values().stream().filter(list -> list.size()>1).collect(Collectors.toList());
    }
}
