package repositories;

import models.Player;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Stateless
public class PlayerRepository {

    EntityManager entityManager = Persistence.createEntityManagerFactory("WordsPersistenceUnit").createEntityManager();

    public boolean containsUser(String username) {
        Query query = entityManager.createQuery("select count (p) from Player p WHERE p.username = :username");
        query.setParameter("username", username);
        long singleResult = (long)query.getSingleResult();
        return singleResult>0;
    }

    public boolean registerUser(Player playerToRegister) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(playerToRegister);
        transaction.commit();
        return true;
    }

    public Player getUserByUsername(String username) {
        Query query = entityManager.createQuery("select p from Player p WHERE p.username = :username");
        query.setParameter("username", username);
        return (Player)query.getSingleResult();
    }

    public int increaseScoreByUsername(String username) {
        Player user = getUserByUsername(username);
        user.setScore(user.getScore()+1);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(user);
        transaction.commit();
        return user.getScore();
    }

    public Player getPlayerByName(String username) {
        Query query = entityManager.createQuery("select p from Player p where p.username = :username");
        query.setParameter("username", username);
        Player user = (Player) query.getSingleResult();
        return user;
    }
}
