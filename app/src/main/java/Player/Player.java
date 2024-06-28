package Player;

import lucky.*;
import Player.Factory.ScoreStrategyFactory;
import Player.Factory.SumStrategyFactory;
import Player.PlayerStrategy.PlayerStrategy;
import Player.PlayerStrategy.HumanPlayerStrategy;
import Player.ScoreStrategy.CompositeScoreStrategy;
import Player.ScoreStrategy.ScoreStrategy;
import Player.SumStrategy.CompositeSumStrategy;
import Player.SumStrategy.SumStrategy;
import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.*;
import java.util.List;

public class Player {
    private PlayerStrategy strategy;
    private List<SumStrategy> sumStrategies;
    private List<ScoreStrategy> scoreStrategies;
    private String type;
    private Hand hand;
    private int score;
    private List<Card> publicCards;
    private CompositeSumStrategy compositeSumStrategy = new CompositeSumStrategy();
    private CompositeScoreStrategy compositeScoreStrategy = new CompositeScoreStrategy();

    public Player(PlayerStrategy strategy, String type) {
        this.strategy = strategy;
        this.score = 0;
        this.type = type;
        this.sumStrategies = SumStrategyFactory.getInstance().createSumStrategies();
        this.scoreStrategies = ScoreStrategyFactory.getInstance().createScoreStrategies();

        for (SumStrategy sumStrategy : sumStrategies) {
            compositeSumStrategy.addStrategy(sumStrategy);
        }

        for (ScoreStrategy scoreStrategy : scoreStrategies) {
            compositeScoreStrategy.addStrategy(scoreStrategy);
        }
    }

    public void initializeHand(Hand hand) {
        this.hand = hand;
        if (strategy instanceof HumanPlayerStrategy) {
            ((HumanPlayerStrategy) strategy).initCardListener(hand);
        }
    }

    public String getType(){
        return type;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Card playTurn(List<Card> cardsPlayed) {
        Card cardToDiscard = strategy.selectCardToDiscard(this, cardsPlayed);
        return cardToDiscard;
    }

    public boolean isSumThirteen(List<Card> publicCards) {
        return compositeSumStrategy.isSumThirteen(hand.getCardList(), publicCards);
    }

    public int calculateMaxScoreForThirteen(List<Card> publicCards) {
        return compositeScoreStrategy.calculateScore(hand.getCardList(), publicCards);
    }

    private int getScorePrivateCard(Card card) {
        Rank rank = (Rank) card.getRank();
        Suit suit = (Suit) card.getSuit();
        return rank.getScoreCardValue() * suit.getMultiplicationFactor();
    }

    public int calculatePrivateCardsScore(){
        List<Card> privateCards = hand.getCardList();
        int score1 = getScorePrivateCard(privateCards.get(0));
        int score2 = getScorePrivateCard(privateCards.get(1));
        return score1 + score2;
    }

    public void setPublicCards(List<Card> publicCards){
        this.publicCards = publicCards;
    }

    public List<Card> getPublicCards(){
        return publicCards;
    }

}
