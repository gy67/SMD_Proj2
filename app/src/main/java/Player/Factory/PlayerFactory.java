package Player.Factory;

import Player.Player;
import Player.PlayerStrategy.PlayerStrategy;
import Player.PlayerStrategy.*;


public class PlayerFactory {
    public Player createPlayer(String type) {
        PlayerStrategy strategy;
        switch (type.toLowerCase()) {
            case "human":
                strategy = new HumanPlayerStrategy();
                break;
            case "random":
                strategy = new RandomPlayerStrategy();
                break;
            case "basic":
                strategy = new BasicPlayerStrategy();
                break;
            case "clever":
                strategy = new CleverPlayerStrategy(new BasicPlayerStrategy());
                break;
            default:
                throw new IllegalArgumentException("Unknown player type: " + type);
        }
        return new Player(strategy, type);
    }
}
