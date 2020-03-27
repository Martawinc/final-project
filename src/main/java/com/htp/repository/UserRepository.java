package com.htp.repository;

import com.htp.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public User findByLogin (String login){
        TypedQuery <User> query = entityManager.createQuery(
                "select u from User u where u.login = :login", User.class);
        return query.setParameter("login", login).getSingleResult();
    }

    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public User findById(Long id) {
//        return entityManager.find(User.class, id);
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Transactional
    public User save(User user) {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        return  user;
        //return entityManager.find(User.class, user.getId());
    }
}
