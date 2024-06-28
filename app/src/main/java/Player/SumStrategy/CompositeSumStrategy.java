package Player.SumStrategy;

import ch.aplu.jcardgame.Card;
import java.util.ArrayList;
import java.util.List;


public class CompositeSumStrategy implements SumStrategy {
    private List<SumStrategy> strategies = new ArrayList<>();

    public void addStrategy(SumStrategy strategy) {
        strategies.add(strategy);
    }

    public void removeStrategy(SumStrategy strategy) {
        strategies.remove(strategy);
    }

    @Override
    public boolean isSumThirteen(List<Card> privateCards, List<Card> publicCards) {
        for (SumStrategy strategy : strategies) {
            if (strategy.isSumThirteen(privateCards, publicCards)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSumThirteenPairs(Card card1, Card card2) {
        return false;
    }

    @Override
    public boolean isSumThirteenFourCards(Card privateCard1, Card privateCard2, Card publicCard1, Card publicCard2){
        return false;
    }
}
