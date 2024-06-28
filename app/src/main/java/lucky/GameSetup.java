package lucky;

import Player.Player;
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.Location;

import java.util.List;
import java.util.Properties;

public class GameSetup {
    private LuckyThirdteen luckyThirteen;
    private Properties properties;
    private Hand[] hands;
    private Hand playingArea;
    private Hand pack;
    private static final int nbPlayers = 4;
    public final int nbStartCards = 2;
    public final int nbFaceUpCards = 2;
    private final Location trickLocation = new Location(350, 350);
    private final Deck deck;
    private final int trickWidth = 40;
    private final int handWidth = 400;
    private final String PLAYER = "players.";
    private final String InitialCards = ".initialcards";
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };

    public GameSetup(LuckyThirdteen luckyThirteen, Properties properties, Deck deck) {
        this.luckyThirteen = luckyThirteen;
        this.properties = properties;
        this.deck = deck;
        hands = new Hand[nbPlayers];
    }


    public void initGame(List<Player> players) {
        // Initialize player hands
        for (int i = 0; i < nbPlayers; i++) {
            hands[i] = new Hand(deck);
            players.get(i).setHand(hands[i]);
            players.get(i).initializeHand(hands[i]);
        }

        // Initialize playing area
        playingArea = new Hand(deck);
        dealInitialCards();

        // Set views for playing area and hands
        playingArea.setView(luckyThirteen, new RowLayout(trickLocation, (playingArea.getNumberOfCards() + 2) * trickWidth));
        playingArea.draw();

        for (int i = 0; i < nbPlayers; i++) {
            RowLayout layout = new RowLayout(handLocations[i], handWidth);
            layout.setRotationAngle(90 * i);
            hands[i].setView(luckyThirteen, layout);
            hands[i].setTargetArea(new TargetArea(trickLocation));
            hands[i].draw();
        }
    }

    private void dealInitialCards() {
        pack = deck.toHand(false);

        // Deal initial shared cards
        String initialShareKey = "shared.initialcards";
        String initialShareValue = properties.getProperty(initialShareKey);
        if (initialShareValue != null) {
            String[] initialCards = initialShareValue.split(",");
            for (String initialCard : initialCards) {
                if (initialCard.length() <= 1) {
                    continue;
                }
                Card card = getCardFromList(pack.getCardList(), initialCard);
                if (card != null) {
                    card.removeFromHand(true);
                    playingArea.insert(card, true);
                }
            }
        }
        int cardsToShare = nbFaceUpCards - playingArea.getNumberOfCards();
        for (int j = 0; j < cardsToShare; j++) {
            if (pack.isEmpty()) return;
            Card dealt = randomCard(pack.getCardList());
            dealt.removeFromHand(true);
            playingArea.insert(dealt, true);
        }

        // Deal initial private cards
        for (int i = 0; i < nbPlayers; i++) {
            String initialCardsKey = PLAYER + i + InitialCards;
            String initialCardsValue = properties.getProperty(initialCardsKey);
            if (initialCardsValue == null) {
                continue;
            }
            String[] initialCards = initialCardsValue.split(",");
            for (String initialCard : initialCards) {
                if (initialCard.length() <= 1) {
                    continue;
                }
                Card card = getCardFromList(pack.getCardList(), initialCard);
                if (card != null) {
                    card.removeFromHand(false);
                    hands[i].insert(card, false);
                }
            }
        }

        for (int i = 0; i < nbPlayers; i++) {
            int cardsToDealt = nbStartCards - hands[i].getNumberOfCards();
            for (int j = 0; j < cardsToDealt; j++) {
                if (pack.isEmpty()) return;
                Card dealt = randomCard(pack.getCardList());
                dealt.removeFromHand(false);
                hands[i].insert(dealt, false);
            }
        }
    }

    public Card getCardFromList(List<Card> cards, String cardName) {
        Rank cardRank = getRankFromString(cardName);
        Suit cardSuit = getSuitFromString(cardName);
        for (Card card : cards) {
            if (card.getSuit() == cardSuit && card.getRank() == cardRank) {
                return card;
            }
        }
        return null;
    }

    public Rank getRankFromString(String cardName) {
        String rankString = cardName.substring(0, cardName.length() - 1);
        int rankValue = Integer.parseInt(rankString);
        for (Rank rank : Rank.values()) {
            if (rank.getRankCardValue() == rankValue) {
                return rank;
            }
        }
        return Rank.ACE;
    }

    public Suit getSuitFromString(String cardName) {
        String suitString = cardName.substring(cardName.length() - 1);
        for (Suit suit : Suit.values()) {
            if (suit.getSuitShortHand().equals(suitString)) {
                return suit;
            }
        }
        return Suit.CLUBS;
    }

    private Card randomCard(List<Card> list) {
        int x = LuckyThirdteen.random.nextInt(list.size());
        return list.get(x);
    }

    public Hand[] getHands(){
        return hands;
    }

    public Hand getPack(){
        return pack;
    }

    public Hand getPlayingArea(){
        return playingArea;
    }
}
