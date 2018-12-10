package repositories;

import models.Player;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Stateless
public class PlayerRepository {//TODO PlayerRepository

    EntityManager entityManager = Persistence.createEntityManagerFactory("WordsPersistenceUnit").createEntityManager();

    public boolean containsUser(String username) {
        Query query = entityManager.createQuery("select count (p) from Player p WHERE p.username = :username");
        query.setParameter("username", username);
        return query.getFirstResult()>0;
    }

    public boolean registerUser(Player playerToRegister) {
        entityManager.persist(playerToRegister);
        return true;
    }

    public Player getUserByUsername(String s) {
        return new Player();
    }
}
