package pl.lotto.domain.resultchecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayerRepositoryTestImpl implements PlayerRepository {
    Map<String, Player> players = new HashMap<>();

    @Override
    public List<Player> saveAll(List<Player> players) {
        for (Player player : players) {
            this.players.put(player.getHash(), player);
        }
        return players;
    }

    @Override
    public Optional<Player> findById(String ticketId) {
        return Optional.ofNullable(players.get(ticketId));
    }
}
