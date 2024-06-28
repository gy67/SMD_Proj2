package Player.PlayerStrategy;

import lucky.LuckyThirdteen;
import Player.Player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.List;

public class RandomPlayerStrategy implements PlayerStrategy {
    @Override
    public Card selectCardToDiscard(Player player, List<Card> cardsPlayed) {
        // Randomly selects a card to discard
        Hand hand = player.getHand();
        List<Card> cards = hand.getCardList();
        int random_index = LuckyThirdteen.random.nextInt(cards.size());

        return hand.get(random_index);
    }
}
