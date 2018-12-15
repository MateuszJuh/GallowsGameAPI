package helpers;

import models.Player;
import models.PlayerDto;

import java.util.function.Function;

public class PlayerToPlayerDtoMapper implements Function<Player, PlayerDto> {

    @Override
    public PlayerDto apply(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setUsername(player.getUsername());
        if(player.getScore()!=0){
            playerDto.setScore(player.getScore());
        }
        if(player.getId()!=0){
            playerDto.setId(player.getId());
        }
        return playerDto;
    }
}
