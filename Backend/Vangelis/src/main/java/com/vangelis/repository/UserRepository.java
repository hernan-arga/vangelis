package com.vangelis.repository;

import com.vangelis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUserName(String userName);

    @Query(value = "SELECT * FROM users WHERE (id IN (SELECT user_id FROM users_instruments WHERE instruments_id in ?1)) AND (id IN (SELECT user_id FROM users_favorite_genres WHERE favorite_genres_id in ?2))", nativeQuery = true)
    Page<User> findAllFiltered(List<Long> instruments, List<Long> genres, Pageable pageable);

    Page<User> findAll(Pageable pageable);
}
