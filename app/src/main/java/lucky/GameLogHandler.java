package lucky;

import ch.aplu.jcardgame.Card;
import java.util.List;
import java.util.stream.Collectors;

public class GameLogHandler {
    private StringBuilder logResult = new StringBuilder();
    private final String PLAYER = "P";
    private final String END_GAME = "EndGame:";
    private final String WINNER = "Winners:";
    private final String SCORE = "Score:";
    private final String ROUND = "Round";



    public void addCardPlayedToLog(int player, List<Card> cards) {
        if (cards.size() < 2) {
            return;
        }
        logResult.append(PLAYER + player + "-");

        for (int i = 0; i < cards.size(); i++) {
            Rank cardRank = (Rank) cards.get(i).getRank();
            Suit cardSuit = (Suit) cards.get(i).getSuit();
            logResult.append(cardRank.getRankCardLog() + cardSuit.getSuitShortHand());
            if (i < cards.size() - 1) {
                logResult.append("-");
            }
        }
        logResult.append(",");
    }

    public void addRoundInfoToLog(int roundNumber) {
        logResult.append(ROUND + roundNumber + ":");
    }

    public void addEndOfRoundToLog(int[] scores) {
        logResult.append(SCORE);
        for (int score : scores) {
            logResult.append(score + ",");
        }
        logResult.append("\n");
    }

    public void addEndOfGameToLog(int[] scores, List<Integer> winners) {
        logResult.append(END_GAME);
        for (int score : scores) {
            logResult.append(score + ",");
        }
        logResult.append("\n");
        logResult.append(WINNER + String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toList())));
    }

    public void logAutoMovement(int playerIndex, Card dealt, Card discarded) {
        logResult.append(PLAYER + playerIndex + "-" + dealt + (discarded != null ? "-" + discarded : "") + ",");
    }

    public String getLogResult() {
        return logResult.toString();
    }
}
