package by.bsuir.pbz2.data.repository;

import by.bsuir.pbz2.data.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Long, User> {
    Optional<User> findByEmail(String email);
}
