package services;

import exceptions.InvalidTokenFormat;
import helpers.PlayerToPlayerDtoMapper;
import models.Player;
import models.RequestToken;
import models.ResponseWithPlayer;
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

    public ResponseWithPlayer login(String token){
        ResponseWithPlayer response = new ResponseWithPlayer();
        if(authenticateUserToken(token)) {
            response.setPlayerDto(playerToPlayerDtoMapper.apply(playerRepository.getUserByUsername(decodeToken(token).getUsername())));
            response.setOperationSuccessful(true);
        }else {
            response.setOperationSuccessful(false);
            response.setMessage("Invalid username or password");
        }
        return response;
    }

    private boolean authenticateUserToken(String token){
        RequestToken decodedToken = decodeToken(token);
        if(playerRepository.containsUser(decodedToken.getUsername())){
            Player player = playerRepository.getUserByUsername(decodedToken.getUsername());
            if(BCrypt.checkpw(decodedToken.getPassword(), player.getPassword())){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public ResponseWithPlayer register(String encodedToken) {
        RequestToken decoded = decodeToken(encodedToken);
        ResponseWithPlayer response = new ResponseWithPlayer();
        if(!playerRepository.containsUser(decoded.getUsername())){
            Player playerToRegister = preparePlayerToRegister(decoded);
            if(playerRepository.registerUser(playerToRegister)){
                response.setPlayerDto(playerToPlayerDtoMapper.apply(playerRepository.getPlayerByName(decoded.getUsername())));
                response.setOperationSuccessful(true);
            }
        }else {
            response.setOperationSuccessful(false);
            response.setMessage("Username already in use");
        }
        return response;
    }

    private Player preparePlayerToRegister(RequestToken decoded) {
        Player playerToRegister = new Player();
        playerToRegister.setPassword(BCrypt.hashpw(decoded.getPassword(), BCrypt.gensalt()));
        playerToRegister.setUsername(decoded.getUsername());
        return playerToRegister;
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

    public ResponseWithPlayer increaseScore(String encodedToken) {
        ResponseWithPlayer response = new ResponseWithPlayer();
        if(authenticateUserToken(encodedToken)){
            playerRepository.increaseScoreByUsername(decodeToken(encodedToken).getUsername());
            response.setPlayerDto(playerToPlayerDtoMapper.apply(playerRepository.getPlayerByName(decodeToken(encodedToken).getUsername())));
            response.setOperationSuccessful(true);
        }else {
            response.setOperationSuccessful(false);
            response.setMessage("User authentication fail");
        }
        return response;
    }
}
