package services;

import exceptions.InvalidTokenFormat;
import models.Player;
import models.Token;
import org.mindrot.jbcrypt.BCrypt;
import repositories.PlayerRepository;

import java.util.Base64;

public class PlayerService {
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public boolean authenticateUserToken(String token){
        Token decodedToken = decodeToken(token);
        if(playerRepository.containsUser(decodedToken.getUsername())){
            Player player = playerRepository.getUserByUsername(decodedToken.getUsername());
            if(BCrypt.checkpw(player.getPassword(), decodedToken.getPasswordHash())){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    public boolean register(String encodedToken) {
        Token decoded = decodeToken(encodedToken);
        if(!playerRepository.containsUser(decoded.getUsername())){
            return playerRepository.registerUser(new Player(decoded.getUsername(), decoded.getPasswordHash()));
        }else {
            return false;
        }
    }

    private Token decodeToken(String token){
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);
        if(decodedString.contains(":")){
            String[] split = decodedString.split(":");
            if(split.length!=2){
                throw new InvalidTokenFormat("Token must be formatted (before encoding) like: \"<username>:<passwordHash>\"");
            }
            return new Token(split[0], split[1]);
        }else {
            throw new InvalidTokenFormat("Token parameters have to be separated by ':'");
        }
    }

    public int increaseScore(String encodedToken) {//TODO increase score
        return 0;
    }
}
