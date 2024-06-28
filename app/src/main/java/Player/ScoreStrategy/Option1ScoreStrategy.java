package Player.ScoreStrategy;

import Player.SumStrategy.Option1SumStrategy;
import Player.SumStrategy.SumStrategy;
import ch.aplu.jcardgame.Card;

import java.util.List;

public class Option1ScoreStrategy implements ScoreStrategy {
    private SumStrategy sumStrategy = new Option1SumStrategy();
    @Override
    public int calculateScore(List<Card> privateCards, List<Card> publicCards) {
        int maxScore = 0;
        Card privateCard1 = privateCards.get(0);
        Card privateCard2 = privateCards.get(1);

        if (sumStrategy.isSumThirteenPairs(privateCard1, privateCard2)) {
            int score = getScorePrivateCard(privateCard1) + getScorePrivateCard(privateCard2);
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }
}
