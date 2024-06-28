package lucky;

import Player.Player;
import ch.aplu.jcardgame.Card;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ScoreManager {
    private final int nbPlayers;
    private final int[] scores;
    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            new Location(575, 575)
    };;
    private final Actor[] scoreActors;
    private final String FONT_NAME = "Arial";
    private final int FONT_SIZE = 36;
    private final Font bigFont = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
    private final Color bgColor = Color.BLACK;

    public ScoreManager(int nbPlayers) {
        this.nbPlayers = nbPlayers;
        this.scores = new int[nbPlayers];
        this.scoreActors = new Actor[nbPlayers];
        initScores();
    }


    public void initScores() {
        Arrays.fill(scores, 0);
        for (int i = 0; i < nbPlayers; i++) {
            String text = "[" + scores[i] + "]";
            scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
        }
    }


    public void updateScore(LuckyThirdteen game, int player) {
        game.removeActor(scoreActors[player]);
        int displayScore = Math.max(scores[player], 0);
        String text = "P" + player + "[" + displayScore + "]";
        scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
        game.addActor(scoreActors[player], scoreLocations[player]);
    }


    public void calculateScoreEndOfRound(List<Player> players, List<Boolean> isThirteenChecks, List<Card> publicCards) {
        List<Integer> indexesWithThirteen = new ArrayList<>();
        for (int i = 0; i < isThirteenChecks.size(); i++) {
            if (isThirteenChecks.get(i)) {
                indexesWithThirteen.add(i);
            }
        }
        long countTrue = indexesWithThirteen.size();
        Arrays.fill(scores, 0);
        if (countTrue == 1) {
            int winnerIndex = indexesWithThirteen.get(0);
            scores[winnerIndex] = 100;
        } else if (countTrue > 1) {
            for (Integer thirteenIndex : indexesWithThirteen) {
                scores[thirteenIndex] = players.get(thirteenIndex).calculateMaxScoreForThirteen(publicCards);
            }
        } else {
            for (int i = 0; i < scores.length; i++) {
                scores[i] = players.get(i).calculatePrivateCardsScore();
            }
        }
    }

    public int[] getScores() {
        return scores;
    }
}