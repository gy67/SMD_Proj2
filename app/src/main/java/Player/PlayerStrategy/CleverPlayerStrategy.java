package Player.PlayerStrategy;

import lucky.Rank;
import lucky.Suit;
import Player.Factory.SumStrategyFactory;
import Player.Player;
import Player.SumStrategy.SumStrategy;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import static lucky.Suit.PUBLIC_CARD_MULTIPLICATION_FACTOR;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CleverPlayerStrategy extends CleverPlayerDecorator {
    private final Set<Card> cardsPlayed;
    private final List<SumStrategy> sumStrategies;

    public CleverPlayerStrategy(PlayerStrategy basicStrategy) {
        super(basicStrategy);
        this.sumStrategies = SumStrategyFactory.getInstance().createSumStrategies();
        this.cardsPlayed = new HashSet<>();
    }

    @Override
    public Card selectCardToDiscard(Player player, List<Card> cardsPlayed) {
        // Update the set of played cards
        this.cardsPlayed.addAll(cardsPlayed);

        Hand hand = player.getHand();
        List<Card> privateCards = hand.getCardList();
        List<Card> publicCards = player.getPublicCards();

        // Check for combinations that sum to 13 and retain the highest scoring combination
        int maxScore = 0;
        Set<Card> cardsToKeep = new HashSet<>();

        // Check pairs of private cards
        for (Card privateCard1 : privateCards) {
            for (Card privateCard2 : privateCards) {
                if (privateCard1 != privateCard2) {
                    if (sumStrategies.get(0).isSumThirteenPairs(privateCard1, privateCard2)) {
                        int value1 = ((Rank) privateCard1.getRank()).getRankCardValue();
                        int value2 = ((Rank) privateCard2.getRank()).getRankCardValue();
                        int factor1 = ((Suit) privateCard1.getSuit()).getMultiplicationFactor();
                        int factor2 = ((Suit) privateCard2.getSuit()).getMultiplicationFactor();
                        int score = value1 * factor1 + value2 * factor2;
                        if (score > maxScore) {
                            maxScore = score;
                            cardsToKeep.clear();
                            cardsToKeep.add(privateCard1);
                            cardsToKeep.add(privateCard2);
                        }
                    }
                }
            }
        }

        // Check pairs of private and public cards
        for (Card privateCard : privateCards) {
            for (Card publicCard : publicCards) {
                if (sumStrategies.get(1).isSumThirteenPairs(privateCard, publicCard)) {
                    int value1 = ((Rank) privateCard.getRank()).getRankCardValue();
                    int value2 = ((Rank) publicCard.getRank()).getRankCardValue();
                    int factor1 = ((Suit) privateCard.getSuit()).getMultiplicationFactor();
                    int factor2 = PUBLIC_CARD_MULTIPLICATION_FACTOR;
                    int score = value1 * factor1 + value2 * factor2;
                    if (score > maxScore) {
                        maxScore = score;
                        cardsToKeep.clear();
                        cardsToKeep.add(privateCard);
                    }
                }
            }
        }

        // Check combinations of two private cards and two public cards
        for (Card privateCard1 : privateCards) {
            for (Card privateCard2 : privateCards) {
                for (Card publicCard1 : publicCards) {
                    for (Card publicCard2 : publicCards) {
                        if (publicCard1 != publicCard2) {
                            if (sumStrategies.get(2).isSumThirteenFourCards(privateCard1, privateCard2, publicCard1, publicCard2)) {
                                int value1 = ((Rank) privateCard1.getRank()).getRankCardValue();
                                int value2 = ((Rank) privateCard2.getRank()).getRankCardValue();
                                int value3 = ((Rank) publicCard1.getRank()).getRankCardValue();
                                int value4 = ((Rank) publicCard2.getRank()).getRankCardValue();
                                int factor1 = ((Suit) privateCard1.getSuit()).getMultiplicationFactor();
                                int factor2 = ((Suit) privateCard2.getSuit()).getMultiplicationFactor();
                                int factor3 = PUBLIC_CARD_MULTIPLICATION_FACTOR;
                                int score = value1 * factor1 + value2 * factor2 + value3 * factor3 + value4 * factor3;
                                if (score > maxScore) {
                                    maxScore = score;
                                    cardsToKeep.clear();
                                    cardsToKeep.add(privateCard1);
                                    cardsToKeep.add(privateCard2);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Select the card to discard from those not kept
        Card cardToDiscard = null;
        if (cardsToKeep.size() == 2){
            for (Card card : privateCards) {
                if (!cardsToKeep.contains(card)) {
                    cardToDiscard = card;
                    break;
                }
            }
        } else {
            int maxImpact = 0;
            for (Card card : privateCards) {
                if (!cardsToKeep.contains(card)) {
                    int impact = calculateImpact(card, publicCards);
                    if (impact > maxImpact) {
                        maxImpact = impact;
                        cardToDiscard = card;
                    }
                }
            }
        }

        // If no suitable card to discard was found, use BasicPlayerStrategy's selection
        if (cardToDiscard == null) {
            cardToDiscard = baseStrategy.selectCardToDiscard(player, cardsPlayed);
        }

        this.cardsPlayed.add(cardToDiscard);
        return cardToDiscard;
    }

    /**
     * Calculate the impact of discarding a card.
     * The impact is determined by how many combinations with already played cards would sum to 13.
     *
     * @param card The card to evaluate.
     * @return The impact score.
     */
    private int calculateImpact(Card card, List<Card> publicCards) {
        int impact = 0;
        for (Card playedCard : cardsPlayed) {
            if (sumStrategies.get(0).isSumThirteenPairs(card, playedCard)) {
                impact++;
            }
            if (sumStrategies.get(2).isSumThirteenFourCards(card, playedCard, publicCards.get(0), publicCards.get(1))) {
                impact++;
            }
        }
        return impact;
    }
}
