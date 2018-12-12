package models;

import java.util.Optional;

public class ResponsePlayerToken {

    private boolean isOperationSuccessful;
    private Optional<PlayerDto> playerDto;

    public boolean isOperationSuccessful() {
        return isOperationSuccessful;
    }

    public void setOperationSuccessful(boolean operationSuccessful) {
        isOperationSuccessful = operationSuccessful;
    }

    public Optional<PlayerDto> getPlayerDto() {
        return playerDto;
    }

    public void setPlayerDto(Optional<PlayerDto> playerDto) {
        this.playerDto = playerDto;
    }
}
