package Player.ScoreStrategy;

import Player.SumStrategy.Option3SumStrategy;
import Player.SumStrategy.SumStrategy;
import ch.aplu.jcardgame.Card;

import java.util.List;

public class Option3ScoreStrategy implements ScoreStrategy {
    SumStrategy sumStrategy = new Option3SumStrategy();
    public int calculateScore(List<Card> privateCards, List<Card> publicCards){
        int maxScore = 0;
        Card privateCard1 = privateCards.get(0);
        Card privateCard2 = privateCards.get(1);
        Card publicCard1 = publicCards.get(0);
        Card publicCard2 = publicCards.get(1);

        if (sumStrategy.isSumThirteen(privateCards, publicCards)) {
            int score = getScorePrivateCard(privateCard1) +getScorePrivateCard(privateCard2)
                    + getScorePublicCard(publicCard1) + getScorePublicCard(publicCard2);
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }
}
