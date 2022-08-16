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

    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM USERS WHERE id IN (SELECT user_id FROM USERS_INSTRUMENTS WHERE instruments_id in ?1)", nativeQuery = true)
    List<User> getUsersByInstrument(List<Long> instruments, Pageable pageable);

    @Query(value = "SELECT * FROM USERS WHERE id IN (SELECT user_id FROM USER_FAVORITE_GENRES WHERE favorite_genres in ?1)", nativeQuery = true)
    List<User> getUsersByGenre(List<Long> genres, Pageable pageable);

    @Query(value = "SELECT * FROM USERS WHERE id IN (SELECT user_id FROM USERS_INSTRUMENTS WHERE instruments_id in ?1) AND id IN (SELECT user_id FROM USER_FAVORITE_GENRES WHERE favorite_genres in ?2)", nativeQuery = true)
    List<User> getUsersByInstrumentAndGenre(List<Long> instruments, List<Long> genres, Pageable pageable);
}
