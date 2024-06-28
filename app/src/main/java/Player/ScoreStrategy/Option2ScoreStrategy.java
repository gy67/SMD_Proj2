package Player.ScoreStrategy;

import Player.SumStrategy.Option2SumStrategy;
import Player.SumStrategy.SumStrategy;
import ch.aplu.jcardgame.Card;

import java.util.List;

public class Option2ScoreStrategy implements ScoreStrategy {
    private SumStrategy sumStrategy = new Option2SumStrategy();
    @Override
    public int calculateScore(List<Card> privateCards, List<Card> publicCards) {
        int maxScore = 0;

        for (Card privateCard : privateCards) {
            for (Card publicCard : publicCards) {
                if (sumStrategy.isSumThirteenPairs(privateCard, publicCard)) {
                    int score = getScorePrivateCard(privateCard) + getScorePublicCard(publicCard);
                    maxScore = Math.max(maxScore, score);
                }
            }
        }
        return maxScore;
    }
}
