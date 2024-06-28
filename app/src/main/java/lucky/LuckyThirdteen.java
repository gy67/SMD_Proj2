package lucky;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import Player.*;

@SuppressWarnings("serial")
public class LuckyThirdteen extends CardGame {
    private final String winText1 = "Game over. Winner is player: ";
    private final String winText2 = "Game Over. Drawn winners are players: ";
    private final String gameOver_gif = "sprites/gameover.gif";
    static public final int seed = 30008;
    public static final Random random = new Random(seed);
    private Properties properties;
    private GameLogHandler logHandler;
    private AutoMovementHandler autoMovementHandler;
    private ScoreManager scoreManager;
    private CardHandler cardHandler;
    private List<Player> players;

    private final String version = "1.0";
    private final String Title = "LuckyThirteen (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)";
    private final String Initializing_Info = "Initializing...";
    private final String PLAYER = "Player ";
    private final String HUMAN = "human";
    private final String PlayerStatus = " is playing. Please double click on a card to discard";
    private final String PlayerStatusText = " thinking...";
    private final String ISAUTO = "isAuto";
    private final String Thinking_Key = "thinkingTime";
    private final String Thinking_DefaultValue = "200";
    private final String Delay_Key = "delayTime";
    private final String Delay_DefaultValue = "50";
    public final int nbPlayers = 4;
    public static final int THIRTEEN_GOAL = 13;
    private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
    private GameSetup gameSetup;
    private PlayerManager playerManager;
    private Card selected;

    public LuckyThirdteen(Properties properties) {
        super(700, 700, 30);
        this.properties = properties;
        isAuto = Boolean.parseBoolean(properties.getProperty(ISAUTO));
        thinkingTime = Integer.parseInt(properties.getProperty(Thinking_Key, Thinking_DefaultValue));
        delayTime = Integer.parseInt(properties.getProperty(Delay_Key, Delay_DefaultValue));

        playerManager = new PlayerManager(properties);
        gameSetup = new GameSetup(this, properties, deck);
        players = playerManager.getPlayers();
        logHandler = new GameLogHandler();
        autoMovementHandler = new AutoMovementHandler(properties, gameSetup, players, logHandler);

        scoreManager = new ScoreManager(nbPlayers);
        cardHandler = new CardHandler(gameSetup, random);
    }

    private Actor[] scoreActors = {null, null, null, null};

    private final Location textLocation = new Location(350, 450);
    private int thinkingTime = 2000;
    private int delayTime = 600;
    private Hand[] hands;
    public void setStatus(String string) {
        setStatusText(string);
    }
    public  List<Card> publicCards;
    private boolean isAuto = false;
    Font bigFont = new Font("Arial", Font.BOLD, 36);

    private void calculateScoreEndOfRound() {
        List<Boolean> isThirteenChecks = Arrays.asList(false, false, false, false);
        for (int i = 0; i < hands.length; i++) {
            isThirteenChecks.set(i, isThirteen(i));
        }
        scoreManager.calculateScoreEndOfRound(players, isThirteenChecks, publicCards);
    }

    private void updateScore(int player) {
        scoreManager.updateScore(this, player);
    }

    private void initScores() {
        scoreManager.initScores();
    }

    private void initScore() {
        scoreManager.initScores();
        for (int i = 0; i < nbPlayers; i++) {
            updateScore(i);
        }
    }

    public Card getRandomCard(Hand hand) {
        return cardHandler.getRandomCard(hand);
    }

    private boolean isThirteen(int playerIndex) {
        Player player = players.get(playerIndex);
        return player.isSumThirteen(publicCards);
    }


    private void dealACardToHand(Hand hand) {
        cardHandler.dealACardToHand(hand);
    }


    private void playGame() {
        int winner = 0;
        int roundNumber = 1;
        for (int i = 0; i < nbPlayers; i++) updateScore(i);

        List<Card> cardsPlayed = new ArrayList<>();
        logHandler.addRoundInfoToLog(roundNumber);

        int nextPlayer = 0;
        while (roundNumber <= 4) {
            selected = null;
            boolean finishedAuto = false;

            if (isAuto) {
                selected = autoMovementHandler.applyAutoMovement(nextPlayer, hands[nextPlayer]);
                delay(delayTime);
                if (selected != null) {
                    selected.removeFromHand(true);
                } else {
                    selected = getRandomCard(hands[nextPlayer]);
                    selected.removeFromHand(true);
                }
                finishedAuto = (selected == null);
            }

            Player currentPlayer = players.get(nextPlayer);
            Hand currentHand = currentPlayer.getHand();
            if (!isAuto || finishedAuto) {
                if (currentPlayer.getType().equals(HUMAN)) {
                    setStatus(PLAYER + nextPlayer + PlayerStatus);
                    selected = null;

                    // Deal a card to the human player's hand
                    dealACardToHand(currentHand);

                    selected = currentPlayer.playTurn(cardsPlayed);

                    while (selected == null) {
                        delay(delayTime);
                    }

                    selected.removeFromHand(true);
                } else {
                    setStatusText(PLAYER + nextPlayer + PlayerStatusText);
                    dealACardToHand(hands[nextPlayer]);
                    delay(thinkingTime);
                    selected = currentPlayer.playTurn(cardsPlayed);
                    selected.removeFromHand(true);
                }
            }

            logHandler.addCardPlayedToLog(nextPlayer, hands[nextPlayer].getCardList());
            if (selected != null) {
                cardsPlayed.add(selected);
                selected.setVerso(false);  // In case it is upside down
                delay(delayTime);
                // End Follow
            }

            nextPlayer = (nextPlayer + 1) % nbPlayers;

            if (nextPlayer == 0) {
                roundNumber++;
                logHandler.addEndOfRoundToLog(scoreManager.getScores());

                if (roundNumber <= 4) {
                    logHandler.addRoundInfoToLog(roundNumber);
                }
            }

            if (roundNumber > 4) {
                calculateScoreEndOfRound();
            }
            delay(delayTime);
        }
    }


    public String runApp() {
        setTitle(Title);
        setStatusText(Initializing_Info);
        initScores();
        initScore();
        gameSetup.initGame(players);
        hands = gameSetup.getHands();
        publicCards = gameSetup.getPlayingArea().getCardList();
        playerManager.setPublicCards(publicCards);
        playGame();

        for (int i = 0; i < nbPlayers; i++) updateScore(i);
        int maxScore = 0;
        for (int i = 0; i < nbPlayers; i++) if (scoreManager.getScores()[i] > maxScore) maxScore = scoreManager.getScores()[i];
        List<Integer> winners = new ArrayList<>();
        for (int i = 0; i < nbPlayers; i++) if (scoreManager.getScores()[i] == maxScore) winners.add(i);
        String winText;
        if (winners.size() == 1) {
            winText = winText1 + winners.iterator().next();
        } else {
            winText = winText2 + String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toList()));
        }
        addActor(new Actor(gameOver_gif), textLocation);
        setStatusText(winText);
        refresh();
        logHandler.addEndOfGameToLog(scoreManager.getScores(), winners);

        return logHandler.getLogResult();
    }

    public List<Card> getPublicCards(){
        return publicCards;
    }
}

