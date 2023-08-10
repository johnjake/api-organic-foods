package com.oranic.org.repository;

import com.oranic.org.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
      select count(t) from Token t inner join User u
      on t.user.id = u.id
      where u.email = :email_add and (t.expired = false or t.revoked = false)
      """)
    Integer getTokenCountByEmail(String email_add);
    Optional<User> findByEmail(String email);
}
