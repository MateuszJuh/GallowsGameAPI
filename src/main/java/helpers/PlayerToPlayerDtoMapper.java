package helpers;

import models.Player;
import models.PlayerDto;
import models.ResponsePlayerToken;

import java.util.Optional;
import java.util.function.Function;

public class PlayerToPlayerDtoMapper implements Function<Player, ResponsePlayerToken> {
    @Override
    public ResponsePlayerToken apply(Player player) {
        ResponsePlayerToken responsePlayerToken = new ResponsePlayerToken();
        responsePlayerToken.setPlayerDto(Optional.of(new PlayerDto()));
        responsePlayerToken.getPlayerDto().get().setId(player.getId());
        responsePlayerToken.getPlayerDto().get().setScore(player.getScore());
        responsePlayerToken.getPlayerDto().get().setUsername(player.getUsername());
        return responsePlayerToken;
    }
}
