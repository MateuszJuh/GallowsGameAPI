package services;

import exceptions.InvalidTokenFormat;
import models.RequestToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import repositories.PlayerRepository;

import java.util.Base64;

import static org.junit.Assert.*;

public class PlayerServiceTest {//TODO PlayerServiceTest

    private PlayerRepository playerRepositoryMock;
    private PlayerService playerService;

    @Before
    public void prepare(){
        playerRepositoryMock = Mockito.mock(PlayerRepository.class);
        playerService = new PlayerService(playerRepositoryMock);
    }

    @Test
    public void decodeToken_decodingValidToken_returnRequestTokenWithValuesDecoded(){
        String user = "user";
        String password = "password";
        String tokenToEncode = user + ":" + password;
        byte[] encode = Base64.getEncoder().encode(tokenToEncode.getBytes());
        String encodedToken = new String(encode);
        RequestToken requestToken = playerService.decodeToken(encodedToken);
        Assert.assertEquals(user, requestToken.getUsername());
        Assert.assertEquals(password, requestToken.getPassword());
    }

    @Test(expected = InvalidTokenFormat.class)
    public void decodeToken_decodingTokenWithOnlyOneParameter_exceptionThrow(){
        String user = "user";
        byte[] encode = Base64.getEncoder().encode(user.getBytes());
        String encodedToken = new String(encode);
        RequestToken requestToken = playerService.decodeToken(encodedToken);
    }

    @Test(expected = InvalidTokenFormat.class)
    public void decodeToken_decodingTokenWithIncorrectlySeparatedValues_exceptionThrow(){
        String user = "user";
        String password = "password";
        String tokenToEncode = user + ";" + password;
        byte[] encode = Base64.getEncoder().encode(tokenToEncode.getBytes());
        String encodedToken = new String(encode);
        RequestToken requestToken = playerService.decodeToken(encodedToken);
        Assert.assertEquals(user, requestToken.getUsername());
        Assert.assertEquals(password, requestToken.getPassword());
    }

    @Test(expected = InvalidTokenFormat.class)
    public void decodeToken_decodingTokenWithTooManyParameters_exceptionThrow(){
        String user = "user";
        String password = "password";
        String tokenToEncode = user + ":" + password + ":" + "last not wanted element";
        byte[] encode = Base64.getEncoder().encode(tokenToEncode.getBytes());
        String encodedToken = new String(encode);
        RequestToken requestToken = playerService.decodeToken(encodedToken);
        Assert.assertEquals(user, requestToken.getUsername());
        Assert.assertEquals(password, requestToken.getPassword());
    }
}