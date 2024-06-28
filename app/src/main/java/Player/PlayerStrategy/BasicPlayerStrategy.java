package Player.PlayerStrategy;

import lucky.Rank;
import lucky.Suit;
import Player.Player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.List;

public class BasicPlayerStrategy implements PlayerStrategy {
    @Override
    public Card selectCardToDiscard(Player player, List<Card> cardsPlayed) {
        Hand hand = player.getHand();
        List<Card> cards = hand.getCardList();

        int min_score = Integer.MAX_VALUE;
        Card selected = null;

        for (Card card : cards) {
            Rank rank = (Rank) card.getRank();
            Suit suit = (Suit) card.getSuit();

            // count score
            int score = rank.getScoreCardValue() * suit.getMultiplicationFactor();
            if (score < min_score) {
                min_score = score;
                selected = card;
            }
        }
        return selected;
    }
}
