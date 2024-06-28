package Player.PlayerStrategy;

import Player.Player;
import ch.aplu.jcardgame.Card;

import java.util.List;

public interface PlayerStrategy {
    Card selectCardToDiscard(Player player, List<Card> cardsPlayed);
}
