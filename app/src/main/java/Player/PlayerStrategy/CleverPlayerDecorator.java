package Player.PlayerStrategy;

import Player.Player;
import ch.aplu.jcardgame.Card;
import java.util.List;

public class CleverPlayerDecorator implements PlayerStrategy {
    protected PlayerStrategy baseStrategy;

    public CleverPlayerDecorator(PlayerStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    @Override
    public Card selectCardToDiscard(Player player, List<Card> cardsPlayed) {
        return baseStrategy.selectCardToDiscard(player, cardsPlayed);
    }
}
