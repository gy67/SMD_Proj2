package Player.ScoreStrategy;

import ch.aplu.jcardgame.Card;
import java.util.ArrayList;
import java.util.List;

public class CompositeScoreStrategy implements ScoreStrategy {
    private List<ScoreStrategy> strategies = new ArrayList<>();

    public void addStrategy(ScoreStrategy strategy) {
        strategies.add(strategy);
    }

    public void removeStrategy(ScoreStrategy strategy) {
        strategies.remove(strategy);
    }

    @Override
    public int calculateScore(List<Card> privateCards, List<Card> publicCards) {
        int maxScore = 0;
        for (ScoreStrategy strategy : strategies) {
            int score = strategy.calculateScore(privateCards, publicCards);
            if (score > maxScore) {
                maxScore = score;
            }
        }
        return maxScore;
    }
}
