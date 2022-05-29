package com.jojoldu.book.springboot.domain.users;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u ORDER BY u.id DESC")
    List<Users> findAllDesc();

    boolean existsByUsername(String username);

}
