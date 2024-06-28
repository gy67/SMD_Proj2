package lucky;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import java.util.List;
import java.util.Random;

public class CardHandler {
    private final GameSetup gameSetup;
    private final Random random;

    public CardHandler(GameSetup gameSetup, Random random) {
        this.gameSetup = gameSetup;
        this.random = random;
    }

    public void dealACardToHand(Hand hand) {
        if (gameSetup.getPack().isEmpty()) return;
        Card dealt = randomCard(gameSetup.getPack().getCardList());
        dealt.removeFromHand(false);
        hand.insert(dealt, true);
    }

    public Card getRandomCard(Hand hand) {
        dealACardToHand(hand);
        int x = random.nextInt(hand.getCardList().size());
        return hand.getCardList().get(x);
    }

    private Card randomCard(List<Card> list) {
        int x = random.nextInt(list.size());
        return list.get(x);
    }
}
