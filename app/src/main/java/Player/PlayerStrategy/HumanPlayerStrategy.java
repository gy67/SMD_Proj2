package Player.PlayerStrategy;

import Player.Player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;
import ch.aplu.jcardgame.Hand;

import java.util.List;
import java.util.concurrent.CountDownLatch;


public class HumanPlayerStrategy implements PlayerStrategy {
    private Card selected;
    private CardListener cardListener;
    private CountDownLatch latch;

    public void initCardListener(Hand hand) {
        // set card listener
        cardListener = new CardAdapter() {
            public void leftDoubleClicked(Card card) {
                selected = card;
                hand.setTouchEnabled(false);
                latch.countDown(); // release lock
            }
        };
        hand.addCardListener(cardListener);
    }

    @Override
    public Card selectCardToDiscard(Player player, List<Card> cardsPlayed) {
        Hand hand = player.getHand();
        latch = new CountDownLatch(1); // initialize a lock

        // setup touch on
        hand.setTouchEnabled(true);
        selected = null;

        // wait player to choose a card to discard
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        return selected;
    }
}
