package com.vangelis.repository;

import com.vangelis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUserName(String userName);

    @Query(value = "SELECT * FROM users WHERE (id IN (SELECT user_id FROM users_instruments WHERE instruments_id in :instruments)) AND (id IN (SELECT user_id FROM users_favorite_genres WHERE favorite_genres_id in :genres)) AND (user_name LIKE %:userName%)", nativeQuery = true)
    Page<User> findAllFiltered(@Param("instruments") List<Long> instruments, @Param("genres") List<Long> genres, @Param("userName") String username, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE user_name LIKE %:userName%", nativeQuery = true)
    Page<User> findAllByUserName(@Param("userName") String userName, Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE id IN :idList", nativeQuery = true)
    List<User> findAllByIdList(@Param("idList") List<Long> idList);


}
