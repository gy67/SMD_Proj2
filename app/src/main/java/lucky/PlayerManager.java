package lucky;

import Player.Factory.PlayerFactory;
import Player.Player;
import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PlayerManager {
    private List<Player> players;
    private PlayerFactory factory = new PlayerFactory();
    private Properties properties;
    private final String PLAYER = "players.";
    private final int nbPlayers = 4;

    public PlayerManager(Properties properties) {
        this.properties = properties;
        players = new ArrayList<>();
        initPlayers();
    }

    private void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) { // Assuming 4 players
            String playerString = PLAYER + i;
            String type = properties.getProperty(playerString);
            if (type != null && !type.isEmpty()) {
                Player player = factory.createPlayer(type);
                players.add(player);
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPublicCards(List<Card> publicCards){
        for (int i = 0; i < nbPlayers; i++) {
            players.get(i).setPublicCards(publicCards);
        }
    }
}
