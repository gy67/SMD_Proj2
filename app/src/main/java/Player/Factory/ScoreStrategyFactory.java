package Player.Factory;

import Player.ScoreStrategy.Option1ScoreStrategy;
import Player.ScoreStrategy.Option2ScoreStrategy;
import Player.ScoreStrategy.Option3ScoreStrategy;
import Player.ScoreStrategy.ScoreStrategy;

import java.util.ArrayList;
import java.util.List;

public class ScoreStrategyFactory {
    private static ScoreStrategyFactory instance;

    private ScoreStrategyFactory() {}

    public static ScoreStrategyFactory getInstance() {
        if (instance == null) {
            instance = new ScoreStrategyFactory();
        }
        return instance;
    }

    public List<ScoreStrategy> createScoreStrategies() {
        List<ScoreStrategy> scoreStrategies = new ArrayList<ScoreStrategy>();
        scoreStrategies.add(new Option1ScoreStrategy());
        scoreStrategies.add(new Option2ScoreStrategy());
        scoreStrategies.add(new Option3ScoreStrategy());
        return scoreStrategies;
    }
}


