package by.bsuir.pbz2.data.repository.impl;

import by.bsuir.pbz2.data.entity.User;
import by.bsuir.pbz2.data.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(manager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return manager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User save(User entity) {
        Long id = entity.getId();
        if (id == null) {
            manager.persist(entity);
        } else {
            manager.merge(entity);
        }
        return entity;
    }

    @Override
    public boolean delete(Long id) {
        User user = manager.find(User.class, id);
        if (user == null) {
            return false;
        }
        manager.remove(user);
        return true;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(manager.find(User.class, email));
    }
}
