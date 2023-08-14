package com.oranic.org.repository;

import com.oranic.org.model.token.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join User u
      on t.user.id = u.id
      where u.id = :userId and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE Token t SET t.expired = true, t.revoked = true WHERE t.token = :tokenValue")
    int updateTokenToLogout(String tokenValue);

    @Query("select count(t) from Token t where t.token = :tokenValue and t.revoked = true and t.expired = true")
    int verifyValidityToken(String tokenValue);
}
