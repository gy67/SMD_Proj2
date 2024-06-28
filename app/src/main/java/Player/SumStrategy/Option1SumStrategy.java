package Player.SumStrategy;

import lucky.LuckyThirdteen;
import lucky.*;
import ch.aplu.jcardgame.Card;

import java.util.List;

public class Option1SumStrategy implements SumStrategy {

    @Override
    public boolean isSumThirteen(List<Card> privateCards, List<Card> publicCards){
        Card card1 = privateCards.get(0);
        Card card2 = privateCards.get(1);

        return isSumThirteenPairs(card1, card2);
    }

    @Override
    public boolean isSumThirteenPairs(Card card1, Card card2){
        Rank rank1 = (Rank) card1.getRank();
        Rank rank2 = (Rank) card2.getRank();

        return isThirteenFromPossibleValues(rank1.getPossibleSumValues(), rank2.getPossibleSumValues());
    }

    private boolean isThirteenFromPossibleValues(int[] possibleValue1, int[] possibleValue2){
        for (int value1: possibleValue1){
            for (int value2: possibleValue2){
                if (value1 + value2 == LuckyThirdteen.THIRTEEN_GOAL){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isSumThirteenFourCards(Card privateCard1, Card privateCard2, Card publicCard1, Card publicCard2){
        return false;
    }
}
