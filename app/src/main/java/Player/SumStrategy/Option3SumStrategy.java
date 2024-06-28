package Player.SumStrategy;

import lucky.*;
import ch.aplu.jcardgame.Card;

import java.util.List;

public class Option3SumStrategy implements SumStrategy {
    @Override
    public boolean isSumThirteen(List<Card> privateCards, List<Card> publicCards){
        int[] possibleValues1 = ((Rank)privateCards.get(0).getRank()).getPossibleSumValues();
        int[] possibleValues2 = ((Rank)privateCards.get(1).getRank()).getPossibleSumValues();
        int[] possibleValues3 = ((Rank)publicCards.get(0).getRank()).getPossibleSumValues();
        int[] possibleValues4 = ((Rank)publicCards.get(1).getRank()).getPossibleSumValues();

        return isSumThirteenRecursive(new int[][]{possibleValues1, possibleValues2,
                possibleValues3, possibleValues4}, 0, 0);
    }

    // since option3 only need to check sum of four cards, so pairs will always return false
    // -> this included in option1 & option2
    @Override
    public boolean isSumThirteenPairs(Card card1, Card card2){
        return false;
    }

    private boolean isSumThirteenRecursive(int[][] possibleValues, int index, int currentSum) {
        if (index == possibleValues.length) {
            return currentSum == LuckyThirdteen.THIRTEEN_GOAL;
        }

        for (int value : possibleValues[index]) {
            if (isSumThirteenRecursive(possibleValues, index + 1, currentSum + value)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSumThirteenFourCards(Card privateCard1, Card privateCard2, Card publicCard1, Card publicCard2){
        int[] possibleValues1 = ((Rank)privateCard1.getRank()).getPossibleSumValues();
        int[] possibleValues2 = ((Rank)privateCard2.getRank()).getPossibleSumValues();
        int[] possibleValues3 = ((Rank)publicCard1.getRank()).getPossibleSumValues();
        int[] possibleValues4 = ((Rank)publicCard2.getRank()).getPossibleSumValues();

        return isSumThirteenRecursive(new int[][]{possibleValues1, possibleValues2,
                possibleValues3, possibleValues4}, 0, 0);
    }
}
