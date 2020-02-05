package com.dcn.aaserver.repository;

import com.dcn.aaserver.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

//    TODO: could not use jpa query, investigate more..
//    @Query("FROM UserEntity WHERE isDeleted = 0 ")
    @Query(nativeQuery = true, value = "SELECT * FROM User WHERE is_deleted = 0 ")
    Page<UserEntity> findAllPageable(Pageable pageable);

    @Query("FROM UserEntity WHERE isDeleted = 0 ")
    List<UserEntity> findAll();

    Optional<UserEntity> findOneById(Long id);

    Optional<UserEntity> findOneByUsername(String username);

    @Override
    @Modifying
    @Query("UPDATE UserEntity SET isDeleted = 1 WHERE id = ?1")
    void deleteById(Long id);
}