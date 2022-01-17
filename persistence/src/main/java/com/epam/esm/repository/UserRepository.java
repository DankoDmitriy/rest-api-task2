package com.epam.esm.repository;

import com.epam.esm.model.impl.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
