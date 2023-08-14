package com.oranic.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTokenRepository extends JpaRepository<RegToken, Integer> {
}
