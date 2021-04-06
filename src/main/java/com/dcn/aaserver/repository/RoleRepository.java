package com.dcn.aaserver.repository;

import com.dcn.aaserver.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, RoleRepositoryCustom {

    @Query("FROM RoleEntity WHERE code = ?1 AND isDeleted = 0")
    RoleEntity findByCode(String code);

    @Query("FROM RoleEntity WHERE isDeleted = 0 ")
    List<RoleEntity> findAll();

    Optional<RoleEntity> findOneById(Long id);

    Optional<RoleEntity> findOneByCode(String code);

    @Override
    @Modifying
    @Query("UPDATE RoleEntity SET isDeleted = 1 WHERE id = ?1")
    void deleteById(Long id);

    @Query("FROM RoleEntity WHERE id IN ?1 AND isDeleted = 0 ")
    List<RoleEntity> getByIds(List<Long> ids);
}
