package Player.SumStrategy;

import ch.aplu.jcardgame.Card;
import java.util.List;

public interface SumStrategy {
    boolean isSumThirteen(List<Card> privateCards, List<Card> publicCards);
    boolean isSumThirteenPairs(Card card1, Card card2);
    boolean isSumThirteenFourCards(Card privateCard1, Card privateCard2, Card publicCard1, Card publicCard2);
}
