package lucky;

import Player.Player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import java.util.*;

public class AutoMovementHandler {
    private final Properties properties;
    private final GameSetup gameSetup;
    private final List<Player> players;
    private final List<List<String>> playerAutoMovements = new ArrayList<>();
    private final int[] autoIndexHands;
    private final GameLogHandler logHandler;
    private final String PLAYER = "players.";
    private final String Card_Played = ".cardsPlayed";
    private final String PrintOut1 = "cannot draw card: ";
    private final String PrintOut2 = " - hand: ";

    public AutoMovementHandler(Properties properties, GameSetup gameSetup, List<Player> players, GameLogHandler logHandler) {
        this.properties = properties;
        this.gameSetup = gameSetup;
        this.players = players;
        this.autoIndexHands = new int[players.size()];
        this.logHandler = logHandler;
        setupPlayerAutoMovements();
    }


    private void setupPlayerAutoMovements() {
        for (int i = 0; i < players.size(); i++) {
            String playerAutoMovement = properties.getProperty(PLAYER + i + Card_Played);
            if (playerAutoMovement != null) {
                List<String> movements = Arrays.asList(playerAutoMovement.split(","));
                playerAutoMovements.add(movements);
            } else {
                playerAutoMovements.add(new ArrayList<>());
            }
        }
    }


    public Card applyAutoMovement(int playerIndex, Hand hand) {
        if (gameSetup.getPack().isEmpty()) return null;
        List<String> nextPlayerMovement = playerAutoMovements.get(playerIndex);
        int nextPlayerAutoIndex = autoIndexHands[playerIndex];

        if (nextPlayerMovement.size() > nextPlayerAutoIndex) {
            String nextMovement = nextPlayerMovement.get(nextPlayerAutoIndex);
            autoIndexHands[playerIndex]++;
            return executeMovement(hand, nextMovement, playerIndex);
        }
        return null;
    }


    private Card executeMovement(Hand hand, String nextMovement, int playerIndex) {
        String[] cardStrings = nextMovement.split("-");
        String cardDealtString = cardStrings[0];
        Card dealt = gameSetup.getCardFromList(gameSetup.getPack().getCardList(), cardDealtString);
        if (dealt != null) {
            dealt.removeFromHand(false);
            hand.insert(dealt, true);
        } else {
            System.out.println(PrintOut1 + cardDealtString + PrintOut2 + hand);
        }

        if (cardStrings.length > 1) {
            String cardDiscardString = cardStrings[1];
            Card discarded = gameSetup.getCardFromList(hand.getCardList(), cardDiscardString);
            logHandler.logAutoMovement(playerIndex, dealt, discarded);
            return discarded;
        } else {
            logHandler.logAutoMovement(playerIndex, dealt, null);
            return null;
        }
    }
}
