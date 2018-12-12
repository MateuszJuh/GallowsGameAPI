package services;

import exceptions.InvalidTokenFormat;
import helpers.PlayerToPlayerDtoMapper;
import models.Player;
import models.RequestToken;
import models.ResponsePlayerToken;
import org.mindrot.jbcrypt.BCrypt;
import repositories.PlayerRepository;

import java.util.Base64;

public class PlayerService {
    private PlayerRepository playerRepository;
    private PlayerToPlayerDtoMapper playerToPlayerDtoMapper;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        playerToPlayerDtoMapper = new PlayerToPlayerDtoMapper();
    }

    public ResponsePlayerToken login(String token){
        ResponsePlayerToken responsePlayerToken = new ResponsePlayerToken();
        if(authenticateUserToken(token)) {
            playerToPlayerDtoMapper.apply(playerRepository.getUserByUsername(decodeToken(token).getUsername()));
            responsePlayerToken.setOperationSuccessful(true);
        }else {
            responsePlayerToken.setOperationSuccessful(false);
        }
        return responsePlayerToken;
    }

    public boolean authenticateUserToken(String token){
        RequestToken decodedToken = decodeToken(token);
        if(playerRepository.containsUser(decodedToken.getUsername())){
            Player player = playerRepository.getUserByUsername(decodedToken.getUsername());
            if(BCrypt.checkpw(player.getPassword(), decodedToken.getPasswordHash())){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public ResponsePlayerToken register(String encodedToken) {
        RequestToken decoded = decodeToken(encodedToken);
        ResponsePlayerToken responsePlayerToken = new ResponsePlayerToken();
        if(!playerRepository.containsUser(decoded.getUsername())){
            if(playerRepository.registerUser(new Player(decoded.getUsername(), decoded.getPasswordHash()))){
                responsePlayerToken = playerToPlayerDtoMapper.apply(playerRepository.getPlayerByName(decoded.getUsername()));
                responsePlayerToken.setOperationSuccessful(true);
            }
        }else {
            responsePlayerToken.setOperationSuccessful(false);
        }
        return responsePlayerToken;
    }

    private RequestToken decodeToken(String token){
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);
        if(decodedString.contains(":")){
            String[] split = decodedString.split(":");
            if(split.length!=2){
                throw new InvalidTokenFormat("RequestToken must be formatted (before encoding) like: \"<username>:<passwordHash>\"");
            }
            return new RequestToken(split[0], split[1]);
        }else {
            throw new InvalidTokenFormat("RequestToken parameters have to be separated by ':'");
        }
    }

    public ResponsePlayerToken increaseScore(String encodedToken) {
        ResponsePlayerToken responsePlayerToken = new ResponsePlayerToken();
        if(authenticateUserToken(encodedToken)){
            responsePlayerToken = playerToPlayerDtoMapper.apply(playerRepository.getPlayerByName(decodeToken(encodedToken).getUsername()));
            responsePlayerToken.setOperationSuccessful(true);
            return responsePlayerToken;
        }else {
            responsePlayerToken.setOperationSuccessful(false);
            return responsePlayerToken;
        }
    }
}
