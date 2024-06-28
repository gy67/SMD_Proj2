package Player.ScoreStrategy;

import lucky.*;
import ch.aplu.jcardgame.Card;
import java.util.List;

import static lucky.Suit.PUBLIC_CARD_MULTIPLICATION_FACTOR;


public interface ScoreStrategy {
    int calculateScore(List<Card> privateCards, List<Card> publicCards);

    default int getScorePrivateCard(Card card) {
        Rank rank = (Rank) card.getRank();
        Suit suit = (Suit) card.getSuit();
        return rank.getScoreCardValue() * suit.getMultiplicationFactor();
    }

    default int getScorePublicCard(Card card) {
        Rank rank = (Rank) card.getRank();
        return rank.getScoreCardValue() * PUBLIC_CARD_MULTIPLICATION_FACTOR;
    }
}
